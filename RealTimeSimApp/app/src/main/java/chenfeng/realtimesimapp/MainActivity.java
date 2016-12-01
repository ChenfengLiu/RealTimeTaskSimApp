package chenfeng.realtimesimapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import chenfeng.realtimesimapp.model.Task;

public class MainActivity extends AppCompatActivity {

    private Task t;
    private int taskID;
    private ArrayList<Task> tList;

    private Button btAdd, btClear, btDraw;
    private EditText etC, etP;
    private TextView tv;
    private StringBuilder sb;
    private String tvString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskID = 0;
        tvString = "\t| Task ID | Execution Time | Period |";
        sb = new StringBuilder(tvString);

        etC = (EditText) findViewById(R.id.etC);
        etP = (EditText) findViewById(R.id.etP);
        tv = (TextView) findViewById(R.id.textView);

        //get User Input
        btAdd = (Button) findViewById(R.id.btAdd);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create new task
                int c = Integer.parseInt(etC.getText().toString());
                int p = Integer.parseInt(etP.getText().toString());
                t = new Task(c,p,taskID);
                tList.add(t);
                taskID++;
                //display new task
                sb.append("\n\t|\t\t\t\t " + t.getId() + "|\t\t\t\t\t\t\t" + t.getC() + "|\t\t\t\t" + t.getP() + "|");
                tv.setText(sb.toString());
            }
        });

        btClear = (Button) findViewById(R.id.btClear);
        btClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskID = 0;
                tvString = "\t|Task ID|\tExecution Time|\tPeriod|";
                sb = new StringBuilder(tvString);
                tList = new ArrayList<Task>();
                tv.setText(tvString);
            }
        });


        //When draw, start timeScrolling Activity
        btDraw = (Button) findViewById(R.id.btDraw);
        btDraw.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String answer = taskToString(tList);
                if (!answer.equals("")) {
                    Intent i = new Intent(MainActivity.this, timeScrollingActivity.class);
                    i.putExtra("TASK", answer);
                    startActivity(i);
                }

            }
        });
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Activity LifeCycle Funcitons
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onResume() {
        super.onResume();
        taskID = 0;
        tvString = "\t|Task ID|\tExecution Time|\tPeriod|";
        sb = new StringBuilder(tvString);
        tList = new ArrayList<Task>();
        tv.setText(tvString);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        taskID = 0;
        tvString = "\t|Task ID|\tExecution Time|\tPeriod|";
        sb = new StringBuilder(tvString);
        tList = new ArrayList<Task>();
        tv.setText(tvString);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Helper Functions
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private String taskToString(ArrayList<Task> tl) {
        int numTask = tl.size();
        String result = numTask + " ";
        for (int i = 0; i < numTask; i++) {
            result += tl.get(i).getC() + " ";
            result += tl.get(i).getP() + " ";
        }
        return result;
    }

    private void addTestTaskSet(int setIndex) {
        if (setIndex == 1) {
            t = new Task(1, 8, 0);
            tList = new ArrayList<>();
            tList.add(t);
            t = new Task(2, 6, 1);
            tList.add(t);
            t = new Task(4, 24, 2);
            tList.add(t);
        } else if (setIndex == 2) {
            t = new Task(3, 12, 0);
            tList = new ArrayList<>();
            tList.add(t);
            t = new Task(3, 12, 1);
            tList.add(t);
            t = new Task(8, 16, 2);
            tList.add(t);
        }

    }
}
