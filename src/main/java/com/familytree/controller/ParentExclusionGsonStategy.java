package com.familytree.controller;

import com.familytree.beans.FamilyTreeNode;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
/**
 * 
 * @author ramba08
 *
 */
public class ParentExclusionGsonStategy implements ExclusionStrategy {
	boolean parentToChild = true;
	
	public ParentExclusionGsonStategy(boolean parentToChild) {
		this.parentToChild =parentToChild;
	}

	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		if(parentToChild) {
			return (f.getDeclaringClass() == FamilyTreeNode.class && f.getName().equals("parent"));
		}else {
			return (f.getDeclaringClass() == FamilyTreeNode.class && f.getName().equals("children"));
		}
	}

	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		
		return false;
	}

}
