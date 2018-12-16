package com.familytree.beans;

import java.util.HashSet;
import java.util.Set;

public class FamilyTreeNode {
	String name;
	int age;
	Set<FamilyTreeNode> parent;
	Set<FamilyTreeNode> children = new HashSet<>();
	// it is the key
 public FamilyTreeNode() {
	 parent = new HashSet<>();
	 children = new HashSet<>();
 }
	
	
	public Set<FamilyTreeNode> getParent() {
		return parent;
	}
	public void setParent(Set<FamilyTreeNode> parent) {
		this.parent = parent;
	}
	public Set<FamilyTreeNode> getChildren() {
		return children;
	}
	public void setChildren(Set<FamilyTreeNode> children) {
		this.children = children;
	}
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
	
	public int hashCode() {
		return name.hashCode();
	}
	
	
	public boolean equals(Object other) {
		FamilyTreeNode otherObj= (FamilyTreeNode)other;
		return this.name.equals(otherObj.getName());
	}
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("name: "+name);
		buf.append(" age:"+age);
		buf.append(parent != null ?" Nbr Of Parent:"+parent.size():"parent is Null");
		buf.append(children != null ?" Nbr Of children:"+children.size():"children is Null");
		if(parent != null) {
			for(FamilyTreeNode par:parent) {
				buf.append(" parent: "+par.getName());
			}
		}
		
		if(children != null) {
			for(FamilyTreeNode child:children) {
				buf.append(" child: "+child.getName());
			}
		}
		return buf.toString();
	}

	
	
}
