/*
 * By: James McCune
 * Date: 4-13-16
 */

import java.util.ArrayList;
import java.util.List;

public class Sample{
	
	List<Feature> features;
	int classification;
	
	public Sample(){
		features = new ArrayList<>();
	}
	
	public Sample(Sample sample) {
		features = new ArrayList<>();
		for(int i = 0; i < sample.size(); i++){
			features.add(new Feature(sample.get(i).numAtt, sample.get(i).data));
		}
		this.classification = sample.classification;
	}

	public void addAttribute(int numAtt, int data){
		features.add(new Feature(numAtt, data));
	}
	
	public Feature getAttribute(int numAtt){
		
		for(int i = 0; i < features.size(); i++){
			
			if(features.get(i).numAtt == numAtt){
				
				return features.get(i);
			}
		}
		return null;
	}
	
	public void removeAttribute(int numAtt){
		for(int i = 0; i < features.size(); i++){
			if(features.get(i).numAtt == numAtt){
				features.remove(i);
			}
		}
	}
	
	public Feature get(int i){
		return features.get(i);
	}
	
	public int size(){
		return features.size();
	}

	public void setClassification(int random) {
		classification = random;
		
	}

}
