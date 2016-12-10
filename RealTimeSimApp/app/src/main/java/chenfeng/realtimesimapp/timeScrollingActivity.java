package chenfeng.realtimesimapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.Display;
import android.view.View;

import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Random;

import chenfeng.realtimesimapp.model.ScheduleEDF;
import chenfeng.realtimesimapp.model.ScheduleLLF;
import chenfeng.realtimesimapp.model.ScheduleRMS;
import chenfeng.realtimesimapp.model.Task;

public class timeScrollingActivity extends AppCompatActivity {

    private CustomView mCustomView;
    private ArrayList<Task> tList;
    private int numTasks;

    //For RMS
    private int[] RMS_Id, RMS_StartTime, RMS_EndTime, RMS_Instance;
    private ArrayList<Rect> RMS_Rects;
    private int RMS_lineLength;

    //For EDF
    private int[] EDF_Id, EDF_StartTime, EDF_EndTime, EDF_Instance;
    private ArrayList<Rect> EDF_Rects;
    private int EDF_lineLength;

    //For LLF
    private int[] LLF_Id, LLF_StartTime, LLF_EndTime, LLF_Instance;
    private ArrayList<Rect> LLF_Rects;
    private int LLF_lineLength;

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

        //Create three scheduler objects: RMS, EDF, LLF
        //Calculate schedules: RMS, EDF, LLF

        ScheduleRMS mRMSScheduler = new ScheduleRMS(tList);
        if (mRMSScheduler.getIdArr() != null) {
            RMS_Id = mRMSScheduler.getIdArr();
            RMS_StartTime = mRMSScheduler.getStartTimeArr();
            RMS_EndTime = mRMSScheduler.getEndTimeArr();
            RMS_Instance = mRMSScheduler.getInstanceArr();
            RMS_Rects = new ArrayList<>();
            RMS_lineLength = RMS_Id.length;
        }

        ScheduleEDF mEDFScheduler = new ScheduleEDF(tList);
        if (mEDFScheduler.getIdArr() != null) {
            EDF_Id = mEDFScheduler.getIdArr();
            EDF_StartTime = mEDFScheduler.getStartTimeArr();
            EDF_EndTime = mEDFScheduler.getEndTimeArr();
            EDF_Instance = mEDFScheduler.getInstanceArr();
            EDF_Rects = new ArrayList<>();
            EDF_lineLength = EDF_Id.length;
        }


        ScheduleLLF mLLFScheduler = new ScheduleLLF(tList);
        if (mLLFScheduler.getIdArr() != null) {
            LLF_Id = mLLFScheduler.getIdArr();
            LLF_StartTime = mLLFScheduler.getStartTimeArr();
            LLF_EndTime = mLLFScheduler.getEndTimeArr();
            LLF_Instance = mLLFScheduler.getInstanceArr();
            LLF_Rects = new ArrayList<>();
            LLF_lineLength = LLF_Id.length;
        }


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

        //For rectangle colors
        private ArrayList<Integer> colors;


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

            colors = new ArrayList<>();

            //Clear ArrayList history
            clearLists();

            //Generate random colors for each unique task
            getRandomColor();
            //Generate one Rectangle shape for every task instance
            RMS_GenerateRectList();
            EDF_GenerateRectList();
            LLF_GenerateRectList();


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

            int width = dSize.x;
            if (width < RMS_lineLength * SCALE_INDEX + X_PADDING + 150) {
                width = RMS_lineLength * SCALE_INDEX + X_PADDING + 150;
            }

            if (width < EDF_lineLength * SCALE_INDEX + X_PADDING + 150) {
                width = EDF_lineLength * SCALE_INDEX + X_PADDING + 150;
            }

            if (width < LLF_lineLength * SCALE_INDEX + X_PADDING + 150) {
                width = LLF_lineLength * SCALE_INDEX + X_PADDING + 150;
            }

            int height = dSize.y;
            setMeasuredDimension(width, height);

        }

        ////////////////////////////////////////////////////////////////////////////////////////////
        //draw helper function
        ////////////////////////////////////////////////////////////////////////////////////////////
        private void drawRMSSchedule(Canvas canvas) {
            if (RMS_Id != null) {
                //Draw Rectangles
                for (int i = 0; i < RMS_Id.length; i++) {
                    if (RMS_Id[i] == -1) continue;
                    mPaintRect.setColor(colors.get(RMS_Id[i]));
                    canvas.drawRect(RMS_Rects.get(i), mPaintRect);

                    //Draw "time" text
                    float textSize = mPaintText.getTextSize();
                    mPaintText.setTextSize(textSize * 3);
                    canvas.drawText((RMS_StartTime[i] + ""), 0, (RMS_StartTime[i] + "").length(), RMS_Rects.get(i).left, Y_RMS + RECT_HEIGHT + 50, mPaintText);
                    mPaintText.setTextSize(textSize);

                    //Draw instance Label
                    textSize = mPaintText.getTextSize();
                    mPaintText.setTextSize(textSize * 2);
                    String label = "T" + RMS_Id[i] + "," + RMS_Instance[i];
                    canvas.drawText(label, 0, label.length(), RMS_Rects.get(i).centerX() - 10, Y_RMS - 10, mPaintText);
                    mPaintText.setTextSize(textSize);

                }
                //Draw last "time" text
                for (int j = RMS_Id.length - 1; j > 0; j--) {
                    if (RMS_Id[j] != -1) {
                        float textSize = mPaintText.getTextSize();
                        mPaintText.setTextSize(textSize * 3);
                        canvas.drawText((RMS_EndTime[j] + ""), 0, (RMS_EndTime[j] + "").length(), RMS_Rects.get(j).right, Y_RMS + RECT_HEIGHT + 50, mPaintText);
                        mPaintText.setTextSize(textSize);
                    }
                }

                float textSize = mPaintText.getTextSize();
                mPaintText.setTextSize(textSize * 3);
                canvas.drawText((RMS_EndTime.length + ""), 0, (RMS_EndTime.length + "").length(), RMS_EndTime.length * 100 + X_PADDING, Y_RMS + RECT_HEIGHT + 50, mPaintText);
                mPaintText.setTextSize(textSize);

                //Draw Base Line
                mPaintLine.setStrokeWidth(10.0f);
                float lineWidth = (float) RMS_lineLength * SCALE_INDEX;
                canvas.drawLine((X_PADDING - 50), (float) (Y_RMS + RECT_HEIGHT), (X_PADDING + lineWidth + 50), (float) (Y_RMS + RECT_HEIGHT), mPaintLine);

            } else {
                //Draw text: "RMS NOT schedulable"
                float textSize = mPaintText.getTextSize();
                mPaintText.setTextSize(textSize * 3);
                canvas.drawText("RMS NOT Schedulable", 0, 19, 400, Y_RMS + 50, mPaintText);
                mPaintText.setTextSize(textSize);
                //Draw Base Line
                mPaintLine.setStrokeWidth(10.0f);
                canvas.drawLine((X_PADDING - 50), (float) (Y_RMS + RECT_HEIGHT), 1000, (float) (Y_RMS + RECT_HEIGHT), mPaintLine);

            }


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

                    //Draw instance Label
                    textSize = mPaintText.getTextSize();
                    mPaintText.setTextSize(textSize * 2);
                    String label = "T" + EDF_Id[i] + "," + EDF_Instance[i];
                    canvas.drawText(label, 0, label.length(), EDF_Rects.get(i).centerX() - 10, Y_EDF - 10, mPaintText);
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

                //Draw Base Line
                mPaintLine.setStrokeWidth(10.0f);
                float lineWidth = (float) EDF_lineLength * SCALE_INDEX;
                canvas.drawLine((X_PADDING - 50), (float) (Y_EDF + RECT_HEIGHT), (X_PADDING + lineWidth + 50), (float) (Y_EDF + RECT_HEIGHT), mPaintLine);

            } else {
                //Draw text: "EDF NOT schedulable"
                float textSize = mPaintText.getTextSize();
                mPaintText.setTextSize(textSize * 3);
                canvas.drawText("EDF NOT Schedulable", 0, 19, 400, Y_EDF + 50, mPaintText);
                mPaintText.setTextSize(textSize);
                //Draw Default Base Line
                mPaintLine.setStrokeWidth(10.0f);
                canvas.drawLine((X_PADDING - 50), (float) (Y_EDF + RECT_HEIGHT), 1000, (float) (Y_EDF + RECT_HEIGHT), mPaintLine);
            }

        }

        private void drawLLFSchedule(Canvas canvas) {
            if (LLF_Id != null) {
                //Draw Rectangles
                for (int i = 0; i < LLF_Id.length; i++) {
                    if (LLF_Id[i] == -1) continue;
                    mPaintRect.setColor(colors.get(LLF_Id[i]));
                    canvas.drawRect(LLF_Rects.get(i), mPaintRect);

                    //Draw "time" text
                    float textSize = mPaintText.getTextSize();
                    mPaintText.setTextSize(textSize * 3);
                    canvas.drawText((LLF_StartTime[i] + ""), 0, (LLF_StartTime[i] + "").length(), LLF_Rects.get(i).left, Y_LLF + RECT_HEIGHT + 50, mPaintText);
                    mPaintText.setTextSize(textSize);

                    //Draw instance Label
                    textSize = mPaintText.getTextSize();
                    mPaintText.setTextSize(textSize * 2);
                    String label = "T" + LLF_Id[i] + "," + LLF_Instance[i];
                    canvas.drawText(label, 0, label.length(), LLF_Rects.get(i).centerX() - 10, Y_LLF - 10, mPaintText);
                    mPaintText.setTextSize(textSize);
                }
                //Draw last "time" text
                for (int j = LLF_Id.length - 1; j > 0; j--) {
                    if (LLF_Id[j] != -1) {
                        float textSize = mPaintText.getTextSize();
                        mPaintText.setTextSize(textSize * 3);
                        canvas.drawText((LLF_EndTime[j] + ""), 0, (LLF_EndTime[j] + "").length(), LLF_Rects.get(j).right, Y_LLF + RECT_HEIGHT + 50, mPaintText);
                        mPaintText.setTextSize(textSize);
                    }
                }

                //Draw Base Line
                mPaintLine.setStrokeWidth(10.0f);
                float lineWidth = (float) LLF_lineLength * SCALE_INDEX;
                canvas.drawLine((X_PADDING - 50), (float) (Y_LLF + RECT_HEIGHT), (X_PADDING + lineWidth + 50), (float) (Y_LLF + RECT_HEIGHT), mPaintLine);

            } else {
                //Draw text: "EDF NOT schedulable"
                float textSize = mPaintText.getTextSize();
                mPaintText.setTextSize(textSize * 3);
                canvas.drawText("LLF NOT Schedulable", 0, 19, 400, Y_LLF + 50, mPaintText);
                mPaintText.setTextSize(textSize);
                //Draw Default Base Line
                mPaintLine.setStrokeWidth(10.0f);
                canvas.drawLine((X_PADDING - 50), (float) (Y_LLF + RECT_HEIGHT), 1000, (float) (Y_LLF + RECT_HEIGHT), mPaintLine);
            }

        }

        ////////////////////////////////////////////////////////////////////////////////////////////
        //List helper function
        ////////////////////////////////////////////////////////////////////////////////////////////
        private void clearLists() {

        }

        private void getRandomColor() {
            Random ran = new Random();
            for (int i = 0; i < numTasks; i++) {
                int randColor = Color.argb(255, ran.nextInt(256), ran.nextInt(256), ran.nextInt(256));
                colors.add(randColor);
            }
        }

        private void RMS_GenerateRectList() {
            if (RMS_Id != null) {
                for (int i = 0; i < RMS_Id.length; i++) {
                    if (RMS_Id[i] != -1) {
                        int x = RMS_StartTime[i] * SCALE_INDEX;
                        int width = (RMS_EndTime[i] - RMS_StartTime[i]) * SCALE_INDEX;
                        mRectangle = new Rect((x + X_PADDING), Y_RMS, (width + x + X_PADDING), (RECT_HEIGHT + Y_RMS));
                        RMS_Rects.add(mRectangle);
                    } else {
                        mRectangle = new Rect(0, 0, 0, 0);
                        RMS_Rects.add(mRectangle);
                    }

                }//End for loop
            }//End if
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

                }//End for loop
            }//End if
        }

        private void LLF_GenerateRectList() {
            if (LLF_Id != null) {
                for (int i = 0; i < LLF_Id.length; i++) {
                    if (LLF_Id[i] != -1) {
                        int x = LLF_StartTime[i] * SCALE_INDEX;
                        int width = (LLF_EndTime[i] - LLF_StartTime[i]) * SCALE_INDEX;
                        mRectangle = new Rect((x + X_PADDING), Y_LLF, (width + x + X_PADDING), (RECT_HEIGHT + Y_LLF));
                        LLF_Rects.add(mRectangle);
                    } else {
                        mRectangle = new Rect(0, 0, 0, 0);
                        LLF_Rects.add(mRectangle);
                    }

                }//End for loop
            }//End if
        }
    }
}
