package chenfeng.realtimesimapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.Display;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import chenfeng.realtimesimapp.model.scheduleRMS;

public class timeScrollingActivity extends AppCompatActivity {

    private CustomView mCustomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_scrolling);

        //draw custom view to layout
        LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.mlinearlayout);
        CustomView mCustomView = new CustomView(this);
        mLinearLayout.addView(mCustomView);

    }

    private class CustomView extends View {

        private Paint mPaint;
        private Rect mRectangle;

        private int numTasks;
        private ArrayList<Integer> startTime, endTime, taskId, colors;
        private ArrayList<Rect> mRectangles;

        private int SCALE_INDEX = 15;
        private int RECT_HEIGHT = 50;
        private int Y_RMS = 100, Y_EDF = 400, Y_LLF = 700;


        public CustomView(Context context) {
            super(context);
            clearLists();
            new scheduleRMS();
            numTasks = scheduleRMS.getNumTasks();
            startTime = scheduleRMS.getStartList();
            endTime = scheduleRMS.getEndList();
            taskId = scheduleRMS.getIdList();

            getRandomColor();
            generateRectList();

            mPaint = new Paint();
            mPaint.setColor(Color.LTGRAY);
        }

        @Override
        protected void onDraw(Canvas canvas) {

            canvas.drawColor(Color.CYAN);
            canvas.drawText("RMS", 0, 3, 20.0f, 50.0f, mPaint);

            for(int i = 0; i< taskId.size(); i++){
                mPaint.setColor(colors.get(taskId.get(i)));
                canvas.drawRect(mRectangles.get(i), mPaint);
            }

        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            Display d = getWindowManager().getDefaultDisplay();
            Point dSize = new Point();
            d.getSize(dSize);
            int width = dSize.x + 1000;
            int height = dSize.y;
            setMeasuredDimension(width, height);

        }
        ////////////////////////////////////////////////////////////////////////////////////////////
        //draw helper function
        ////////////////////////////////////////////////////////////////////////////////////////////
        private void clearLists(){
            startTime = new ArrayList<>();
            endTime = new ArrayList<>();
            taskId = new ArrayList<>();
            colors = new ArrayList<>();
            mRectangles = new ArrayList<>();
        }

        private void getRandomColor() {
            Random ran = new Random();

            for(int i = 0; i< numTasks; i++){
                int randColor = Color.argb(255, ran.nextInt(256), ran.nextInt(256), ran.nextInt(256));
                colors.add(randColor);
            }
        }

        private void generateRectList() {

            for (int i = 0; i < startTime.size(); i++) {
                int x = startTime.get(i);
                int width = endTime.get(i);
                mRectangle = new Rect(x * SCALE_INDEX, Y_RMS, width * SCALE_INDEX, RECT_HEIGHT);
                mRectangles.add(mRectangle);
            }

        }
    }
}
