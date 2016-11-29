package chenfeng.realtimesimapp.model;

import java.util.ArrayList;

import static android.R.attr.id;

/**
 * Created by chenfeng on 22/11/16.
 */
public class ScheduleRMS {

    //program assigned & generated
    private int taskInstance, startTime, endTime;

    private int numTasks;
    private ArrayList<Integer> startTimeList, endTimeList, taskIdList;

    public ScheduleRMS() {
        numTasks = 3;
        startTimeList = new ArrayList<>();
        startTimeList.add(0);
        startTimeList.add(1);
        startTimeList.add(3);
        endTimeList = new ArrayList<>();
        endTimeList.add(1);
        endTimeList.add(2);
        endTimeList.add(6);
        taskIdList = new ArrayList<>();
        taskIdList.add(0);
        taskIdList.add(1);
        taskIdList.add(2);


    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Task ArrayList Functions
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public int getNumTasks() {
        return numTasks;
    }

    public ArrayList<Integer> getStartList() {
        return startTimeList;
    }

    public ArrayList<Integer> getEndList() {
        return endTimeList;
    }

    public ArrayList<Integer> getIdList() {
        return taskIdList;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Getters and Setters
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public void setTaskInstance(int i) {
        this.taskInstance = i;
    }

    public int getTaskInstance() {
        return taskInstance;
    }

    public void setStartTime(int s) {
        this.startTime = s;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setEndTime(int e) {
        this.endTime = id;
    }

    public int getEndTime() {
        return endTime;
    }

}
