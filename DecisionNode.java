/*
 * By: James McCune
 * Date: 4-13-16
 */

public class DecisionNode {
	
	int feature;

	DecisionNode leftchild;
	DecisionNode rightchild;
	int value;

	DecisionNode parent;

	
		
	public DecisionNode(){
		
	}
	
	public DecisionNode(int feature){
		this.feature = feature;
	}


	public void setValue(int mode) {
		this.value = mode;
		
	}

	public void addChild(DecisionNode newNode, int value) {
		
//		System.out.println("adding a child");

		
		

			if(value == 0){
//				System.out.println("adding left child");
				leftchild = newNode;
				leftchild.parent = this;
			}
			else if(value == 1){
//				System.out.println("adding right child");
				rightchild = newNode;
				rightchild.parent = this;
			}

		
	}

	public boolean hasChildren() {
		if(leftchild == null && rightchild == null)
			return false;
		else
			return true;
	}


}
