package methods;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;

import driver.DriverScript;
import locators.ObjectLocators;

public class ProjectModuleMethods extends DriverScript implements ObjectLocators{
	/****************************************************************
	 * Method Name	: createProject
	 * Purpose		: To create the new project
	 * Author		: 
	 * Parameters	: WebDriver oDriver, Map<String, String> objData, String resultLocation, ExtentTest test
	 * Return Type	: String
	 * 
	 ****************************************************************/
	public String createProject(WebDriver oDriver, Map<String, String> objData, String resultLocation, ExtentTest test)
	{
		String strStatus = null;
		String strProjectName = null;
		String startDt[];
		String endDt[];
		try {
			appInd.waitFor(oDriver, obj_Project_Menu_Link, "Clickable", "", 5);
			strStatus+=String.valueOf(appInd.clickObject(oDriver, obj_Project_Menu_Link, resultLocation, test));
			appInd.waitFor(oDriver, obj_Project_NewProject_Link, "Clickable", "", 5);
			strStatus+=String.valueOf(appInd.clickObject(oDriver, obj_Project_NewProject_Link, resultLocation, test));
			appInd.waitFor(oDriver, obj_Project_Name_Edit, "Visibility", "", 5);
			strProjectName = objData.get("TD_ProjectName")+"_"+appInd.getDataTime("ddMMYYYYhhmmss");
			objData.put("TD_ProjectName", strProjectName);
			
			strStatus+=String.valueOf(appInd.selectObject(oDriver, obj_Project_Company_Select, objData.get("TD_Company"), resultLocation, test));
			strStatus+=String.valueOf(appInd.setObject(oDriver, obj_Project_Name_Edit, objData.get("TD_ProjectName"), resultLocation, test));
			strStatus+=String.valueOf(appInd.setObject(oDriver, obj_Project_TeamSize_Edit, objData.get("TD_TeamSize"), resultLocation, test));
			
			startDt = objData.get("TD_StartDate").split("#", -1);
			strStatus+=String.valueOf(appInd.selectObject(oDriver, obj_Project_StartDate_Years_Select, startDt[0], resultLocation, test));
			strStatus+=String.valueOf(appInd.selectObject(oDriver, obj_Project_StartDate_Months_Select, startDt[1], resultLocation, test));
			strStatus+=String.valueOf(appInd.selectObject(oDriver, obj_Project_StartDate_Days_Select, startDt[2], resultLocation, test));
			strStatus+=String.valueOf(appInd.selectObject(oDriver, obj_Project_StartDate_Hours_Select, startDt[3], resultLocation, test));
			strStatus+=String.valueOf(appInd.selectObject(oDriver, obj_Project_StartDate_Minutes_Select, startDt[4], resultLocation, test));
			
			
			endDt = objData.get("TD_EndDate").split("#", -1);
			strStatus+=String.valueOf(appInd.selectObject(oDriver, obj_Project_EndDate_Years_Select, endDt[0], resultLocation, test));
			strStatus+=String.valueOf(appInd.selectObject(oDriver, obj_Project_EndDate_Months_Select, endDt[1], resultLocation, test));
			strStatus+=String.valueOf(appInd.selectObject(oDriver, obj_Project_EndDate_Days_Select, endDt[2], resultLocation, test));
			strStatus+=String.valueOf(appInd.selectObject(oDriver, obj_Project_EndDate_Hours_Select, endDt[3], resultLocation, test));
			strStatus+=String.valueOf(appInd.selectObject(oDriver, obj_Project_EndDate_Minutes_Select, endDt[4], resultLocation, test));
			
			strStatus+=String.valueOf(appInd.setObject(oDriver, obj_Project_Description_TextArea, objData.get("TD_Description"), resultLocation, test));
			strStatus+=String.valueOf(appInd.javaScriptclickObject(oDriver, obj_CreateProject_Btn, resultLocation, test));
			appInd.waitFor(oDriver, obj_Project_Confirmation_Msg, "Visibility", "", 10);
			
			strStatus+=String.valueOf(verifyProjectCreatedSuccessful(oDriver, objData, resultLocation, test));
			
			if(strStatus.contains("false")) {
				reports.writeResult(oDriver, "Fail", "Failed to create the new project", resultLocation, test);
				return null;
			}else {
				reports.writeResult(oDriver, "Pass", "The new project '"+objData.get("TD_ProjectName")+"' was created successful", resultLocation, test);
				reports.writeResult(oDriver, "screenshot", "The new project was created successful", resultLocation, test);
				return objData.get("TD_ProjectName");
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in the method createProject(). "+e.getMessage(), resultLocation, test);
			return null;
		}
		finally
		{
			strStatus = null;
			strProjectName = null;
			startDt = null;
			endDt = null;
		}
	}
	
	
	
	
	/****************************************************************
	 * Method Name	: verifyProjectCreatedSuccessful
	 * Purpose		: To check that the Project is created or not
	 * Author		: 
	 * Parameters	: WebDriver oDriver, Map<String, String> objData, String resultLocation, ExtentTest test
	 * Return Type	: boolean
	 * 
	 ****************************************************************/
	public boolean verifyProjectCreatedSuccessful(WebDriver oDriver, Map<String, String> objData, String resultLocation, ExtentTest test)
	{
		String strStatus = null;
		String startDt[];
		String startDate = null;
		String endDt[];
		String endDate = null;
		try {
			
			startDt = objData.get("TD_StartDate").split("#", -1);
			if(Integer.parseInt(startDt[2]) < 10) {
				startDt[2] = "0" + startDt[2];
			}
			startDate = startDt[0]+"-"+appInd.getMonthInNumber(startDt[1])+"-"+startDt[2]+" "+startDt[3]+":"+startDt[4]+":00 UTC";
			
			
			endDt = objData.get("TD_EndDate").split("#", -1);
			if(Integer.parseInt(endDt[2]) < 10) {
				endDt[2] = "0" + endDt[2];
			}
			endDate = endDt[0]+"-"+appInd.getMonthInNumber(endDt[1])+"-"+endDt[2]+" "+endDt[3]+":"+endDt[4]+":00 UTC";
			
			strStatus+= appInd.compareValues(oDriver, (appInd.getElementText(oDriver, obj_Project_Confirmation_Name_Label, "Text", resultLocation, test).split(":"))[1].trim(), objData.get("TD_ProjectName"), resultLocation, test);
			strStatus+= appInd.compareValues(oDriver, (appInd.getElementText(oDriver, obj_Project_Confirmation_TeamSize_Label, "Text", resultLocation, test).split(":"))[1].trim(), objData.get("TD_TeamSize"), resultLocation, test);
			strStatus+= appInd.compareValues(oDriver, (appInd.getElementText(oDriver, obj_Project_Confirmation_StartDate_Label, "Text", resultLocation, test).split(": "))[1].trim(), startDate, resultLocation, test);
			strStatus+= appInd.compareValues(oDriver, (appInd.getElementText(oDriver, obj_Project_Confirmation_EndDate_Label, "Text", resultLocation, test).split(": "))[1].trim(), endDate, resultLocation, test);
			strStatus+= appInd.compareValues(oDriver, (appInd.getElementText(oDriver, obj_Project_Confirmation_Description_Label, "Text", resultLocation, test).split(":"))[1].trim(), objData.get("TD_Description"), resultLocation, test);
			strStatus+= appInd.compareValues(oDriver, (appInd.getElementText(oDriver, obj_Project_Confirmation_Company_Label, "Text", resultLocation, test).split(":"))[1].trim(), objData.get("TD_Company"), resultLocation, test);
			
			if(strStatus.contains("false")) {
				return false;
			}else {
				return true;
			}
		}catch(Exception e){
			reports.writeResult(oDriver, "Exception", "Exception in the method verifyProjectCreatedSuccessful(). "+e.getMessage(), resultLocation, test);
			return false;
		}
		finally{
			strStatus = null;
			startDt = null;
			startDate = null;
			endDt = null;
			endDate = null;
		}
	}
	
	
	
	
	
	/****************************************************************
	 * Method Name	: deleteProject
	 * Purpose		: To delete the project
	 * Author		: 
	 * Parameters	: WebDriver oDriver, String projectName, String resultLocation, ExtentTest test
	 * Return Type	: boolean
	 * 
	 ****************************************************************/
	public boolean deleteProject(WebDriver oDriver, String projectName, String resultLocation, ExtentTest test)
	{
		String strStatus = null;
		int countBefore = 0;
		int countAfter = 0;
		try {
			appInd.waitFor(oDriver, obj_Project_Menu_Link, "Clickable", "", 10);
			strStatus+= appInd.clickObject(oDriver, obj_Project_Menu_Link, resultLocation, test);
			appInd.waitFor(oDriver, obj_Project_Destroy_Link, "Clickable", "", 10);
			countBefore = oDriver.findElements(By.xpath("//table[@id='paginate_table']//tr/td[text()="+"'"+projectName+"'"+"]")).size();
			
			strStatus+=String.valueOf(appInd.clickObject(oDriver, By.xpath("//table[@id='paginate_table']//tr/td[text()="+"'"+projectName+"'"+"]/following-sibling::td/a[text()='Destroy']"), resultLocation, test));
			Thread.sleep(2000);
			if(appInd.verifyAlertPresent(oDriver)) {
				oDriver.switchTo().alert().accept();
				appInd.waitFor(oDriver, obj_Project_Deleted_Confirmation_Msg, "Visibility", "", 10);
			}else {
				reports.writeResult(oDriver, "Fail", "Failed to display the alert while deleting the Project", resultLocation, test);
				return false;
			}
			
			countAfter = oDriver.findElements(By.xpath("//table[@id='paginate_table']//tr/td[text()="+"'"+projectName+"'"+"]")).size();
			strStatus+=String.valueOf(appInd.compareValues(oDriver, String.valueOf(countAfter), String.valueOf(countBefore -1), resultLocation, test));
			
			if(strStatus.contains("false")) {
				reports.writeResult(oDriver, "Fail", "Failed to delete the project '"+projectName+"'", resultLocation, test);
				return false;
			}else {
				reports.writeResult(oDriver, "Pass", "The project '"+projectName+"' was deleted successful", resultLocation, test);
				return true;
			}
		}catch(Exception e)
		{
			reports.writeResult(oDriver, "Exception", "Exception in the method deleteProject(). "+e.getMessage(), resultLocation, test);
			return false;
		}
		finally{
			strStatus = null;
		}
	}
}
