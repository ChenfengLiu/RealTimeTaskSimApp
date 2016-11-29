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

import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Random;

import chenfeng.realtimesimapp.model.ScheduleRMS;

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

        private int SCALE_INDEX = 100;
        private int RECT_HEIGHT = 100;
        private int Y_RMS = 100, Y_EDF = 400, Y_LLF = 700;
        private int X_PADDING = 200;


        public CustomView(Context context) {
            super(context);

            mPaint = new Paint();
            mPaint.setColor(Color.GRAY);

            clearLists();

            ScheduleRMS mRMSScheduler = new ScheduleRMS();
            numTasks = mRMSScheduler.getNumTasks();
            startTime = mRMSScheduler.getStartList();
            endTime = mRMSScheduler.getEndList();
            taskId = mRMSScheduler.getIdList();


            getRandomColor();
            generateRectList();


        }

        @Override
        protected void onDraw(Canvas canvas) {
            //set background color
            canvas.drawColor(Color.LTGRAY);
            //Draw RMS schedule
            drawRMSSchedule(canvas);
            //Draw EDF schedule
            drawEDFSchedule(canvas);
            //Draw LLF schedule
            drawLLFSchedule(canvas);


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
        private void drawRMSSchedule(Canvas canvas) {

            for (int i = 0; i < taskId.size(); i++) {
                mPaint.setColor(colors.get(taskId.get(i)));
                canvas.drawRect(mRectangles.get(i), mPaint);
            }
            mPaint.setColor(Color.BLACK);
            mPaint.setStrokeWidth(10.0f);

            float lineWidth = (float) endTime.get(endTime.size()-1) * SCALE_INDEX;
            canvas.drawLine(150.0f, (float) Y_RMS, (250.0f + lineWidth) , (float) Y_RMS, mPaint);
        }

        private void drawEDFSchedule(Canvas canvas) {

        }

        private void drawLLFSchedule(Canvas canvas) {

        }

        ////////////////////////////////////////////////////////////////////////////////////////////
        //List helper function
        ////////////////////////////////////////////////////////////////////////////////////////////
        private void clearLists() {
            startTime = new ArrayList<Integer>();
            endTime = new ArrayList<Integer>();
            taskId = new ArrayList<Integer>();
            colors = new ArrayList<Integer>();
            mRectangles = new ArrayList<Rect>();
        }

        private void getRandomColor() {
            Random ran = new Random();

            for (int i = 0; i < numTasks; i++) {
                int randColor = Color.argb(255, ran.nextInt(256), ran.nextInt(256), ran.nextInt(256));
                colors.add(randColor);
            }
        }

        private void generateRectList() {

            for (int i = 0; i < startTime.size(); i++) {
                int x = startTime.get(i) * SCALE_INDEX;
                int width = (endTime.get(i) - startTime.get(i)) * SCALE_INDEX;
                mRectangle = new Rect((x + X_PADDING), Y_RMS, (width + x + X_PADDING), (RECT_HEIGHT + Y_RMS));
                mRectangles.add(mRectangle);
            }

        }
    }
}
