package com.ibm.devops.sample.test.selenium.ui.base;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 
 * @author Steven Zou(zhjiacdl@cn.ibm.com)
 * 
 * This class is a base class for constructing selenium scripts in sccd and rqm integration scenario
 * Each script used for the above scenario should extend this class to support test environment variables
 * passing functionality
 */

public abstract class TestScript {
	
	private static final String RQM_VAR_PREFIX = "qm_";
	private static final String PROTOCOL_PREFIX = "http://";
	private static final int TIME_OUT = 60;//Seconds
	
	protected WebDriver driver;
	protected StringBuffer verificationErrors = new StringBuffer();
	
	private String baseUrl;
	
	public void setUp() throws Exception {
		initializeEnv();
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(TIME_OUT, TimeUnit.SECONDS);
	}
	
	public String getBaseUrl() {
		return baseUrl;
	}
	
	public void waitForElementVisible(By by){
		WebDriverWait wait = new WebDriverWait(driver,TIME_OUT);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}
	
	public void waitForText(By by,String text){
		WebDriverWait wait = new WebDriverWait(driver,TIME_OUT);
		wait.until(ExpectedConditions.textToBePresentInElement(by, text));
	}
	
	public void waitForElementPresent(By by){
		WebDriverWait wait = new WebDriverWait(driver,TIME_OUT);
		wait.until(ExpectedConditions.presenceOfElementLocated(by));
	}
	
	private void initializeEnv() throws Exception{
		String hostName = System.getenv(RQM_VAR_PREFIX+"hostName");
		if(null==hostName || "".equals(hostName)){
			throw new Exception("Required variable <hostName> is null");
		}
		String port = System.getenv(RQM_VAR_PREFIX+"port");
		baseUrl = PROTOCOL_PREFIX+hostName;
		if(port!=null && !"".equals(port)){
			baseUrl+=":"+port;
		}
	}
}
