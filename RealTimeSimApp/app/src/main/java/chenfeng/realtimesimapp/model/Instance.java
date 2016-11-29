package chenfeng.realtimesimapp.model;

import java.util.ArrayList;

/**
 * Created by yuxiang on 11/28/16.
 */

public class Instance {

    private int taskID;
    private int instanceNum;
    private ArrayList<Integer> startTime, endTime;

    public Instance(int id, int num, ArrayList<Integer> start, ArrayList<Integer> end){
        taskID = id;
        instanceNum = num;
        startTime = start;
        endTime = end;
    }

    public int getTaskID(){
        return taskID;
    }

    public int getInstanceNum(){
        return instanceNum;
    }

    public ArrayList<Integer> getStartTime(){
        return startTime;
    }

    public ArrayList<Integer> getEndTime(){
        return endTime;
    }

}
