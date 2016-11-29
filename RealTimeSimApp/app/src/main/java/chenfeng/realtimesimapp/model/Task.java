package chenfeng.realtimesimapp.model;

/**
 * Created by chenfeng on 26/11/16.
 */
public class Task {

    //from user input
    private int c, p, d;

    //program assigned & generated
    private static int count;
    private int id;
    private int taskInstance, startTime, endTime;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Constructors
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public Task(int c, int p, int d) {
        this.c = c;
        this.p = p;
        this.d = d;
        id = count++;
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

    public int getId(){ return id; }

    public int getCount() {
        return count;
    }

    public void clear() {
        count = 0;
    }

}
