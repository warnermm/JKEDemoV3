package com.ibm.devops.sample.test.selenium.ui.scripts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ibm.devops.sample.test.selenium.ui.base.TestScript;

/**
 * 
 * @author Steven Zou (zhjiacdl@cn.ibm.com)
 * 
 * Selenium junit script class for testing JKE Banking sample
 * Scenario: Login in with invalid userid and password
 * Steps:
 *   1> Find text box "usernameBox"
 *   2> Input value "admin" to the "usernameBox"
 *   3> Find password text box "passwordBox"
 *   4> Input value "admin" to the passwordBox
 *   5> Click the login button
 *   6> Verify the loginError text
 *
 */

public class JkeBankingTestScript_LoginError extends TestScript{
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testJKEBANKTC1() throws Exception {
		driver.get(getBaseUrl()+ "/#state=welcome");
		
		//Login with valid user&pass
		WebElement userNameBox = driver.findElement(By.id("usernameBox"));
		userNameBox.clear();
		userNameBox.sendKeys("admin");
		WebElement passwordBox = driver.findElement(By.id("passwordBox"));
		passwordBox.clear();
		passwordBox.sendKeys("admin");
		driver.findElement(By.id("loginButton")).click();
		
		//Wait for the result return
		waitForElementVisible(By.id("loginError"));
		
		//Verify
		try {
			assertEquals("Username or password is incorrect!", driver.findElement(By.id("loginError")).getText());
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
