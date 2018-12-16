package com.familytree.beans;

import java.util.List;

public class NodeWithChildren implements Comparable<NodeWithChildren>{
	String name;
	
	int age;
	
	List<String> children;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public List<String> getChildren() {
		return children;
	}

	public void setChildren(List<String> children) {
		this.children = children;
	}
	
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("name: "+name);
		buf.append(" age: "+age);
		int i =1;
		for(String child:children) {
			buf.append("child "+i++ +":"+child);
		}
		return buf.toString();
	}

	@Override
	public int compareTo(NodeWithChildren arg0) {
		
		return this.age-arg0.getAge();
	}
	
	public int hasCode() {
		return 31*name.hashCode()+37*children.hashCode()+age%7;
	}
	
	public boolean equals(Object other) {
		if(other == null) {
			return false;
		}
		if (this == other) {
			return true;
		}
		NodeWithChildren otherNode = (NodeWithChildren)other;
		if (this.getName().equals(otherNode.getName())) {
			return this.age == otherNode.getAge() && this.children.size() == otherNode.getChildren().size() ;
		}
		return false;
		
	}

	
}
