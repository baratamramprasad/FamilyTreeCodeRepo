package com.familytree.controller;

import java.util.SortedSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.familytree.beans.FamilyMemberAddRequest;
import com.familytree.beans.FamilyMemberAddedResponse;
import com.familytree.beans.NodeWithChildren;

@RestController
@RequestMapping("/familytreeservice")
@EnableWebMvc
public class FamilyTreeController {
	
	Logger logger = LoggerFactory.getLogger(FamilyTreeService.class);
	@Autowired
	FamilyTreeService familyTreeService;
	
	@RequestMapping(method = RequestMethod.POST, value="/test")
	@ResponseBody
	String test(@RequestBody String testMsgBody) throws Exception {
		logger.info("Rest call to test with input:\n"+testMsgBody);
		System.out.println("FamilyTreeController.test()ramprasad:");
		
		 return "test msg response";
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/addOrUpdate")
	@ResponseBody
	FamilyMemberAddedResponse addFamilyMember(@RequestBody FamilyMemberAddRequest familyMemberDetail) throws Exception {
		logger.info("Rest call to addFamilyMember with input:\n"+familyMemberDetail);
		
		return familyTreeService.addOrUpdateFamilyMember(familyMemberDetail);
	}
	 @GetMapping("/getChildren/{name}")
	 @ResponseBody
	 SortedSet<NodeWithChildren> getFamilyTree(@PathVariable("name") String name,@RequestParam("sortBy") String sortBy
			 ,@RequestParam("orderBy") String orderBy) throws Exception {
		 logger.info("Rest call to getFamilyTree with input :"+(name!= null ? "name: "+name:"name is null")
				 +(sortBy!=null?" sortBy: "+sortBy:"sortby is null")
				 +(orderBy!=null?" orderBy: "+orderBy:"orderBy is null"));
		 
		 
			
		 return familyTreeService.getFamilyTree(name,sortBy,orderBy);
	 }
	 
	 @GetMapping("/{name}")
	 @ResponseBody
	 String printFamilyInPretty(@PathVariable("name") String name,@RequestParam("printParentToChild") boolean printParentToChild) {
		 logger.info("Rest call to printFamilyInPretty with input  name: "+name+" printParentToChild: "+printParentToChild	);
		 return familyTreeService.printFamilyInPretty(name,printParentToChild);
	 }

}
