package chenfeng.realtimesimapp.model;

import java.util.ArrayList;

/**
 * Created by chenfeng on 22/11/16.
 */
public class scheduleRMS {

    //program assigned & generated
    private static int id;
    private boolean isDropped;

    private int taskInstance, startTime, endTime;

    private static int numTasks;
    public static ArrayList<Integer> startTimeList, endTimeList, taskIdList;

    public scheduleRMS() {
        //Init tasks
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

        numTasks = 3;

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Task ArrayList Functions
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public static int getNumTasks() {
        return numTasks;
    }

    public static ArrayList<Integer> getStartList() {
        return startTimeList;
    }

    public static ArrayList<Integer> getEndList() {
        return endTimeList;
    }

    public static ArrayList<Integer> getIdList() {
        return taskIdList;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Getters and Setters
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setIsDropped(boolean b) {
        this.isDropped = b;
    }

    public boolean getIsDropped() {
        return isDropped;
    }

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
