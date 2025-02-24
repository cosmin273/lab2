package ro.usv.rf;

import java.util.Arrays;
import java.util.Map;

import static ro.usv.rf.FileUtils.normalizeLearningSet;

public class MainClass {

	public static void main(String[] args) {
		double[][] patternSet = FileUtils.readLearningSetFromFile("in.txt");
		int numberOfPatterns = patternSet.length;
		int numberOfFeatures = patternSet[0].length;
		for(double v[]:patternSet){
			System.out.println(Arrays.toString(v));
		}
		System.out.println("Date normalizate");
		double[][] nor=normalizeLearningSet(patternSet);
		for(double v[]:nor){
			System.out.println(Arrays.toString(v));
		}
		
		for (int j=0; j<numberOfFeatures; j++)
		{
			double[] feature = new double[numberOfPatterns];
			for (int i=0; i<numberOfPatterns; i++)
			{
				feature[i] = patternSet[i][j];
			}
			System.out.println("Feature average is:" + StatisticsUtils.calculateFeatureAverage(feature));
		}

		Map<Pattern, Integer> patternsMap = StatisticsUtils.getPatternsMapFromInitialSet(patternSet);

		System.out.println(patternsMap.toString());
		
		
		double[] weightedAverages = StatisticsUtils.calculateWeightedAverages(patternsMap, numberOfFeatures);
		System.out.println(Arrays.toString(weightedAverages));
		double[] dispersion=StatisticsUtils.calculateDispersion(patternsMap,numberOfFeatures);
		System.out.println(Arrays.toString(dispersion));

				
	}



}
