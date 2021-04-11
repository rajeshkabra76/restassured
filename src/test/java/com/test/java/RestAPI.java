package com.test.java;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class RestAPI {
	
	
	  String expectedValue = null;
	  FileInputStream fis = null;
	  XSSFWorkbook xssfWorkbook = null;
	  XSSFSheet sheet = null;
	  int rowCount = 0;
	
@Test(enabled = true, priority = 1)
  public void validate_season() {
	  
	  HashedMap<String, String> expectedHeader = new HashedMap<String, String>();
	 // expectedHeader.put("content-length", "4739");
	  expectedHeader.put("content-type", "application/json; charset=utf-8");
	  System.out.println("Test 1");
	  
	  
	 ValidatableResponse response = RestAssured.given().
			  when().
			  get("/f1/2016/last.json").
			  then().assertThat().statusCode(200).and().headers(expectedHeader).and();
			  ;
			  
			 JsonPath jsonPathEvaluator = response.extract().jsonPath();
			 
			 List listObj = new ArrayList<String>();
			 listObj = jsonPathEvaluator.get("MRData.RaceTable.Races.season");
	
			 expectedValue = sheet.getRow(rowCount).getCell(0).getStringCellValue();
			 
			 for (Object object : listObj) {
				Assert.assertEquals(object.toString(), expectedValue);
			}
			 
			 
		
  }
  
  @Test(enabled = true, priority = 2, description = "This test validates raceName")
  public void validate_raceName() {
	  ValidatableResponse response = RestAssured.given().
			  when().
			  get("/f1/2016/last.json").
			  then().assertThat().statusCode(200);
			  
			 JsonPath jsonPathEvaluator = response.extract().jsonPath();
			 
			 List listObj = new ArrayList<String>();
			 listObj = jsonPathEvaluator.get("MRData.RaceTable.Races.raceName");
			 
			 expectedValue = sheet.getRow(rowCount).getCell(1).getStringCellValue();
			 for (Object object : listObj) {
				Assert.assertEquals(object.toString(), expectedValue);
			}
			 
			 
	
	  
  }
  @BeforeMethod
  public void beforeMethod() {
  }

  @AfterMethod
  public void afterMethod() {
  }

  @BeforeClass
  public void beforeClass() throws IOException {
	  
	  try {
			 fis = new FileInputStream("src/test/resources/TestData/APItesting.xlsx");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		
		xssfWorkbook = new XSSFWorkbook(fis);
	  
	
	  sheet = xssfWorkbook.getSheetAt(0);
	  rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
	  
  }
  
  

  @AfterClass
  public void afterClass() {
	  try {
		xssfWorkbook.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }

  @BeforeTest
  public void beforeTest() {
  }

  @AfterTest
  public void afterTest() {
  }

  @BeforeSuite
  public void beforeSuite() {
	  RestAssured.baseURI = "https://ergast.com/api";
	  
  }

  @AfterSuite
  public void afterSuite() {
  }

}
