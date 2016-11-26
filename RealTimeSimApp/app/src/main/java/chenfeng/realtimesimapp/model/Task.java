package chenfeng.realtimesimapp.model;

/**
 * Created by chenfeng on 26/11/16.
 */
public class Task {

    //from user input
    private int c, p, d;

    //program assigned & generated
    private static int id;
    private boolean isDropped;
    private int taskInstance, startTime, endTime;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Constructors
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public Task(int c, int p, int d) {
        this.c = c;
        this.p = p;
        this.d = d;
        this.isDropped = false;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Getters and Setters
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void setC(int c) {
        this.c = c;
    }

    public int getC() {
        return c;
    }

    public void setP(int p) {
        this.p = p;
    }

    public int getP() {
        return p;
    }

    public void setD(int d) {
        this.d = d;
    }

    public int getD() {
        return d;
    }



}
