package com.example.capstone;

/**
 * Created by Kyuuung on 14. 3. 24.
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.MotionEvent;
import android.view.View;


public class TouchView extends View{

    //화면에 글씨 출력하기 위한 paint 객체
    private Paint textP;
    //발생한 이벤트의 종류
    String eventType="";
    //화면에 원을 그리기 위한 페인트 객체
    Paint circleP;
    //터치한 곳의 좌표를 저장할 변수
    int x0, y0, x1, y1;
    int tempx, tempy;


    public TouchView(Context context) {
        super(context);
        init();
    }

    //초기화하는 메소드
    private void init() {
        //Paint 객체 생성 및 필요한 값 세팅하기
        textP = new Paint();
        textP.setTextSize(20);
        textP.setColor(Color.WHITE);
        circleP = new Paint();
        circleP.setColor(Color.GRAY);
        circleP.setStyle(Style.FILL);
    }
    //화면 그리는 메소드
    @Override
    protected void onDraw(Canvas canvas) {
        //터치한 곳의 좌표 출력하기
        canvas.drawText("x0 :"+x0+", y0 :"+y0, 0, 20, textP);
        canvas.drawText("x1 :"+x1+", y1 :"+y1, 0, 40, textP);
        canvas.drawText("eventType :"+eventType, 0, 60, textP);
        canvas.drawText("tempx :"+tempx+", tempy :"+tempy,0,80,textP);
        //터치한 곳에 원그리기
        canvas.drawCircle(x0, y0, 10, circleP);
        //터치한 곳에 직선 그리기((0,y좌표)부터 (500,y좌표)까지 선을 긋는다.)
        canvas.drawLine(0, y0, 720, y0, circleP);
        canvas.drawLine(x0, 0, x0, 1280, circleP);
        //터치한 곳에 원그리기2
        canvas.drawCircle(x1, y1, 10, circleP);
        //터치한 곳에 직선 그리기2((0,y좌표)부터 (500,y좌표)까지 선을 긋는다.)
        canvas.drawLine(0, y1, 720, y1, circleP);
        canvas.drawLine(x1, 0, x1, 1280, circleP);
    }

    //터치를 입력받기 위해서
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //인자로 전달되는 객체에 터치한 곳의 좌표와 이벤트 종류에 대한 정보가 들어있다.

        //이벤트 정보 읽어오기
        //getAction()하면 이벤트에 따라 상수값이 반환된다.
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN :
                eventType="액션 다운!";
                tempx=(int)event.getX();
                tempy=(int)event.getY();
                break;
            case MotionEvent.ACTION_MOVE :
                //eventType="액션 무브!";
                x0=(int)event.getX();
                y0=(int)event.getY();
               // if(tempx-x0)
                if(Math.abs(x0-tempx)<Math.abs(y0-tempy)){
                if(tempy>y0) eventType="왼쪽으로 이동"; //왼쪽
                else eventType="오른쪽으로 이동 "; //오른쪽
                }
                else if(Math.abs(x0-tempx)>100) eventType="점프";
                //if(tempx<x0) eventType="점프";
                break;
            case MotionEvent.ACTION_UP :
                eventType="액션 업!";
                tempx = 0;
                tempy = 0;
                break;
        }
        //터치 이벤트가 발생한 곳의 갯수 얻어오기
        int touchCount = event.getPointerCount();
        switch(touchCount){
            case 1: //1군데 일때
                //x0=(int)event.getX();
               // y0=(int)event.getY();

                break;
            case 2: //2군데 일때
                x0=(int)event.getX(0);
                y0=(int)event.getY(0);
                x1=(int)event.getX(1);
                y1=(int)event.getY(1);
                break;
        }

        //지금 그려진 화면을 무효화 하고 화면을 갱신한다(onDraw호출).
        invalidate();

        //true를 리턴하는 것은 단순 다운터치만이 아니라는 의미.
        return true;
    }
}
