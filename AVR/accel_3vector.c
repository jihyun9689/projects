#define F_CPU 16000000U
#define STATE_START 0
#define STATE_CONNECTED 1
#define STATE_WRIST 2
#define STATE_ANKLE 3


#include <avr/io.h>
#include <avr/interrupt.h>
#include <util/delay.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>



volatile char RECV0,RECV1;
volatile int i;

volatile char SBuf1[100];
volatile char SBuf2[100];

volatile int SCnt1=0;
volatile int SCnt2=0;

volatile double DegRoll1, DegPitch1, DegYaw1, AccelX1, AccelY1, AccelZ1, xyz1;
volatile double DegRoll2, DegPitch2, DegYaw2, AccelX2, AccelY2, AccelZ2, xyz2;
volatile double pre_AccelX1, pre_AccelY1, pre_AccelZ1, pre_xyz1;

volatile int intxyz1=0;
volatile int intxyz2=0;

volatile char buffer1[15]; 
volatile char buffer2[15]; 

void ad1(void);//센서의 각 축값들을 추출
void ad2(void);//센서의 각 축값들을 추출

int FindCommma(char *buf); //센서의 3가지 축을 추출


unsigned char PutChar0(unsigned char c);
void PutString0(unsigned char *s);
unsigned char PutChar1(unsigned char c);
void PutString1 (unsigned char *s);
char a;

int forI1 = 0;
int forI2 = 0;

int state = STATE_START;

char connectBuffer0[40]; 
int connectNum = 0; 

volatile char allBuffer[60];
volatile char wristBuffer[60];
volatile char ankleBuffer[60];
volatile int allNum1 = 0;
volatile int allNum2 = 0;
volatile int statePut_wrist = 0;
volatile int statePut_ankle = 0;
volatile int allSum = 0;


unsigned char PutChar0(unsigned char c){
 
	while(!UCSR0A & 0x20);
		UDR0 = c;
	return 0;
}

void PutString0 (unsigned char *s){

	while (*s != '\0') {
		PutChar0(*s);
		_delay_ms(5);
		s++;
	}


}

unsigned char PutChar1(unsigned char c){
 
	while(!UCSR1A & 0x20);
		UDR1 = c;
	return 0;
}


void PutString1 (unsigned char *s){
	while (*s != '\0') {
		PutChar1(*s);
		s++;
	}
}


void connect(void){
	_delay_ms(1000);
	PORTB = 0x01; //SELEC CONTOL STREAM on
	
	while(!(~PINC&0x01)){	//연결 될때 까지 무한 반복(1), 불꺼지면 빠져나온다.
		_delay_ms(500);
		PutString0("ATO2"); //다중연결과 핸드폰 연결
		PutChar0(0x0D);
		_delay_ms(20);
		
	}
	//_delay_ms(2000);
	PORTB = 0x00;
	_delay_ms(20);
	state = STATE_CONNECTED;
}

//속목
ISR(SIG_USART0_RECV){
	RECV0 = UDR0;
	
	//connectBuffer0[connectNum] = UDR0;
	
	//connectNum++ ; 

	if(state == STATE_CONNECTED){
	
	ad1();

	}
}

 
//발목
ISR(SIG_UART1_RECV){ // with sensor 
	RECV1 = UDR1; 
	
	if(state == STATE_CONNECTED){
	
	ad2();

	}
	
}



int main(void){
	DDRC=0x00;
	
	DDRB=0x01;

    //UCSR0A=0x01;
    UCSR0B=0x98;
    UCSR0C=0x06;
    UBRR0H=0;
    UBRR0L=0x08;	//115200

    //UCSR1A=0x01;
    UCSR1B=0x98;
    UCSR1C=0x06;
    UBRR1H=0;
    UBRR1L=0x08;	//115200
	
	sei();

	if(state == STATE_START)connect();	

	while(1){
		
		
		if(statePut_wrist == 1 && statePut_ankle == 1){
			
			itoa(allSum,buffer2,10);		

			sprintf(allBuffer,"%s%s%s", wristBuffer , ",", ankleBuffer);
			sprintf(allBuffer,"%s%s%s", allBuffer , ",", buffer2);			

			PutString0(allBuffer);
			statePut_wrist = 0;
			statePut_ankle = 0;
			allSum = 0;
			 
			for(forI1 = 0 ; forI1 < 60 ; forI1++){
				allBuffer[forI1] = '\0';
			}	
			forI1 = 0;
		}
		
	}
}


int FindCommma(char *buf){
	int n;
	for(n=0;n<100;n++){
    	if(buf[n]==',')break;
	}
	return n;
}


//손목
void ad1(void){
	int value,value2;
	
	SBuf1[SCnt1]=RECV0; //1byte 수신 	
	

	if(SBuf1[SCnt1]==0x0a && statePut_wrist == 0)//EOD에서 LF가 0a 라서~
	{
		value = FindCommma(SBuf1);
		SBuf1[value]='\0';
		DegRoll1=atof(SBuf1); 
		
		value++;
		value2=FindCommma(&SBuf1[value]);
		SBuf1[value+value2]='\0';
		DegPitch1=atof(&SBuf1[value]); //문자배열을 숫자로~!
		
		value=value+value2 +1;
		value2=FindCommma(&SBuf1[value]);
		SBuf1[value+value2]='\0';
		DegYaw1=atof(&SBuf1[value]); //문자배열을 숫자로~!

		value=value+value2 +1;
		value2=FindCommma(&SBuf1[value]);
		SBuf1[value+value2]='\0';
		AccelX1=atof(&SBuf1[value]);
	
		value=value+value2 +1;
		value2=FindCommma(&SBuf1[value]);
		SBuf1[value+value2]='\0';
		AccelY1=atof(&SBuf1[value]);

		value=value+value2 +1;
		value2=FindCommma(&SBuf1[value]);
		SBuf1[value+value2]='\0';
		AccelZ1=atof(&SBuf1[value]);
		
		for(forI1 = 0 ; forI1 < 20 ; forI1++){
			wristBuffer[forI1] = '\0';
		}

		//PutString0(allbuffer);
		forI1 = 0;
	
		wristBuffer[allNum1] = 0x2A; 
	
		allNum1 = allNum1 + 1;

		/*
		if(pre_AccelX1 != AccelX1){		

			AccelX1 = (int)(AccelX1 * 100);
		
		}
		allSum = allSum + AccelX1;
	
		itoa(AccelX1,buffer1,10);
	
		while(buffer1[forI1] != '\0'){
		
			wristBuffer[allNum1++] = buffer1[forI1++];
		}
	
		forI1 = 0;

		wristBuffer[allNum1] = ',';

		allNum1 = allNum1 + 1;

		if(pre_AccelY1 != AccelY1){		

			AccelY1 = (int)(AccelY1 * 100);
		
		}

		allSum = allSum + AccelY1;
	
		itoa(AccelY1,buffer1,10);
	
		while(buffer1[forI1] != '\0'){
		
			wristBuffer[allNum1++] = buffer1[forI1++];

		}
	
		forI1 = 0;
	
		wristBuffer[allNum1] = ',';

		allNum1 = allNum1 + 1;

		*/
		
		if(pre_AccelZ1 != AccelZ1){		
		
			AccelZ1 = (int)(AccelZ1 * 100);

		}
		
		allSum = allSum + AccelZ1;
	
		itoa(AccelZ1,buffer1,10);
	
		while(buffer1[forI1] != '\0'){
		
			wristBuffer[allNum1++] = buffer1[forI1++];

		}
	
		forI1 = 0 ;
		allNum1 = 0;
		statePut_wrist = 1;

		pre_AccelX1 = AccelX1;
		pre_AccelY1 = AccelY1;
		pre_AccelZ1 = AccelZ1;
	}
 	else if(SBuf1[SCnt1]=='*'){
		SCnt1=-1;

	}
	SCnt1++;
}

void ad2(void){
	int value,value2;
	
	SBuf2[SCnt2]=RECV1; //1byte 수신 	
	

	if(SBuf2[SCnt2]==0x0a && statePut_ankle == 0)//EOD에서 LF가 0a 라서~
	{
		value = FindCommma(SBuf2);
		SBuf2[value]='\0';
		DegRoll2=atof(SBuf2); 
		
		value++;
		value2=FindCommma(&SBuf2[value]);
		SBuf2[value+value2]='\0';
		DegPitch2=atof(&SBuf2[value]); //문자배열을 숫자로~!
		
		value=value+value2 +1;
		value2=FindCommma(&SBuf2[value]);
		SBuf2[value+value2]='\0';
		DegYaw2=atof(&SBuf2[value]); //문자배열을 숫자로~!

		value=value+value2 +1;
		value2=FindCommma(&SBuf2[value]);
		SBuf2[value+value2]='\0';
		AccelX2=atof(&SBuf2[value]);
	
		value=value+value2 +1;
		value2=FindCommma(&SBuf2[value]);
		SBuf2[value+value2]='\0';
		AccelY2=atof(&SBuf2[value]);

		value=value+value2 +1;
		value2=FindCommma(&SBuf2[value]);
		SBuf2[value+value2]='\0';
		AccelZ2=atof(&SBuf2[value]);
			
		xyz2 = (AccelX2 * AccelX2 + AccelY2 * AccelY2 + AccelZ2 * AccelZ2) * 10;
			
		intxyz2 = (int)xyz2;
		

		for(forI2 = 0 ; forI2 < 20 ; forI2++){
			ankleBuffer[forI2] = '\0';
		}				
		
		forI2 = 0;		

		itoa(intxyz2,buffer2,10);		
	
		allSum = allSum + intxyz2;

		while(buffer2[forI2] != '\0'){
		
			ankleBuffer[allNum2++] = buffer2[forI2++];

		}
		
		forI2 = 0 ;
		allNum2 = 0;
		statePut_ankle = 1;

	
	}
 	else if(SBuf2[SCnt2]=='*'){
		SCnt2=-1;

	}
	SCnt2++;
}





