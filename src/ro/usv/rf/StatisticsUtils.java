package ro.usv.rf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsUtils {
	
	protected static double calculateFeatureAverage(double[] feature)
	{
		double average = 0.0;
		for (int i=0; i<feature.length; i++)
		{
			average += feature[i];
		}
		average = average/feature.length;
		return average;
	}
	
	protected static Map<Pattern, Integer> getPatternsMapFromInitialSet(double[][] patternSet) {
		Map<Pattern, Integer> patternsMap = new HashMap<Pattern, Integer>();
		for(int i=0;i<patternSet.length;i++)
		{
			Pattern patternObj=new Pattern(patternSet[i]);
			if(patternsMap.containsKey(patternObj))
				patternsMap.put(patternObj,patternsMap.get(patternObj)+1);
			else
				patternsMap.put(patternObj,1);
		}


		// enter code here
		return patternsMap;
	}

	public static double[] calculateWeightedAverages(Map<Pattern, Integer> patternsMap, int numberOfFeatures) {
		double[]  weightedAverages = new double[numberOfFeatures];
		double n=0;
		for(Map.Entry<Pattern,Integer> entry:patternsMap.entrySet())
		{
			Pattern patternobj= entry.getKey();
			double weight=entry.getValue();
			double[] patternLine=patternobj.getPatternValues();
			for(int i=0;i<numberOfFeatures;i++) {
				weightedAverages[i] = weightedAverages[i] + patternLine[i] * weight;
			}
			n=n+weight;
		}
		for(int i=0;i<numberOfFeatures;i++)
			weightedAverages[i]=(int)weightedAverages[i]/n;

		//enter code here
		return weightedAverages;
	}
	public static double[] calculateDispersion(Map<Pattern,Integer> patternsMap,int numberOfFeatures){
		double[] dispersionArr =new double[numberOfFeatures];
		double n=0;
		double[] avg =calculateWeightedAverages(patternsMap,numberOfFeatures);
		for(Map.Entry<Pattern,Integer> entry:patternsMap.entrySet())
		{
			Pattern patterObj = entry.getKey();
			double weight=entry.getValue();
			double[] patternV =patterObj.getPatternValues();
			n+=weight;
			for(int i=0;i<patternV.length;i++){
				dispersionArr[i]+=(patternV[i]-avg[i])*(patternV[i]-avg[i])*weight;
			}
		}
		for(int i=0;i<numberOfFeatures;i++)
			dispersionArr[i]=dispersionArr[i]/(n-1);
		return dispersionArr;
	}
	public static double[] calculateFrequencyOfOccurrence(Map<Pattern, Integer> patternsMap, int numberOfFeatures) {
		double[] frequencyArr = new double[numberOfFeatures]; // Vector pentru frecvențe
		double totalPatterns = 0;

		// Calculăm numărul total de pattern-uri
		for (int count : patternsMap.values()) {
			totalPatterns += count;
		}

		// Calculăm frecvența fiecărui feature pe coloane
		Map<Integer, Map<Double, Integer>> columnFrequencyMap = new HashMap<>();

		for (Map.Entry<Pattern, Integer> entry : patternsMap.entrySet()) {
			Pattern patternObj = entry.getKey();
			double[] values = patternObj.getPatternValues();
			int count = entry.getValue();

			for (int col = 0; col < numberOfFeatures; col++) {
				columnFrequencyMap.putIfAbsent(col, new HashMap<>());
				Map<Double, Integer> valueCounts = columnFrequencyMap.get(col);
				valueCounts.put(values[col], valueCounts.getOrDefault(values[col], 0) + count);
			}
		}

		// Calculăm frecvențele normalizate pentru fiecare coloană
		for (int col = 0; col < numberOfFeatures; col++) {
			System.out.println("\nCol: " + col);
			for (Map.Entry<Double, Integer> entry : columnFrequencyMap.get(col).entrySet()) {
				double value = entry.getKey();
				int count = entry.getValue();
				double percentage = (count / totalPatterns) * 100;
				frequencyArr[col] = percentage; // Salvăm frecvența pentru fiecare coloană (suma lor poate depăși 100%)
				System.out.printf("Value: %.1f, Frequency: (%d) %.2f%%%n", value, count, percentage);
			}
		}

		return frequencyArr; // Returnăm vectorul cu frecvențele pentru fiecare coloană
	}
		public static double calculateCovariance(double[] feature1, double[] feature2) {
			int n = feature1.length;

			// Calculăm media fiecărui feature
			double mean1 = 0, mean2 = 0;
			for (int i = 0; i < n; i++) {
				mean1 += feature1[i];
				mean2 += feature2[i];
			}
			mean1 /= n;
			mean2 /= n;

			// Calculăm suma produselor abaterilor de la medie
			double covariance = 0;
			for (int i = 0; i < n; i++) {
				covariance += (feature1[i] - mean1) * (feature2[i] - mean2);
			}

			// Aplicăm formula finală
			return covariance / (n - 1);
		}
	public static double[][] lineToColumn(double[][] dataSet){
		int numRows = dataSet.length;
		int numCols = dataSet[0].length;

		// Transpunem dataset-ul pentru a accesa coloanele corect
		double[][] columns = new double[numCols][numRows];

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				columns[j][i] = dataSet[i][j];
			}
		}
		return  columns;
	}
		public static void displayCovarianceMatrix(double[][] dataSet) {
			int numRows = dataSet.length;
			int numCols = dataSet[0].length;

			// Transpunem dataset-ul pentru a accesa coloanele corect
			double[][] columns = new double[numCols][numRows];

			columns=lineToColumn(dataSet);

			// Calculăm covarianța între fiecare pereche de coloane
			for (int i = 0; i < numCols; i++) {
				for (int j = i; j < numCols; j++) {
					double cov = calculateCovariance(columns[i], columns[j]);
					System.out.printf("%s and %s has covariance %.15f%n",
							java.util.Arrays.toString(columns[i]),
							java.util.Arrays.toString(columns[j]),
							cov);
				}
			}
		}
	public static void displayCorrelationMatrix(double[][] dataSet, Map<Pattern, Integer> patternsMap, int numberOfFeatures) {
		int numRows = dataSet.length;
		int numCols = dataSet[0].length;

		// Transpunem dataset-ul pentru a accesa corect coloanele
		double[][] columns = lineToColumn(dataSet);

		// Calculăm dispersia pentru fiecare caracteristică
		double[] dispersionArr = calculateDispersion(patternsMap, numberOfFeatures);

		// Afișăm matricea de corelație
		for (int i = 0; i < numCols; i++) {
			for (int j = 0; j < numCols; j++) {
				// Calculăm covarianța între caracteristicile i și j
				double covariance = calculateCovariance(columns[i], columns[j]);

				// Calculăm coeficientul de corelație folosind formula
				double correlationCoefficient = covariance / (Math.sqrt(dispersionArr[i]) * Math.sqrt(dispersionArr[j]));

				// Afișăm coeficientul de corelație
				System.out.printf("%.3f\t", correlationCoefficient);
			}
			System.out.println();
		}
	}
	public static double[] calculateAverageSquareDeviation(Map<Pattern, Integer> patternsMap, int numberOfFeatures) {
		// Calculăm dispersia pentru fiecare caracteristică
		double[] dispersionArr = calculateDispersion(patternsMap, numberOfFeatures);
		double[] squareDeviation=new double[numberOfFeatures];
		// Afișăm deviația medie pătratică pentru fiecare caracteristică
		for (int i = 0; i < numberOfFeatures; i++) {
			double deviation = Math.sqrt(dispersionArr[i]);
			squareDeviation[i]=deviation;
		}
		return squareDeviation;
	}
	public static double[][] autoScaleFeatures(double[][] dataSet) {
		int numSamples = dataSet.length;
		int numFeatures = dataSet[0].length;

		// Calculăm media pentru fiecare caracteristică (coloană)
		double[] featureMeans = new double[numFeatures];
		for (int j = 0; j < numFeatures; j++) {
			double sum = 0;
			for (int i = 0; i < numSamples; i++) {
				sum += dataSet[i][j];
			}
			featureMeans[j] = sum / numSamples;
		}

		// Aplicăm formula pentru autoscaling
		double[][] scaledData = new double[numSamples][numFeatures];
		for (int j = 0; j < numFeatures; j++) {
			double sumSquaredDiffs = 0;
			for (int i = 0; i < numSamples; i++) {
				sumSquaredDiffs += Math.pow(dataSet[i][j] - featureMeans[j], 2);
			}
			double sqrtSumSquaredDiffs = Math.sqrt(sumSquaredDiffs);

			// Calculăm valorile scalate pentru fiecare exemplu
			for (int i = 0; i < numSamples; i++) {
				scaledData[i][j] = (dataSet[i][j] - featureMeans[j]) / sqrtSumSquaredDiffs;
			}
		}

		return scaledData;
	}

	// Funcția pentru a afișa rezultatele
	public static void displayTransformedData(double[][] scaledData) {
		for (int i = 0; i < scaledData.length; i++) {
			System.out.printf(String.valueOf(i+1)+". ");
			for (int j = 0; j < scaledData[i].length; j++) {
				// Afișăm valorile scalate cu 2 zecimale
				System.out.printf("%.2f\t", scaledData[i][j]);
			}
			System.out.println();
		}
	}


}
