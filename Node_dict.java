package Algorithm;

import Algorithm.Node_dict;

class Node_dict{

	Node_dict right;
	Node_dict left;
	Node_dict parent;
	String voca;
	String part;
	String mean;

	Node_dict(){	
	}
	
	Node_dict(String voca, String part){
		this.voca = voca;
		this.part = part;
	}

	Node_dict(String voca, String part, String mean){
		this.voca = voca;
		this.part = part;
		this.mean = mean;
	}

	public String getVoca() {
		return voca;
	}
	
	public String getPart() {
		return part;
	}
	
	public String getMean() {
		return mean;
	}

}
