/*
 * By: James McCune
 * Date: 4-13-16
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;


public class Main {
	
	static int numAttributes = 10;
	static int numSamples = 100;
	

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
//		int numAttributes = 4;
//		int numSamples = 7;
		
		
		SampleList sampleList = new SampleList();
//uncomment the 2 lines below to allow user input file
//		BufferedReader buffreader = initializeInput(scanner);
//		sampleList = parseFile(buffreader);
//		
		populateSamples(sampleList, numAttributes, numSamples);
		
		printSamples(sampleList);

		
		DecisionTree dt = new DecisionTree(sampleList);
		dt.buildTree(dt.tree.root, sampleList, 0, numAttributes);

		System.out.println("\n\nThe decision tree is...\n");
		printTree(dt.tree.root, 1);
		
//		SampleList temp = new SampleList();
//		Sample query = getQuery(scanner,numAttributes);
//		temp.add(query);
//		printSamples(temp);
		
		
		while(true){
			Sample query = getQuery(scanner,numAttributes);
			int result = DecisionTree.query(dt.tree.root, query);
			System.out.println("The decision is: " + result);
		}
//		scanner.close();
	}
	
	/*
	 * Gets user query
	 */
	private static Sample getQuery(Scanner scanner, int numAttributes){
//		Scanner scanner = new Scanner(System.in);
		Sample query = new Sample();
		System.out.println("\n\nEnter " + numAttributes + " attributes to query against decision tree.\n"
				+ "Put each attributes on its own line.\n");
		for(int i = 1; i <= numAttributes; i++){
			try{
				if(scanner.hasNext()){
					String input = scanner.next();
					query.addAttribute(i, Integer.parseInt(input));
					System.out.println(input);
				}
				
			}catch(InputMismatchException e){
				
			}
		}
		
		return query;
//		scanner.close();
	}


	/*
	 * Recursive Tree output
	 */
	private static void printTree(DecisionNode node, int depth) {
		if(node == null){
			System.out.print("\n");
			return;
		}
		else if(!node.hasChildren()){
			System.out.println(node.value);
		}
		else{
			System.out.println("Feature: " + node.feature);
			for(int i = 0; i < depth; i++){
				System.out.print("\t");
			}
			System.out.print("Left: ");
			printTree(node.leftchild, depth+1);
			for(int i = 0; i < depth; i++){
				System.out.print("\t");
			}
			System.out.print("Right: ");
			printTree(node.rightchild, depth+1);
		}	
	}
	
	
	/*
	 * Initializes the user input for file path
	 */
	private static BufferedReader initializeInput(Scanner scanner){
		BufferedReader in = null;
		boolean continueprompt = true;
		do {
			System.out.println("Input path to data file.");
			try {
				in = new BufferedReader(new FileReader(new File(scanner.nextLine())));
				continueprompt = false;
			} catch (FileNotFoundException e) {
				System.out.println("File Not Found.");
				scanner.nextLine();
			}
		} while (continueprompt);
//		scanner.close();
		return in;
	}

	
	/* 
	 * Parses the plain text file located at the path input by the user
	 */
	private static SampleList parseFile(BufferedReader in){
		SampleList sampleList = new SampleList();
		boolean firstLine = true;
		try {
			String str;
			while ((str = in.readLine()) != null) {
				String[] parsed = str.split(" ");
				if (firstLine) {
					firstLine = false;
					numAttributes = Integer.parseInt(parsed[0]);
					numSamples = Integer.parseInt(parsed[1]);
				} else {
					Sample sample = new Sample();
					for(int i = 0; i < parsed.length; i++){
						if(i == parsed.length - 1){
							sample.classification = Integer.parseInt(parsed[i]);
						}
						else{
							sample.addAttribute(i+1, Integer.parseInt(parsed[i]));
						}
					}
					sampleList.add(sample);
					
				}

			}
			in.close();
		} catch (IOException e) {
			System.out.println("File Read Error");
		}
		return sampleList;
	}
	
	
	/*
	 * Sample Population data from assignment page
	 */
	private static void populateSamples(SampleList sampleList) {
		Sample sample = new Sample();
		sample.addAttribute(1, 1);
		sample.addAttribute(2, 1);
		sample.addAttribute(3, 0);
		sample.classification = 0;
		sampleList.add(sample);
		
		sample = new Sample();
		sample.addAttribute(1, 1);
		sample.addAttribute(2, 1);
		sample.addAttribute(3, 0);
		sample.classification = 1;
		sampleList.add(sample);
		
		sample = new Sample();
		sample.addAttribute(1, 0);
		sample.addAttribute(2, 0);
		sample.addAttribute(3, 1);
		sample.classification = 1;
		sampleList.add(sample);
		
		sample = new Sample();
		sample.addAttribute(1, 1);
		sample.addAttribute(2, 1);
		sample.addAttribute(3, 0);
		sample.classification = 0;
		sampleList.add(sample);
		
	}

	
	/*
	 * Random Sample Population Generator
	 */
	private static void populateSamples(SampleList sampleList, int numAttributes, int numSamples) {
		for(int i = 0; i < numSamples; i++){
			
			Sample sample = new Sample();
			Sample copysample = new Sample();
			for(int j = 1; j <= numAttributes + 1; j++){
				
				if(j == numAttributes + 1){
					sample.setClassification(random(0,1));
					copysample.setClassification(sample.classification);
					//sample.addAttribute(0, random(0,1));
				}
				else{
					sample.addAttribute(j, random(0,1));
					copysample.addAttribute(j, sample.get(sample.size()-1).data);
				}
			}
			sampleList.add(sample);
		}
		
	}
	
	
	
	public static void printSamples(SampleList sampleList){
		for(int i = 0; i < sampleList.size(); i++){
			System.out.print("\nSample: " + (i+1) + " ->");
			for(int j = 0; j < sampleList.get(i).size(); j++){
				Feature feature = sampleList.get(i).get(j);
				System.out.print("\t(" + feature.numAtt + "," + feature.data + ")");
			}
			System.out.print("\t Classification: " + sampleList.get(i).classification + "\n");
		}
	}
	
	//Inclusive Random Number Function
		private static int random(int min, int max){
			Random rand = new Random();
			return rand.nextInt((max - min) + 1) + min;
		}

}
