//package com.wowell.talboro2.view;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.DialogFragment;
//import android.util.AttributeSet;
//import android.util.TypedValue;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.NumberPicker;
//import android.widget.TextView;
//
//import com.wowell.talboro2.R;
//import com.wowell.talboro2.utils.calculate.CalculateDP;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by kim on 2016-05-31.
// */
//public class FagerstromDialog extends DialogFragment{
//
//    private static final int LOW_MID_DIVIDER_SCORE = 3;
//    private static final int MID_HIGH_DIVIDER_SCORE = 6;
//
//    private Map<Integer, TestQuestion> questionMap = new HashMap<>();
//    private Map<Integer, List<TestAnswer>> answerMap = new HashMap<>();
//    private List<View> pages = new ArrayList<>();
//
//    private int pageCount = 0;
//    int totalScore = 0;
//
//    LinearLayout contentLinearLayout;
//    TextView questionTextView;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.layout_fagerstrom, container);
//
//        prepareTest();
//
//        contentLinearLayout = (LinearLayout) view.findViewById(R.id.layout_fagerstrom_content);
//        questionTextView = (TextView) view.findViewById(R.id.fagerstrom_quetion_text_view);
//        contentLinearLayout.addView(makeQuestionPage(pageCount));
//
//        return view;
//    }
//
//    private void prepareTest() {
//        List<TestAnswer> answer1 = new ArrayList<>();
//        answer1.add(new TestAnswer(0, R.string.fagerstrom_page_1_answer_1, 3));
//        answer1.add(new TestAnswer(0, R.string.fagerstrom_page_1_answer_2, 2));
//        answer1.add(new TestAnswer(0, R.string.fagerstrom_page_1_answer_3, 1));
//        answer1.add(new TestAnswer(0, R.string.fagerstrom_page_1_answer_4, 0));
//        questionMap.put(0, new TestQuestion(0, R.string.fagerstrom_page_1_question));
//        answerMap.put(0, answer1);
//
//        List<TestAnswer> answer2 = new ArrayList<>();
//        answer2.add(new TestAnswer(1, R.string.fagerstrom_page_2_answer_1, 3));
//        answer2.add(new TestAnswer(1, R.string.fagerstrom_page_2_answer_2, 2));
//        answer2.add(new TestAnswer(1, R.string.fagerstrom_page_2_answer_3, 1));
//        answer2.add(new TestAnswer(1, R.string.fagerstrom_page_2_answer_4, 0));
//        questionMap.put(1, new TestQuestion(1, R.string.fagerstrom_page_2_question));
//        answerMap.put(1, answer2);
//
//        List<TestAnswer> answer3 = new ArrayList<>();
//        answer3.add(new TestAnswer(2, R.string.fagerstrom_page_3_answer_1, 1));
//        answer3.add(new TestAnswer(2, R.string.fagerstrom_page_3_answer_2, 0));
//        questionMap.put(2, new TestQuestion(2, R.string.fagerstrom_page_3_question));
//        answerMap.put(2, answer3);
//
//        List<TestAnswer> answer4 = new ArrayList<>();
//        answer4.add(new TestAnswer(3, R.string.fagerstrom_page_4_answer_1, 1));
//        answer4.add(new TestAnswer(3, R.string.fagerstrom_page_4_answer_2, 0));
//        questionMap.put(3, new TestQuestion(3, R.string.fagerstrom_page_4_question));
//        answerMap.put(3, answer4);
//
//        List<TestAnswer> answer5 = new ArrayList<>();
//        answer5.add(new TestAnswer(4, R.string.fagerstrom_page_5_answer_1, 1));
//        answer5.add(new TestAnswer(4, R.string.fagerstrom_page_5_answer_2, 0));
//        questionMap.put(4, new TestQuestion(4, R.string.fagerstrom_page_5_question));
//        answerMap.put(4, answer5);
//
//        List<TestAnswer> answer6 = new ArrayList<>();
//        answer6.add(new TestAnswer(5, R.string.fagerstrom_page_6_answer_1, 1));
//        answer6.add(new TestAnswer(5, R.string.fagerstrom_page_6_answer_2, 0));
//        questionMap.put(5, new TestQuestion(5, R.string.fagerstrom_page_6_question));
//        answerMap.put(5, answer6);
//    }
//
//    private View makeQuestionPage(int pageNum){
//        LinearLayout linearLayout = new LinearLayout(getActivity());
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(CalculateDP.dpToPixel(287, getActivity()), CalculateDP.dpToPixel(310, getActivity()));
//        linearLayout.setLayoutParams(layoutParams);
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
//
//        linearLayout.addView(getQuestionTextView(pageNum));
//        linearLayout.addView(getAnswerTextView(pageNum));
//        return linearLayout;
//    }
//
//    private View getQuestionTextView(int pageNum){
//        int dp10 = CalculateDP.dpToPixel(10, getActivity());
//        TextView questionTextView = new TextView(getActivity());
//
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(CalculateDP.dpToPixel(280, getActivity()), ViewGroup.LayoutParams.WRAP_CONTENT);
//        layoutParams.setMargins(dp10, 0, dp10, dp10);
//        questionTextView.setLayoutParams(layoutParams);
//
//        questionTextView.setText(getResources().getString(questionMap.get(pageNum).getQuestionResId()));
//
//        return questionTextView;
//    }
//
//    private View getAnswerTextView(int pageNum){
//        LinearLayout linearLayout = new LinearLayout(getActivity());
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(CalculateDP.dpToPixel(300, getActivity()), ViewGroup.LayoutParams.WRAP_CONTENT);
//        linearLayout.setLayoutParams(layoutParams);
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//        linearLayout.setGravity(Gravity.CENTER);
//
//        for(final TestAnswer testAnswer : answerMap.get(pageNum)){
//            TextView answerTextView = new TextView(getActivity());
//
//            LinearLayout.LayoutParams answerTextViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CalculateDP.dpToPixel(50, getActivity()));
//            int dp10 = CalculateDP.dpToPixel(10, getActivity());
//            answerTextViewParams.setMargins(dp10, 0, dp10, dp10);
//            answerTextView.setLayoutParams(answerTextViewParams);
//            answerTextView.setGravity(Gravity.CENTER);
//            answerTextView.setBackgroundColor(Color.parseColor("#00bfff"));
//
//            answerTextView.setText(getResources().getString(testAnswer.getTextResId()));
//            linearLayout.addView(answerTextView);
//
//            answerTextView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(questionMap.size() - 2 >= pageCount){
//                        totalScore += testAnswer.getScore();
//                        pageCount += 1;
//                        contentLinearLayout.removeAllViews();
//                        contentLinearLayout.addView(makeQuestionPage(pageCount));
//                    }else{
//                        contentLinearLayout.removeAllViews();
//                        contentLinearLayout.addView(makeResultPage());
//                    }
//                }
//            });
//        }
//        return linearLayout;
//    }
//
//    private View makeResultPage(){
//        String nicotinDependency;
//        String resultDescription;
//
//        LinearLayout linearLayout = new LinearLayout(getActivity());
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(CalculateDP.dpToPixel(300, getActivity()), ViewGroup.LayoutParams.WRAP_CONTENT);
//        linearLayout.setLayoutParams(layoutParams);
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//        linearLayout.setGravity(Gravity.CENTER);
//        linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
//
//        if (totalScore <= LOW_MID_DIVIDER_SCORE) {
//            nicotinDependency = getResources().getString(R.string.fagerstrom_result_nicotin_dependency_low);
//            resultDescription = getResources().getString(R.string.fagerstrom_result_nicotin_dependency_low_description);
//        } else if (totalScore > LOW_MID_DIVIDER_SCORE && totalScore <= MID_HIGH_DIVIDER_SCORE) {
//            nicotinDependency = getResources().getString(R.string.fagerstrom_result_nicotin_dependency_mid);
//            resultDescription = getResources().getString(R.string.fagerstrom_result_nicotin_dependency_mid_description);
//        } else {
//            nicotinDependency = getResources().getString(R.string.fagerstrom_result_nicotin_dependency_high);
//            resultDescription = getResources().getString(R.string.fagerstrom_result_nicotin_dependency_high_description);
//        }
//
//        linearLayout.addView(resultDependencyTextView(nicotinDependency));
//        linearLayout.addView(resultDescriptionTextView(resultDescription));
//
//        return linearLayout;
//    }
//
//    private View resultDescriptionTextView(String dependency){
//        TextView resultTextView = new TextView(getActivity());
//
//        LinearLayout.LayoutParams answerTextViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        int dp10 = CalculateDP.dpToPixel(10, getActivity());
//        answerTextViewParams.setMargins(dp10, 0, dp10, dp10);
//        resultTextView.setLayoutParams(answerTextViewParams);
//        resultTextView.setGravity(Gravity.CENTER);
//        resultTextView.setBackgroundColor(Color.parseColor("#00bfff"));
//        resultTextView.setText(dependency);
//
//        return resultTextView;
//    }
//
//    private View resultDependencyTextView(String dependency){
//        TextView resultTextView = new TextView(getActivity());
//
//        LinearLayout.LayoutParams answerTextViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CalculateDP.dpToPixel(50, getActivity()));
//        int dp10 = CalculateDP.dpToPixel(10, getActivity());
//        answerTextViewParams.setMargins(dp10, 0, dp10, dp10);
//        resultTextView.setLayoutParams(answerTextViewParams);
//        resultTextView.setGravity(Gravity.CENTER);
//        resultTextView.setBackgroundColor(Color.parseColor("#00bfff"));
//        resultTextView.setText(dependency);
//
//        return resultTextView;
//    }
//
//    private class TestQuestion {
//        private int page;
//        private int questionResId;
//
//        public TestQuestion(int page, int questionResId) {
//            this.page = page;
//            this.questionResId = questionResId;
//        }
//
//        public int getPage() {
//            return page;
//        }
//
//        public int getQuestionResId() {
//            return questionResId;
//        }
//    }
//
//    private class TestAnswer {
//        private int page;
//        private int textResId;
//        private int score;
//
//        public TestAnswer(int page, int textResId, int score) {
//            this.page = page;
//            this.textResId = textResId;
//            this.score = score;
//        }
//
//        public int getPage() {
//            return page;
//        }
//
//        public int getTextResId() {
//            return textResId;
//        }
//
//        public int getScore() {
//            return score;
//        }
//    }
//}
