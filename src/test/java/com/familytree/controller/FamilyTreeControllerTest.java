package com.familytree.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.familytree.beans.FamilyMemberAddRequest;
import com.familytree.beans.FamilyMemberAddedResponse;
import com.familytree.mainapp.SpringBootRestApplication;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringBootRestApplication.class,FamilyTreeController.class })
@WebAppConfiguration
@ComponentScan(basePackages = "com.familytree")
public class FamilyTreeControllerTest {
	//do not mock this class for tetsing all together.
	private FamilyTreeService familyTreeService;
	  
	  MockMvc mockMvc;

	    @Autowired
	    private WebApplicationContext wac;

	    @Before
	    public void setUp() {
	        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	    }

	
	@Test
	public void test() throws Exception {
		System.out.println("FamilyTreeControllerTest.test");
		String json = new Gson().toJson("Test Message");
		MvcResult result =mockMvc.perform(post("/familytreeservice/test").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(json)).andExpect(status().isOk()).andReturn();
		Assert.assertEquals("test msg response", result.getResponse().getContentAsString());
	}

	
	@Test
	public void addFamilyMember() throws Exception {
		System.out.println("addFamilyMember" + familyTreeService + "mock :" + mockMvc);
		
		FamilyMemberAddRequest request = new FamilyMemberAddRequest();
		request.setName("BMR");
		request.setAge(60);
		Set<String> children = new HashSet<>();
		children.add("BRP");
		children.add("BTR");
		request.setChildren(children);
		String json = new Gson().toJson(request);
		MvcResult result =mockMvc.perform(post("/familytreeservice/addOrUpdate").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(json)).andExpect(status().isOk()).andReturn();
		FamilyMemberAddedResponse res = new FamilyMemberAddedResponse();
		res.setResMsg(new ArrayList<String>());
		res.getResMsg().add("addNewFamilyMember-BMR");
		String expectedReply=new Gson().toJson(res);
		System.out.println("FamilyTreeControllerTest.addFamilyMember()\n"+result.getResponse().getContentAsString());
		Assert.assertEquals(expectedReply, result.getResponse().getContentAsString());
		FamilyMemberAddRequest request1 = new FamilyMemberAddRequest();
		request1.setName("BRP");
		request1.setAge(35);
		Set<String> children1 = new HashSet<>();
		children1.add("BSK");
		children1.add("BS");
		request1.setChildren(children1);
		request1.setParent(new String[] {"BAJ","BMR"});
		String json1 = new Gson().toJson(request1);
		System.out.println("***************************************BRP Start************************************");
		MvcResult result1 =mockMvc.perform(post("/familytreeservice/addOrUpdate").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(json1)).andExpect(status().isOk()).andReturn();
		System.out.println("FamilyTreeControllerTest.addFamilyMember()\n"+result1.getResponse().getContentAsString());
		System.out.println("*****************************************BRP End*************************************");
		
		MvcResult result2 =mockMvc.perform(get("/familytreeservice/BAJ?printParentToChild=true").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		String output= result2.getResponse().getContentAsString();
		
		System.out.println("===========================================Pretty OUTPUT from parent to child=======================================================\n\n"+output);
		
		
		result2 =mockMvc.perform(get("/familytreeservice/BS?printParentToChild=false").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		output= result2.getResponse().getContentAsString();
		System.out.println("===========================================Pretty OUTPUT from child to parent=======================================================\n\n"+output);
		
		result2 =mockMvc.perform(get("/familytreeservice/getChildren/BMR?sortBy=age&orderBy=ASC").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		output= result2.getResponse().getContentAsString();
		System.out.println("===========================================Sorted Order By Age=======================================================\n\n"+output);
		
		result2 =mockMvc.perform(get("/familytreeservice/getChildren/BMR?sortBy=age&orderBy=Desc").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		output= result2.getResponse().getContentAsString();
		System.out.println("===========================================Sorted Descending Order By Age =======================================================\n\n"+output);
		
	}

	
		
		
	

}
