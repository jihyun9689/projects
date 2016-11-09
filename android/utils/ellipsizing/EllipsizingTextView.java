package com.wowell.talboro2.utils.ellipsizing;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextUtils.TruncateAt;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.wowell.talboro2.utils.logger.LogManager;
/*
 * Copyright (C) 2011 Micah Hainline
 * Copyright (C) 2012 Triposo
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// see https://github.com/triposo/barone/blob/master/src/com/triposo/barone/EllipsizingTextView.java
// and the comments at http://stackoverflow.com/questions/2160619/android-ellipsize-multiline-textview

public class EllipsizingTextView
        extends TextView {
    private static final String ELLIPSIS = "…";
    private static final Pattern DEFAULT_END_PUNCTUATION = Pattern.compile("[\\.,…;\\:\\s]*$", Pattern.DOTALL);

    private final List<EllipsizeListener> ellipsizeListeners = new ArrayList<EllipsizeListener>();

    private boolean isEllipsized;
    private boolean isStale;
    private boolean programmaticChange;
    private String fullText;
    private int maxLines;
    private float lineSpacingMultiplier = 1.0f;
    private float lineAdditionalVerticalPadding = 0.0f;
    EllipsizingSpannableListener ellipsizingSpannableListener;

    /**
     * The end punctuation which will be removed when appending #ELLIPSIS.
     */
    private Pattern endPunctuationPattern;

    public EllipsizingTextView(Context context) {
        this(context, null);
    }

    public EllipsizingTextView(Context context,
                               AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public EllipsizingTextView(Context context,
                               AttributeSet attrs,
                               int defStyle) {
        super(context, attrs, defStyle);
        super.setEllipsize(null);
        TypedArray a = context.obtainStyledAttributes(attrs, new int[] { android.R.attr.maxLines });
        setMaxLines(a.getInt(0, Integer.MAX_VALUE));
        a.recycle();
        setEndPunctuationPattern(DEFAULT_END_PUNCTUATION);
    }

    public void addSpannableListener(EllipsizingSpannableListener ellipsizingSpannableListener){
        this.ellipsizingSpannableListener = ellipsizingSpannableListener;
    }

    public void addEllipsizeListener(EllipsizeListener listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        ellipsizeListeners.add(listener);
    }

    public boolean ellipsizingLastFullyVisibleLine() {
        return maxLines == Integer.MAX_VALUE;
    }

    @Override
    public int getMaxLines() {
        return maxLines;
    }

    public boolean isEllipsized() {
        return isEllipsized;
    }

    public void removeEllipsizeListener(EllipsizeListener listener) {
        ellipsizeListeners.remove(listener);
    }

    @Override
    public void setEllipsize(TruncateAt where) {
        // Ellipsize settings are not respected
    }

    public void setEndPunctuationPattern(Pattern pattern) {
        this.endPunctuationPattern = pattern;
    }

    @Override
    public void setLineSpacing(float add,
                               float mult) {
        this.lineAdditionalVerticalPadding = add;
        this.lineSpacingMultiplier = mult;
        super.setLineSpacing(add, mult);
    }

    @Override
    public void setMaxLines(int maxLines) {
        super.setMaxLines(maxLines);
        this.maxLines = maxLines;
        isStale = true;
    }

    @Override
    public void setPadding(int left,
                           int top,
                           int right,
                           int bottom) {
        super.setPadding(left, top, right, bottom);
        if (ellipsizingLastFullyVisibleLine()) {
            isStale = true;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isStale) {
            resetText();
        }
        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w,
                                 int h,
                                 int oldw,
                                 int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (ellipsizingLastFullyVisibleLine()) {
            isStale = true;
        }
    }

    @Override
    protected void onTextChanged(CharSequence text,
                                 int start,
                                 int before,
                                 int after) {
        super.onTextChanged(text, start, before, after);
        if (!programmaticChange) {
            fullText = text.toString();
            isStale = true;
        }
    }

    private Layout createWorkingLayout(String workingText) {
        return new StaticLayout(workingText, getPaint(),
                getWidth() - getPaddingLeft() - getPaddingRight(),
                Alignment.ALIGN_NORMAL, lineSpacingMultiplier,
                lineAdditionalVerticalPadding, false /* includepad */);
    }

    /**
     * Get how many lines of text we can display so their full height is visible.
     */
    private int getFullyVisibleLinesCount() {
        Layout layout = createWorkingLayout("");
        int height = getHeight() - getPaddingTop() - getPaddingBottom();
        int lineHeight = layout.getLineBottom(0);
        return height / lineHeight;
    }

    /**
     * Get how many lines of text we are allowed to display.
     */
    private int getLinesCount() {
        int result = 0;
        if (ellipsizingLastFullyVisibleLine()) {
            int fullyVisibleLinesCount = getFullyVisibleLinesCount();
            if (fullyVisibleLinesCount == -1) {
                result = 1;
            }
            else {
                result = fullyVisibleLinesCount;
            }
        }
        else {
            result = maxLines;
        }

        return result;
    }

    private void resetText() {
        String workingText = fullText;
        boolean ellipsized = false;
        Spannable spannable = null;
        Layout layout = createWorkingLayout(workingText);
        int linesCount = getLinesCount();
        if (layout.getLineCount() > linesCount) {
            // We have more lines of text than we are allowed to display.
            workingText = fullText.substring(0, layout.getLineEnd(linesCount - 1)).trim();
            while (createWorkingLayout(workingText + ELLIPSIS).getLineCount() > linesCount) {
                int lastSpace = workingText.lastIndexOf(' ');
                if (lastSpace == -1) {
                    break;
                }
                workingText = workingText.substring(0, lastSpace);
            }
            // We should do this in the loop above, but it's cheaper this way.
            String moreStr = " 더 보기";
            workingText = endPunctuationPattern.matcher(workingText).replaceFirst("");
            workingText = workingText + ELLIPSIS + moreStr;

            spannable = new SpannableString(workingText);
            spannable.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    ellipsizingSpannableListener.onClick();
                }
            }, workingText.length() - moreStr.length(), workingText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            ellipsized = true;
        }
        if (!workingText.equals(getText())) {
            programmaticChange = true;
            try {
                if(spannable != null){
                    setText(spannable);
                }else{
                    setText(workingText);
                }
            }
            finally {
                programmaticChange = false;
            }
        }
        isStale = false;
        if (ellipsized != isEllipsized) {
            isEllipsized = ellipsized;
            for (EllipsizeListener listener : ellipsizeListeners) {
                listener.ellipsizeStateChanged(ellipsized);
            }
        }
    }

    public interface EllipsizeListener {
        void ellipsizeStateChanged(boolean ellipsized);
    }
}