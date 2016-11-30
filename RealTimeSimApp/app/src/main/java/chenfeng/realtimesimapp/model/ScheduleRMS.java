package chenfeng.realtimesimapp.model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by chenfeng on 22/11/16.
 */
public class ScheduleRMS {

    private ArrayList<Task> tListInput;
    private int simTime;

    private ArrayList<Instance> tListProcessed;

    private int[] idArr, startTimeArr, endTimeArr, instanceArr;

    public ScheduleRMS(ArrayList<Task> tasks) {
        //calculate simulation time
        tListInput = tasks;
        simTime = simTime(tasks, 0, tasks.size());

        System.out.println("input is: " + tListInput.get(1).getId() + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        idArr = new int[simTime];
        startTimeArr = new int[simTime];
        endTimeArr = new int[simTime];
        instanceArr = new int[simTime];

        tListProcessed = new ArrayList<>();
        runScheduler(tListInput);

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //EDF Scheduler helper functions
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void runScheduler(ArrayList<Task> tasks) {
        if (isSchedulable(tasks)) {
            //create all task instance with different arrive times
            createInstance(tListInput);
            simulate(tListProcessed, simTime);
        } else {
            idArr = null;
        }
        System.out.println("RMS DONE!!!!!!!!");
    }

    private void simulate(ArrayList<Instance> instances, int sTime) {
        ArrayList<Integer> indexList;

        for (int i = 0; i < sTime; i++) {
            //find all ready tasks at current time
            indexList = findReadyTasks(i, instances);

            //choose one instance to execute
            int chosen = chooseInstance(i, indexList);

            //execute the instance
            runInstance(i, chosen);

        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Simulation helper functions
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void createInstance(ArrayList<Task> tasks) {

        for (Task t : tasks) {
            int numInstance = simTime / t.getP();
            for (int i = 0; i < numInstance; i++) {
                Instance mInstance = new Instance(t.getId(), i * t.getP(), t.getC(), t.getP(), i);
                tListProcessed.add(mInstance);
            }
        }
    }

    private ArrayList<Integer> findReadyTasks(int currentTime, ArrayList<Instance> instances) {
        ArrayList<Integer> readyInstanceIndex = new ArrayList<>();
        for (int i = 0; i < instances.size(); i++) {
            //if arrived, and not finished execution
            if (instances.get(i).getArriveTime() <= currentTime && instances.get(i).getC() > 0) {
                readyInstanceIndex.add(i);
            }
        }
        return readyInstanceIndex;
    }

    private int chooseInstance(int currentTime, ArrayList<Integer> index) {
        int target = -1;
        int min = Integer.MAX_VALUE;
        for (int i : index) {
            if ((tListProcessed.get(i).getP()) < min) {
                min = tListProcessed.get(i).getP();
                target = i;
            }
        }
        return target;
    }

    private void runInstance(int currentTime, int index) {
        if (index == -1) {
            idArr[currentTime] = -1;
            startTimeArr[currentTime] = -1;
            endTimeArr[currentTime] = -1;
            instanceArr[currentTime] = -1;
        } else {
            idArr[currentTime] = tListProcessed.get(index).getId();
            startTimeArr[currentTime] = currentTime;
            endTimeArr[currentTime] = currentTime + 1;
            instanceArr[currentTime] = tListProcessed.get(index).getInstance();

            tListProcessed.get(index).executeC();
        }
    }

    private Boolean isSchedulable(ArrayList<Task> tasks) {
        sort(tasks);
        return exactTest(tasks);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Simulation time helper functions (LCM, Euclidean algorithm)
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private int simTime(ArrayList<Task> tasks, int start, int end) {
        if ((end - start) == 1) return lcm(tasks.get(start).getP(), tasks.get(end - 1).getP());
        else return (lcm(tasks.get(start).getP(), simTime(tasks, start + 1, end)));
    }

    private int lcm(int a, int b) {
        if (a == 0 || b == 0) {
            return 0;
        }
        return (a * b) / gcd(a, b);
    }

    private int gcd(int a, int b) {
        int r = 0;
        do {
            r = a % b;
            a = b;
            b = r;
        } while (b != 0);
        return a;
    }


    private void sort(ArrayList<Task> tasks){
        for (int i = 1; i < tasks.size(); i++){
            Task curr = tasks.get(i);
            for(int j = i - 1; j >= 0; j--){
                if(curr.getP() > tasks.get(j).getP() && j != i - 1){
                    Collections.swap(tasks, i, j + 1);
                    break;
                }
            }
        }
    }

    private boolean exactTest(ArrayList<Task> tasks){
        int[] time = new int[tasks.size()];

        for (int i = 0; i < tasks.size(); i++){
            time[0] += tasks.get(i).getC();
        }
        for (int i = 1; i < tasks.size(); i++){
            for (int j = 0; j < tasks.size(); j++){
                time[i] += tasks.get(j).getC() * (int) Math.ceil((double) time[i - 1] / tasks.get(j).getP());
            }
            if (time[i] > tasks.get(tasks.size() - 1).getP()){
                return false;
            } else if (time[i] == time[i - 1]){
                return true;
            }
        }
        return false;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Getters of int arrays: id, startTime, endTime, instance
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public int[] getIdArr() {
        return idArr;
    }

    public int[] getStartTimeArr() {
        return startTimeArr;
    }

    public int[] getEndTimeArr() {
        return endTimeArr;
    }

    public int[] getInstanceArr() {
        return instanceArr;
    }

}
