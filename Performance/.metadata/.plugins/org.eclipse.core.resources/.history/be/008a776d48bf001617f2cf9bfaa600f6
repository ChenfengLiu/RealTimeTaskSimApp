package analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import model.*;

public class TaskGenerator {

	// one sample which contains a set of Task sets
	private ArrayList<ArrayList<Task>> taskSets;
	// Average workload of one sample
	private double workload;
	// Average preemption of one sample
	private double preemptionEDF, preemptionLLF;

	////////////////////////////////////////////////////////////////////////////////////////////////
	// Constructor
	////////////////////////////////////////////////////////////////////////////////////////////////
	public TaskGenerator(int numTasks, int numSets, int maxP) {
		taskSets = new ArrayList<ArrayList<Task>>();
		generateSample(numTasks, numSets, maxP);
		workload = calAverageWorkload();
		preemptionEDF = getAveragePreEmptionEDF(taskSets);
		preemptionLLF = getAveragePreEmptionLLF(taskSets);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	// Generate One Sample & Generator Helper Functions
	////////////////////////////////////////////////////////////////////////////////////////////////
	private void generateSample(int numTasks, int numSets, int maxP) {
		ArrayList<Task> oneSet;
		Task t;
		Random ran = new Random();
		for (int i = 0; i < numSets; i++) {
			oneSet = new ArrayList<>();
			for (int j = 0; j < numTasks; j++) {
				t = new Task(ran.nextInt(maxP - 1) + 1, ran.nextInt(maxP - 2) + 2, j);
				oneSet.add(t);
			}
			// add to sample if schedulable
			if (isSchedulable(oneSet)) {
				sort(oneSet);
				taskSets.add(oneSet);
			} else {
				i = i - 1;
			}

		}
	}

	private void sort(ArrayList<Task> tasks) {
		for (int i = 1; i < tasks.size(); i++) {
			Task curr = tasks.get(i);
			for (int j = i - 1; j >= 0 && curr.getP() < tasks.get(j).getP(); j--) {
				Collections.swap(tasks, j, j + 1);
			}
		}
	}

	private Boolean isSchedulable(ArrayList<Task> tasks) {
		double result = 0;
		for (int i = 0; i < tasks.size(); i++) {
			result += (double) tasks.get(i).getC() / tasks.get(i).getP();
		}
		if (result <= 1)
			return true;
		else
			return false;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////
	// Workload Helper Function
	////////////////////////////////////////////////////////////////////////////////////////////////

	private double calAverageWorkload() {
		double result = 0.0;
		for (int i = 0; i < taskSets.size(); i++) {
			for (int j = 0; j < taskSets.get(0).size(); j++) {
				result += (double) taskSets.get(i).get(j).getC() / taskSets.get(i).get(j).getP();
			}
		}
		// calculate average workload
		result = result / taskSets.size();
		return result;

	}
	////////////////////////////////////////////////////////////////////////////////////////////////
	// Preemption Helper Function
	////////////////////////////////////////////////////////////////////////////////////////////////

	private double getAveragePreEmptionEDF(ArrayList<ArrayList<Task>> taskSets) {
		double result = 0.0;
		for (int i = 0; i < taskSets.size(); i++) {
			ScheduleEDF mEDFScheduler = new ScheduleEDF(taskSets.get(i));
			result += mEDFScheduler.calNumPreEmption();
		}
		return result / taskSets.size();
	}

	private double getAveragePreEmptionLLF(ArrayList<ArrayList<Task>> taskSets) {
		double result = 0.0;
		for (int i = 0; i < taskSets.size(); i++) {
			ScheduleLLF mLLFScheduler = new ScheduleLLF(taskSets.get(i));
			result += mLLFScheduler.calNumPreEmption();
		}
		return result / taskSets.size();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	// Getters
	////////////////////////////////////////////////////////////////////////////////////////////////
	// public ArrayList<ArrayList<Task>> getTaskSets() {
	// return taskSets;
	// }

	public double getAverageWorkload() {
		return workload;
	}

	public double getAveragePreEmptionEDF() {
		return preemptionEDF;
	}

	public double getAveragePreEmptionLLF() {
		return preemptionLLF;
	}

}
