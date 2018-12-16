# FamilyTreeCodeRepo

1. clone the repo from below git url.

	https://github.com/baratamramprasad/FamilyTreeCodeRepo.git

2.Go to HomeDir and run below command
	mvn spring-boot:run

3.Aftre spring boot application is initiated, check if the familytreeService is deployed or not by below get request and see the welcome msg.
http://localhost:8080/

4.Now application is ready.set content-type as Application/json

	A-API to  create/update the family member.
		 POST "/familytreeservice/addOrUpdate 

		{
		"name":"BMR",
		"children":
			["BRP","BTR"],
		"age":60
		}
	B- Sorting API-
		 GET "/familytreeservice/getChildren/BMR?sortBy=age&orderBy=Desc"

	C- pretty print API
		GET "/familytreeservice/BMR?printParentToChild=true"
		GET "/familytreeservice/BMR?printParentToChild=false"


Also I have single Junit test case where i have covered all the test cases.

Note: This is working code and let me know if you need any clarification.
