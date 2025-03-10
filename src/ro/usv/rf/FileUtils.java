package ro.usv.rf;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtils {
	private static final String inputFileValuesSeparator = " ";
	private static final String outputFileValuesSeparator = ",";

	protected static double[][] readLearningSetFromFile(String fileName) {
		//Start with an ArrayList<ArrayList<Double>>
		List<ArrayList<Double>> learningSet = new ArrayList<ArrayList<Double>>();
		// read file into stream, try-with-resources
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			learningSet = stream.map(FileUtils::convertLineToLearningSetRow).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		// convert ArrayList<ArrayList<Double>> to double[][] for performance
		return convertToBiDimensionalArray(learningSet);
	}

	private static double[][] convertToBiDimensionalArray(List<ArrayList<Double>> learningSet) {
		double[][] learningSetArray = new double[learningSet.size()][];
		for (int n = 0; n < learningSet.size(); n++) {
			ArrayList<Double> rowListEntry = learningSet.get(n);
			// get each row double values
			double[] rowArray = new double[learningSet.get(n).size()];
			for (int p = 0; p < learningSet.get(n).size(); p++) {
				rowArray[p] = rowListEntry.get(p);
			}
			learningSetArray[n] = rowArray;
		}
		return learningSetArray;
	}

	private static ArrayList<Double> convertLineToLearningSetRow(String line) {
		ArrayList<Double> learningSetRow = new ArrayList<Double>();
		String[] stringValues = line.split(inputFileValuesSeparator);
		//we need to convert from string to double
		for (int p = 0; p < stringValues.length; p++) {
			learningSetRow.add(Double.valueOf(stringValues[p]));
		}
		return learningSetRow;
	}

	protected static void writeLearningSetToFile(String fileName, double[][] normalizedSet) {
		// first create the output to be written
		StringBuilder stringBuilder = new StringBuilder();
		for (int n = 0; n < normalizedSet.length; n++) // for each row
		{
			//for each column
			for (int p = 0; p < normalizedSet[n].length; p++) {
				//append to the output string
				stringBuilder.append(normalizedSet[n][p] + "");
				//if this is not the last row element
				if (p < normalizedSet[n].length - 1) {
					//then add separator
					stringBuilder.append(outputFileValuesSeparator);
				}
			}
			//append new line at the end of the row
			stringBuilder.append("\n");
		}
		try {
			Files.write(Paths.get(fileName), stringBuilder.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static double[][] normalizeLearningSet(
			double[][] patternSet)
	{
		double[][] normalizedPatternSet = new double[patternSet.length][patternSet[0].length];

		double min[]=new double[patternSet[0].length];
		double max[]=new double[patternSet[0].length];
		Arrays.fill(min,Double.MAX_VALUE);
		Arrays.fill(max,Double.NEGATIVE_INFINITY);
		for(double v[]:patternSet){
			for(int i=0;i<v.length;i++){
				if(min[i]>v[i])
					min[i]=v[i];
				if(max[i]<v[i])
					max[i]=v[i];
			}
		}
		for(int i=0;i<patternSet.length;i++){
			for(int j=0;j<patternSet[0].length;j++){
				normalizedPatternSet[i][j]=(patternSet[i][j]-min[j])/(max[j]-min[j]);
			}

		}
		// TODO .. enter your code here

		return normalizedPatternSet;
	}
}