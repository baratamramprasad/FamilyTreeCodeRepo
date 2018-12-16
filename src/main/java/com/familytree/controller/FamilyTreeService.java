package com.familytree.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.familytree.beans.FamilyMemberAddRequest;
import com.familytree.beans.FamilyMemberAddedResponse;
import com.familytree.beans.FamilyTreeNode;
import com.familytree.beans.NodeWithChildren;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
public class FamilyTreeService {
	
	private HashMap<String,FamilyTreeNode> familyMap = new HashMap<>();
	
	Logger logger = LoggerFactory.getLogger(FamilyTreeService.class);

	/**
	 * 
	 * @return
	 * @throws Exception 
	 */
	public SortedSet<NodeWithChildren>  getFamilyTree(String name,String sortBy,String orderBy) throws Exception {
		logger.info("getFamilyTree name"+name+" sortBy"+sortBy+" orderBy"+orderBy);
		if(name == null) {
			throw new Exception("NULL name is not supported");
		}
		if(! familyMap.containsKey(name)) {
			throw new Exception("Family member with name "+name+"does not exist.");
		}
		
		if(sortBy != null && ! sortBy.equalsIgnoreCase("age")) {
			throw new Exception("Only sortBy age is supported.");
		}
		//TODO hard coding of ASC shall be removed.
		if(orderBy == null ) {
			orderBy = "ASC";
		}
		
		return sortedList(name,sortBy,orderBy);
		
		
	}

	private SortedSet<NodeWithChildren> sortedList(String name, String sortBy, String orderBy) {
		Comparator<NodeWithChildren> comparator =new Comparator<NodeWithChildren>() {

			@Override
			public int compare(NodeWithChildren o1, NodeWithChildren o2) {	
				
				//TODO hard coding of ASC shall be removed by enum
				if(orderBy.equalsIgnoreCase("ASC")) {
					return o1.compareTo(o2);
				}else {
					return o2.compareTo(o1);
				}
			}
		};
		
		FamilyTreeNode treeNode = familyMap.get(name);
		TreeSet<NodeWithChildren> sortedSet = new TreeSet<>(comparator);
		// tree traversal for
		recursiveRetreval(treeNode,sortedSet);
		System.out.println("FamilyTreeService.sortedList()sortedSet :"+sortedSet);
		for(NodeWithChildren node:sortedSet) {
			System.out.println(node);
		}
		return sortedSet;
	}

	private void recursiveRetreval(FamilyTreeNode treeNode, TreeSet<NodeWithChildren> sortedSet) {
		logger.trace("treeNode"+treeNode.getName()+" sortedSet size :"+sortedSet.size());
		
		sortedSet.add(convertToNodeWithChild(treeNode));
		if(treeNode.getChildren() == null || treeNode.getChildren().size() ==0) {
			return;
		}
		for(FamilyTreeNode child:treeNode.getChildren()) {
			logger.trace("child"+child.getName());
			recursiveRetreval(child,sortedSet);
		}	
	}

	private NodeWithChildren convertToNodeWithChild(FamilyTreeNode treeNode) {
		NodeWithChildren node = new NodeWithChildren();
		node.setName(treeNode.getName());
		node.setAge(treeNode.getAge());
		List<String> children = new ArrayList<>();
		if(treeNode.getChildren() != null) {
			for(FamilyTreeNode child:treeNode.getChildren()) {
				children.add(child.getName());
			}
		}
		
		node.setChildren(children);
		return node;
	}

	/**
	 * 
	 * @param familyMemberDetail
	 * @return
	 * @throws Exception 
	 */
	public FamilyMemberAddedResponse addOrUpdateFamilyMember(FamilyMemberAddRequest familyMemberDetail) throws Exception {
		logger.info("addOrUpdateFamilyMember"+familyMemberDetail);
		FamilyMemberAddedResponse response = new FamilyMemberAddedResponse();
		response.setResMsg(new ArrayList<String>());
		
		if(familyMap.containsKey(familyMemberDetail.getName())) {
			String res=updateDetails(familyMemberDetail);
			response.getResMsg().add(res);
		}else {
			String res= addNewFamilyMember(familyMemberDetail);
			response.getResMsg().add(res);
		}
			
		
		
		logger.info("response: "+response);
		return response;
	}

	private String addNewFamilyMember(FamilyMemberAddRequest familyMemberDetail) throws Exception {
		logger.info("addNewFamilyMember"+familyMemberDetail.getName());
		FamilyTreeNode familyNode = new FamilyTreeNode();
		familyMap.put(familyMemberDetail.getName().trim(), familyNode);
		familyNode.setName(familyMemberDetail.getName());
		if(familyMemberDetail.getAge() > 0) {
			familyNode.setAge(familyMemberDetail.getAge());
		}
		Set<FamilyTreeNode>  parent =createOrGetParent(familyMemberDetail.getParent());
		
		familyNode.getParent().addAll(parent);
		if (familyNode.getParent().size() > 2) {
			throw new Exception("Nbr of parents exceeds 2 for-" + familyNode.getName());
		}
		if (familyMemberDetail.getParent() != null) {
			for (String par : familyMemberDetail.getParent()) {
				logger.debug("add children for -"+par);
				familyMap.get(par.trim()).getChildren().add(familyNode);
			}
		}
		familyNode.getChildren().addAll(createOrGetChildren(familyNode.getName(),familyMemberDetail.getChildren()));
		if (familyMemberDetail.getChildren() != null) {
			for (String child : familyMemberDetail.getChildren()) {
				logger.debug("update parent for child-"+child);
				familyMap.get(child).getParent().add(familyNode);
				if (familyMap.get(child).getParent().size() > 2) {
					throw new Exception("Nbr of parents exceeds 2 for-" + familyMap.get(child).getName());
				}

			}

		}
		
		return "addNewFamilyMember-"+familyMemberDetail.getName();
	}

	private Set<FamilyTreeNode> createOrGetChildren(String parent,Set<String> children) throws Exception {
		logger.info("createOrGetChildren "+children);
		if(children == null) {
			return new HashSet<FamilyTreeNode>();
		}
		Set<FamilyTreeNode> childList = new HashSet<FamilyTreeNode>(children.size());
		for (String child : children) {
			logger.info("child name: "+child);
			if (familyMap.get(child) == null) {
				FamilyMemberAddRequest req = new FamilyMemberAddRequest();
				req.setName(child);
				req.setParent(new String[] {parent});
				addNewFamilyMember(req);
			} 
			childList.add(familyMap.get(child));
		}
		return childList;
	}

	private Set<FamilyTreeNode> createOrGetParent(String[] parent) throws Exception {
		logger.info("createOrGetParent "+(parent == null ?" parent is null":" nbr of parent:"+parent.length));
		Set<FamilyTreeNode> parentSet = new HashSet<>();
		if (parent != null ) {
			if(parent.length > 2) {
				throw new Exception("There can not be more than two parents");
			}
			
			for (String par : parent) {
				if (familyMap.get(par.trim()) == null) {
					FamilyMemberAddRequest req = new FamilyMemberAddRequest();
					req.setName(par);
					addNewFamilyMember(req);
				}
				parentSet.add(familyMap.get(par));
			}
			logger.info("nodes "+parentSet.size());
		}
		
		return parentSet;
	}

	

	private String  updateDetails(FamilyMemberAddRequest familyMemberDetail) throws Exception {
		logger.info("updateDetails:"+familyMemberDetail);
		FamilyTreeNode node = familyMap.get(familyMemberDetail.getName());
		node.setAge(familyMemberDetail.getAge());
		node.getChildren().addAll(createOrGetChildren(familyMemberDetail.getName(),familyMemberDetail.getChildren()));
		String[] parent =familyMemberDetail.getParent();
		node.getParent().addAll(createOrGetParent(parent));
		
		for(FamilyTreeNode par:node.getParent()) {
			par.getChildren().add(node);
		}
		return "updated  age/children- "+familyMemberDetail.getName();
		
	}

	public String printFamilyInPretty(String name,boolean printParentToChild) {
		 Gson gson = new GsonBuilder()
			        .setExclusionStrategies(new ParentExclusionGsonStategy(printParentToChild))
			        .setPrettyPrinting()
			        .create();
		 String json = gson.toJson(familyMap.get(name));
		 
		 logger.info("json in pretty format::\n"+json);
		return json;
	}
	

}
