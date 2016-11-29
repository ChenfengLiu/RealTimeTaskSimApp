package chenfeng.realtimesimapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import chenfeng.realtimesimapp.model.Task;

public class MainActivity extends AppCompatActivity {

    private Task t;
    private ArrayList<Task> tList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Test user input: only a test
        t = new Task(1, 3, 0);
        tList = new ArrayList<>();
        tList.add(t);
        t = new Task(2, 4, 1);
        tList.add(t);
//        t=  new Task(1, 7, 2);
//        tList.add(t);

        final String answer = taskToString(tList);


        Button bt = (Button) findViewById(R.id.bt1);
        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, timeScrollingActivity.class);
                i.putExtra("TASK", answer);
                startActivity(i);
            }
        });
    }

    private String taskToString(ArrayList<Task> tl) {
        int numTask = tl.size();
        String result = numTask + " ";
        for (int i = 0; i < numTask; i++) {
            result += tl.get(i).getC() + " ";
            result += tl.get(i).getP() + " ";
        }
        return result;
    }
}
