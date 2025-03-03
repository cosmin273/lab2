package ro.usv.rf;

import java.util.Arrays;
import java.util.Map;

import static ro.usv.rf.FileUtils.normalizeLearningSet;

public class MainClass {

	public static void main(String[] args) {
		double[][] patternSet = FileUtils.readLearningSetFromFile("in.txt");
		int numberOfPatterns = patternSet.length;
		int numberOfFeatures = patternSet[0].length;
		for (double v[] : patternSet) {
			System.out.println(Arrays.toString(v));
		}
		System.out.println("Date normalizate");
		double[][] nor = normalizeLearningSet(patternSet);
		for (double v[] : nor) {
			System.out.println(Arrays.toString(v));
		}

		for (int j = 0; j < numberOfFeatures; j++) {
			double[] feature = new double[numberOfPatterns];
			for (int i = 0; i < numberOfPatterns; i++) {
				feature[i] = patternSet[i][j];
			}
			System.out.println("Feature average is:" + StatisticsUtils.calculateFeatureAverage(feature));
		}

		Map<Pattern, Integer> patternsMap = StatisticsUtils.getPatternsMapFromInitialSet(patternSet);

		System.out.println(patternsMap.toString());


		double[] weightedAverages = StatisticsUtils.calculateWeightedAverages(patternsMap, numberOfFeatures);
		System.out.println(Arrays.toString(weightedAverages));
		double[] dispersion = StatisticsUtils.calculateDispersion(patternsMap, numberOfFeatures);
		System.out.println("f)" + Arrays.toString(dispersion));
		System.out.print("e)");
		StatisticsUtils.calculateFrequencyOfOccurrence(patternsMap, numberOfFeatures);
		StatisticsUtils.displayCovarianceMatrix(patternSet);
		System.out.print("h)");
		StatisticsUtils.displayCorrelationMatrix(patternSet, patternsMap, numberOfFeatures);

		double[] averageSquareDeviation = StatisticsUtils.calculateAverageSquareDeviation(patternsMap, numberOfFeatures);
		System.out.println("i)" + Arrays.toString(averageSquareDeviation));
		double[][] scaledData = StatisticsUtils.ScalingFormula(patternSet,patternsMap, numberOfFeatures);
		System.out.println("Transformed dataset (autoscaled values):");
		for (int i = 0; i < scaledData.length; i++) {
			System.out.printf("%d.", i + 1);
			for (double value : scaledData[i]) {
				System.out.printf("   %.2f", value);
			}
			System.out.println();
		}
		System.out.println("*********************LAB3*******************");
		double[][] distnate=new double[patternSet.length][patternSet.length];
		for(int i=0;i<patternSet.length;i++){
			for(int j=0;j<patternSet.length;j++)
				distnate[i][j]=DistanceUtils.distCityBlock(patternSet[i],patternSet[j] );
		}
		for(int i=0;i<patternSet.length;i++){
			for(int j=0;j<patternSet.length;j++)
				System.out.printf(distnate[i][j]+" ");
			System.out.println();
		}
		System.out.println("Cebisev");
		for(int i=0;i<patternSet.length;i++){
			for(int j=0;j<patternSet.length;j++)
				distnate[i][j]=DistanceUtils.distCebisev(patternSet[i],patternSet[j] );
		}
		for(int i=0;i<patternSet.length;i++){
			for(int j=0;j<patternSet.length;j++)
				System.out.printf(distnate[i][j]+" ");
			System.out.println();
		}
		DistanceMatrix distante=new DistanceMatrix(patternSet);
		System.out.println(distante);
		double[][] vecini=distante.neighbors(1);
		for(int i=0;i<vecini.length;i++){
			for(int j=0;j<vecini[0].length;j++)
				System.out.printf(vecini[i][j]+" ");
			System.out.println();
		}
		//distante.toString();
		/*System.out.println(DistanceUtils.distEuclid(patternSet[0],patternSet[1] ));
		System.out.println(DistanceUtils.distCebisev(patternSet[0],patternSet[1]));
		System.out.println(DistanceUtils.distCityBlock(patternSet[0],patternSet[1]));*/

	}

}
