/*
 * By: James McCune
 * Date: 4-13-16
 */

import java.util.ArrayList;
import java.util.List;

public class SampleList {
	
	List<Sample> sampleList = new ArrayList<>();
	
	
	public SampleList(){
		this.sampleList = new ArrayList<>();
		
	}
	
	public SampleList(SampleList list){
		this.sampleList = new ArrayList<>();
		for(int i = 0; i< list.size(); i++){
			sampleList.add(new Sample(list.get(i)));
		}
		
	}
	
	public int size(){
		return sampleList.size();
	}
	
	public Sample get(int i){
		return sampleList.get(i);
	}
	
	public void add(Sample sample){
		sampleList.add(sample);
	}

}
