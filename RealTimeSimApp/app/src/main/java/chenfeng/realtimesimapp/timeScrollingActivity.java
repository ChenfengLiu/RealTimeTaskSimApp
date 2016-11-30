package chenfeng.realtimesimapp;

import android.content.Context;
import android.content.Intent;
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

import chenfeng.realtimesimapp.model.ScheduleEDF;
import chenfeng.realtimesimapp.model.ScheduleRMS;
import chenfeng.realtimesimapp.model.Task;

public class timeScrollingActivity extends AppCompatActivity {

    private CustomView mCustomView;
    private ArrayList<Task> tList;
    private int numTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_scrolling);

        //get task String from Main Activity
        Intent intent = getIntent();
        String tasks = intent.getExtras().getString("TASK");
        tList = new ArrayList<>();
        tList = stringToTaskList(tasks);
        numTasks = tList.size();

        //draw custom view to layout
        LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.mlinearlayout);
        CustomView mCustomView = new CustomView(this);
        mLinearLayout.addView(mCustomView);

    }

    private ArrayList<Task> stringToTaskList(String tString) {
        String[] tArr = tString.split(" ");
        int numTask = Integer.parseInt(tArr[0]);

        Task tTemp;
        ArrayList<Task> tResult = new ArrayList<>();
        for (int i = 0; i < numTask; i++) {
            tTemp = new Task(Integer.parseInt(tArr[i * 2 + 1]), Integer.parseInt(tArr[i * 2 + 2]), i);
            tResult.add(tTemp);
        }

        return tResult;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Create Custom View
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private class CustomView extends View {

        private Paint mPaintRect, mPaintLine, mPaintText;
        private Rect mRectangle;


        //For RMS
        private int[] RMS_Id, RMS_StartTime, RMS_EndTime, RMS_Instance;
//        private ArrayList<Integer> startTime, endTime, taskId, colors;
        private ArrayList<Rect> RMS_Rects;
        private int RMS_lineLength;

        //For EDF
        private int[] EDF_Id, EDF_StartTime, EDF_EndTime, EDF_Instance;
        private ArrayList<Rect> EDF_Rects;
        private int EDF_lineLength;

        //For Canvas
        private int SCALE_INDEX = 100;
        private int RECT_HEIGHT = 100;
        private int Y_RMS = 200, Y_EDF = 550, Y_LLF = 900;
        private int X_PADDING = 250;


        public CustomView(Context context) {
            super(context);
            //
            // Create three Paint object for drawing Rectangles, lines and text.
            //
            mPaintRect = new Paint();
            mPaintLine = new Paint();
            mPaintText = new Paint();

            //Clear ArrayList history
            clearLists();


            //
            //Create three scheduler objects: RMS, EDF, LLF
            //Calculate schedules: RMS, EDF, LLF
            //TODO

            ScheduleRMS mRMSScheduler = new ScheduleRMS();
            numTasks = mRMSScheduler.getNumTasks();
            startTime = mRMSScheduler.getStartList();
            endTime = mRMSScheduler.getEndList();
            taskId = mRMSScheduler.getIdList();

            ScheduleEDF mEDFScheduler = new ScheduleEDF(tList);
            EDF_Id = mEDFScheduler.getIdArr();
            EDF_StartTime = mEDFScheduler.getStartTimeArr();
            EDF_EndTime = mEDFScheduler.getEndTimeArr();
            EDF_Instance = mEDFScheduler.getInstanceArr();
            EDF_Rects = new ArrayList<>();
            EDF_lineLength = EDF_Id.length;

            //Generate random colors for each unique task
            getRandomColor();
            //Generate one Rectangle shape for every task instance
            generateRectList();
            EDF_GenerateRectList();


        }

        @Override
        protected void onDraw(Canvas canvas) {
            //set background color
            canvas.drawColor(Color.LTGRAY);
            //Draw Titles
            float textSize = mPaintText.getTextSize();
            mPaintText.setTextSize(textSize * 5);
            canvas.drawText("RMS Schedule: ", 0, 14, X_PADDING - 100, Y_RMS - 100, mPaintText);
            canvas.drawText("EDF Schedule: ", 0, 14, X_PADDING - 100, Y_EDF - 100, mPaintText);
            canvas.drawText("LLF Schedule: ", 0, 14, X_PADDING - 100, Y_LLF - 100, mPaintText);
            mPaintText.setTextSize(textSize);

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

            int width = EDF_lineLength * SCALE_INDEX + X_PADDING + 150;

            if (width < (dSize.x + 100)) {
                width = dSize.x + 100;
            }
            int height = dSize.y;
            setMeasuredDimension(width, height);

        }

        ////////////////////////////////////////////////////////////////////////////////////////////
        //draw helper function
        ////////////////////////////////////////////////////////////////////////////////////////////
        private void drawRMSSchedule(Canvas canvas) {
            //Draw Rectangles
            for (int i = 0; i < taskId.size(); i++) {
                mPaintRect.setColor(colors.get(taskId.get(i)));
                canvas.drawRect(mRectangles.get(i), mPaintRect);

                //Draw "time" text
                float textSize = mPaintText.getTextSize();
                mPaintText.setTextSize(textSize * 3);
                canvas.drawText((startTime.get(i) + ""), 0, (startTime.get(i) + "").length(), mRectangles.get(i).left, Y_RMS + RECT_HEIGHT + 50, mPaintText);
                mPaintText.setTextSize(textSize);
            }

            //Draw last "time" text
            float textSize = mPaintText.getTextSize();
            mPaintText.setTextSize(textSize * 3);
            canvas.drawText((endTime.get(endTime.size() - 1) + ""), 0, (endTime.get(endTime.size() - 1) + "").length(), mRectangles.get(mRectangles.size() - 1).right
                    , Y_RMS + RECT_HEIGHT + 50, mPaintText);
            mPaintText.setTextSize(textSize);


            //Draw Lines
            mPaintLine.setStrokeWidth(10.0f);
            float lineWidth = (float) endTime.get(endTime.size() - 1) * SCALE_INDEX;
            canvas.drawLine((X_PADDING - 50), (float) (Y_RMS + RECT_HEIGHT), (X_PADDING + lineWidth + 50), (float) (Y_RMS + RECT_HEIGHT), mPaintLine);
        }

        private void drawEDFSchedule(Canvas canvas) {
            if (EDF_Id != null) {
                //Draw Rectangles
                for (int i = 0; i < EDF_Id.length; i++) {
                    if (EDF_Id[i] == -1) continue;
                    mPaintRect.setColor(colors.get(EDF_Id[i]));
                    canvas.drawRect(EDF_Rects.get(i), mPaintRect);

                    //Draw "time" text
                    float textSize = mPaintText.getTextSize();
                    mPaintText.setTextSize(textSize * 3);
                    canvas.drawText((EDF_StartTime[i] + ""), 0, (EDF_StartTime[i] + "").length(), EDF_Rects.get(i).left, Y_EDF + RECT_HEIGHT + 50, mPaintText);
                    mPaintText.setTextSize(textSize);
                }
                //Draw last "time" text
                for (int j = EDF_Id.length - 1; j > 0; j--) {
                    if (EDF_Id[j] != -1) {
                        float textSize = mPaintText.getTextSize();
                        mPaintText.setTextSize(textSize * 3);
                        canvas.drawText((EDF_EndTime[j] + ""), 0, (EDF_EndTime[j] + "").length(), EDF_Rects.get(j).right, Y_EDF + RECT_HEIGHT + 50, mPaintText);
                        mPaintText.setTextSize(textSize);
                    }
                }


            } else {
                //Draw text: "EDF NOT schedulable"
            }

            //Draw Base Line
            mPaintLine.setStrokeWidth(10.0f);
            float lineWidth = (float) EDF_lineLength * SCALE_INDEX;
            canvas.drawLine((X_PADDING - 50), (float) (Y_EDF + RECT_HEIGHT), (X_PADDING + lineWidth + 50), (float) (Y_EDF + RECT_HEIGHT), mPaintLine);

        }

        private void drawLLFSchedule(Canvas canvas) {
            //Draw Rectangles

            //Draw Lines
            mPaintLine.setStrokeWidth(10.0f);
            float lineWidth = (float) endTime.get(endTime.size() - 1) * SCALE_INDEX;
            canvas.drawLine((X_PADDING - 50), (float) (Y_LLF + RECT_HEIGHT), (X_PADDING + lineWidth + 50), (float) (Y_LLF + RECT_HEIGHT), mPaintLine);

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

        private void EDF_GenerateRectList() {
            if (EDF_Id != null) {
                for (int i = 0; i < EDF_Id.length; i++) {
                    if (EDF_Id[i] != -1) {
                        int x = EDF_StartTime[i] * SCALE_INDEX;
                        int width = (EDF_EndTime[i] - EDF_StartTime[i]) * SCALE_INDEX;
                        mRectangle = new Rect((x + X_PADDING), Y_EDF, (width + x + X_PADDING), (RECT_HEIGHT + Y_EDF));
                        EDF_Rects.add(mRectangle);
                    } else {
                        mRectangle = new Rect(0, 0, 0, 0);
                        EDF_Rects.add(mRectangle);
                    }


                }
            }


        }
    }
}
