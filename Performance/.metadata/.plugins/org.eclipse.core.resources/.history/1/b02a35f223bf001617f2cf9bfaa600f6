package analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import model.*;

public class ExecuteAnalysis {

	private static double[] EDF_preEmptionArr, LLF_preEmptionArr, workloadArr;

	public static void main(String[] args) {

		int numTasks = 2;
		int numSets = 10;
		int maxP = 10;

		runAnalysis(10, numTasks, numSets, maxP);

		System.out.println(Arrays.toString(workloadArr));
		System.out.println(Arrays.toString(EDF_preEmptionArr));
		System.out.println(Arrays.toString(LLF_preEmptionArr));

	}

	private static void runAnalysis(int numSamples, int numTasks, int numSets, int maxP) {
		EDF_preEmptionArr = new double[numSamples];
		LLF_preEmptionArr = new double[numSamples];
		workloadArr = new double[numSamples];

		for (int i = 0; i < numSamples; i++) {
			TaskGenerator myTaskGenerator = new TaskGenerator(numTasks, numSets, maxP);
			workloadArr[i] = myTaskGenerator.getAverageWorkload();
			EDF_preEmptionArr[i] = myTaskGenerator.getAveragePreEmptionEDF();
			LLF_preEmptionArr[i] = myTaskGenerator.getAveragePreEmptionLLF();
		}

	}

}
