package analysis;

import java.text.DecimalFormat;

public class ExecuteAnalysis {

	private static double[] EDF_preEmptionArr, LLF_preEmptionArr, workloadArr;

	public static void main(String[] args) {

		int numTasks = 3;
		int numSets = 10;
		int maxP = 30;
		int numSamples = 50;

		runAnalysis(numSamples, numTasks, numSets, maxP);
		
		sortByWorkload(numSamples);
		resultToString(numSamples);

	}

	private static void runAnalysis(int numSamples, int numTasks, int numSets, int maxP) {
		EDF_preEmptionArr = new double[numSamples];
		LLF_preEmptionArr = new double[numSamples];
		workloadArr = new double[numSamples];

		for (int i = 0; i < numSamples; i++) {
			TaskGenerator myTaskGenerator = new TaskGenerator(numTasks, numSets, maxP);
			DecimalFormat df = new DecimalFormat("#.###");
			workloadArr[i] = Double.parseDouble(df.format(myTaskGenerator.getAverageWorkload()));
			EDF_preEmptionArr[i] = myTaskGenerator.getAveragePreEmptionEDF();
			LLF_preEmptionArr[i] = myTaskGenerator.getAveragePreEmptionLLF();
		}

	}

	private static void sortByWorkload(int numSamples) {

		for (int i = 0; i < numSamples - 1; i++) {
			double min = Double.MAX_VALUE;
			int minIndex = -1;
			for (int j = i; j < numSamples; j++) {
				if(workloadArr[j] < min){
					min = workloadArr[j];
					minIndex = j;
				}
			}
			if(minIndex != -1){
				swap(i, minIndex);
			}
			
		}
	}
	private static void swap(int a, int b){
		double tmpW = workloadArr[a];
		double tmpE = EDF_preEmptionArr[a];
		double tmpL = LLF_preEmptionArr[a];
		workloadArr[a] = workloadArr[b];
		EDF_preEmptionArr[a] = EDF_preEmptionArr[b];
		LLF_preEmptionArr[a] = LLF_preEmptionArr[b];
		workloadArr[b] = tmpW;
		EDF_preEmptionArr[b] = tmpE;
		LLF_preEmptionArr[b] = tmpL;
		
	}

	private static void resultToString(int numSamples) {
		for (int i = 0; i < numSamples; i++) {
			System.out.print(workloadArr[i] + " ");
		}
		System.out.println();
		for (int i = 0; i < numSamples; i++) {
			System.out.print(EDF_preEmptionArr[i] + " ");
		}
		System.out.println();
		for (int i = 0; i < numSamples; i++) {
			System.out.print(LLF_preEmptionArr[i] + " ");
		}
		System.out.println();

	}

}
