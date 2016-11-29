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
    public ArrayList<Integer> startTimeList, endTimeList, taskIdList;

    public ScheduleRMS() {


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
