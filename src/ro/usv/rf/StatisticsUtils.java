package ro.usv.rf;

import java.util.HashMap;
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
		double dispersionArr[]=new double[numberOfFeatures];
		double n=0;
		double avg[]=calculateWeightedAverages(patternsMap,numberOfFeatures);
		for(Map.Entry<Pattern,Integer> entry:patternsMap.entrySet())
		{
			Pattern patterObj = entry.getKey();
			double weight=entry.getValue();
			double patternV[]=patterObj.getPatternValues();
			n+=weight;
			for(int i=0;i<patternV.length;i++){
				dispersionArr[i]+=(patternV[i]-avg[i])*(patternV[i]-avg[i])*weight;
			}
		}
		for(int i=0;i<numberOfFeatures;i++)
			dispersionArr[i]=dispersionArr[i]/(n-1);
		return dispersionArr;
	}
}
