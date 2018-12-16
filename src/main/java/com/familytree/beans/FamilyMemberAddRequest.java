package com.familytree.beans;

import java.util.Set;

public class FamilyMemberAddRequest {
	String name;
	String[] parent;
	Set<String> children ;
	int age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getParent() {
		return parent;
	}
	public void setParent(String[] parent) {
		this.parent = parent;
	}
	public Set<String> getChildren() {
		return children;
	}
	public void setChildren(Set<String> children) {
		this.children = children;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public String toString() {
		
		StringBuilder buf = new StringBuilder();
		buf.append("name: "+name);
		buf.append(" age:"+age);
		buf.append(parent != null ?" Nbr Of Parent:"+parent.length:"parent is Null");
		buf.append(children != null ?" Nbr Of children:"+children.size():"children is Null");
		for(int i =0;parent!= null && i<parent.length;i++) {
			buf.append(" parent "+(i+1)+":"+parent[i]);
		}
		int i=1;
		for(String child:children) {
			buf.append(" children :"+i++ +":"+child);
		}
		
		return buf.toString();
	}
	

}
