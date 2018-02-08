/*
 * By: James McCune
 * Date: 4-13-16
 */

import java.util.ArrayList;
import java.util.List;

public class DecisionTree {
	

	Tree tree;
	
	public DecisionTree(SampleList sampleList){
		tree  = new Tree();
	}
	
	
	public DecisionNode buildTree(DecisionNode node, SampleList sampleList, int mode, int attributes) {
		
		if(sampleList.size() == 0){
			node.setValue(mode);
			return node;
		}
		else if(attributes == 0){
			node.setValue(mode(sampleList));
			return node;
		}
		else if(sameClassification(sampleList)){
			
//			System.out.println("Same Classification");
//			Main.printSamples(sampleList);
//			System.out.println("Samplelist size: " + sampleList.size());
//			System.out.println("Setting value to " + mode(sampleList));
			node.setValue(mode(sampleList));
			
			return node;
		}
		
		else{
			int best = chooseAttribute(new SampleList(sampleList));
			
//			System.out.println("The Mode of the SampleList is: " + mode(sampleList));
			
			node.feature = best;
			List<SampleList> splitResults = split(new SampleList(sampleList),best);
			for(int i = 0; i < splitResults.size(); i++){
//				System.out.println("Doing split " + i);
				DecisionNode child = new DecisionNode();
				
				
				
				DecisionNode result = buildTree(child, splitResults.get(i), i, attributes - 1);
				
				if(i == 0){
					node.addChild(result, 0);
				}
				else{
					node.addChild(result, 1);
				}
				
//				Main.printSamples(sampleList);
//				node.addChild(result,sampleList.get(0).getAttribute(best).data);
				//System.out.println("added children");
				
			}
			
			return node;
		}
	}


	
	private int chooseAttribute(SampleList sampleList) {
		double bestGain = 0;
		int feature = 0;
		
		for(int i = 0; i < sampleList.get(0).size(); i++){
			double temp = InformationGainOnSplit(new SampleList(sampleList),sampleList.get(0).get(i).numAtt);
//			System.out.println("IG: " + temp);
			if(temp >= bestGain){
				bestGain = temp;
				feature = sampleList.get(0).get(i).numAtt;
			}
		}
//		System.out.println("\n\nBest Gain: " + bestGain);
//		System.out.println("Split Feature: " + feature);
		return feature;
	}

	private double InformationGainOnSplit(SampleList sampleListOriginal, int feature){
		SampleList sampleList = new SampleList(sampleListOriginal);
	
		
		List<SampleList> splitResults;
		double totalPositive = getNumSamplesWithPositiveClassification(sampleList);
		double totalSamples = sampleList.size();
		
		
		double[] weights = new double[2];
		weights[0] = totalPositive/totalSamples;
		weights[1] = (totalSamples - totalPositive)/totalSamples;
		double weightedEntropy = 0;

		
//		System.out.println("\nSplitting on feature: " + feature);
		
//		System.out.println("\nTotal Positive: " + totalPositive);
//		System.out.println("Total Samples: " + totalSamples);
		

			splitResults = split(sampleList,feature);
			
//			System.out.println("\nHaves_____________________________________");
//			Main.printSamples(splitResults.get(0));
//			
//			System.out.println("\nHave Nots_________________________________");
//			Main.printSamples(splitResults.get(1));
			
			
			
			
			for(int j = 0; j < splitResults.size(); j++){
				
				double numPosSamples = getNumSamplesWithPositiveClassification(splitResults.get(j));
				double numSamples = splitResults.get(j).size();
				double prob = (numPosSamples/numSamples);
				double entropy = calcEntropy(prob);
				weightedEntropy += (weights[j] * entropy);
//				System.out.println("\nNum Positive Samples: " + numPosSamples);
//				System.out.println("Num Samples: " + numSamples);
//				System.out.println("Entropy: " + entropy);
//				System.out.println("Weight: " + weights[j]);
//				System.out.println("Weighted Entropy: " + (weights[j]*entropy));
				
			}
//			System.out.println("Information Gain: " + (1 - weightedEntropy));
		
		return (1 - weightedEntropy);
	}
	
	private List<SampleList> split(SampleList list, int feature){
		
		SampleList sampleList = new SampleList(list);
		SampleList haves = new SampleList();
		SampleList havenots = new SampleList();
		
		for(int i = 0; i < sampleList.size(); i++){
			if(sampleList.get(i).getAttribute(feature) != null){
				if(sampleList.get(i).getAttribute(feature).data == 0){
					sampleList.get(i).removeAttribute(feature);
					havenots.add(sampleList.get(i));
				}
				else{
					sampleList.get(i).removeAttribute(feature);
					haves.add(sampleList.get(i));
				}
			}
			
		}
		
		List<SampleList> split = new ArrayList<>();
		split.add(havenots);
		split.add(haves);
		
		return split;
		
	}
	
	private double getNumSamplesWithPositiveClassification(SampleList sampleList){
		double num = 0;
		for(int i = 0; i < sampleList.size(); i++){
			if(sampleList.get(i).classification == 1){
				num++;
			}
		}
		return num;
	}
	
	private double calcEntropy(double probability){

		
		double entropy =  (-(probability*(Math.log10(probability)/Math.log10(2))) - ((1 - probability)*(Math.log10(1-probability)/Math.log10(2))));
		
		if(Double.isNaN(entropy)){
			entropy = 0;
		}
		return entropy;
	}
	
	private boolean sameClassification(SampleList leftlist) {
		
		int comparitor = leftlist.get(0).classification;
		for(int i = 0; i < leftlist.size(); i++){
			if(leftlist.get(i).classification != comparitor){
				return false;
			}
		}
		return true;
	}
	
	private int mode(SampleList sampleList){
		int one = 0;
		int zero = 0;
		
		for(int i = 0; i < sampleList.size(); i++){
			if(sampleList.get(i).classification == 1){
				one++;
			}
			else if(sampleList.get(i).classification == 0){
				zero++;
			}
		}
		
		if(one > zero){
			return 1;
		}
		else 
			return 0;
		
	}
///Users/jmccune/Documents/test

	public static int query(DecisionNode node,Sample query) {
		int result = 0;
		
		if(node.feature == 0){
			System.out.println(node.value);
			
			result = node.value;
		}
		
//		System.out.println("Feature: " + node.feature + " \t Attribute: " + query.getAttribute(node.feature));
		else if(query.getAttribute(node.feature).data == 1){
			System.out.println("Going right");
			result = query(node.rightchild, query);
		}
		else if(query.getAttribute(node.feature).data == 0){
			System.out.println("Going left");
			result = query(node.leftchild, query);
		}
		
		return result;
	}
	
	

}
