package chenfeng.realtimesimapp.model;

import java.util.ArrayList;

/**
 * Created by yuxiang on 11/28/16.
 */

public class Instance {

    private int id, arriveTime, c, p, instance;

    public Instance(int id, int arriveTime, int c, int p, int instance) {
        this.id = id;
        this.arriveTime = arriveTime;
        this.c = c;
        this.p = p;
        this.instance = instance;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Getters
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public int getId() {
        return id;
    }

    public int getArriveTime() {
        return arriveTime;
    }

    public void executeC() {
        c = c - 1;
    }

    public int getC() {
        return c;
    }

    public int getP() {
        return p;
    }

    public int getInstance() {
        return instance;
    }
}
