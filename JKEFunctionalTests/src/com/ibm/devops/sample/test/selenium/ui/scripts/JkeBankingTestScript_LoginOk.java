package com.ibm.devops.sample.test.selenium.ui.scripts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
 * Scenario: Login in the system successfully
 *   1> Find text box "usernameBox"
 *   2> Input value "jbrown" to the "usernameBox"
 *   3> Find password text box "passwordBox"
 *   4> Input value "jbrown" to the passwordBox
 *   5> Click the login button
 *   6> Verify the userName "Julie"
 *
 */
public class JkeBankingTestScript_LoginOk extends TestScript{
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testJKEBANKTCLOGINOK() throws Exception {
		driver.get(getBaseUrl() + "/#state=welcome");
		
		//Login
		WebElement userNameBox = driver.findElement(By.id("usernameBox"));
		userNameBox.clear();
		userNameBox.sendKeys("jbrown");
		WebElement passwordBox = driver.findElement(By.id("passwordBox"));
		passwordBox.clear();
		passwordBox.sendKeys("jbrown");
		driver.findElement(By.id("loginButton")).click();
		
		//Wait result return
		waitForElementVisible(By.xpath("//div[@id=\"loggedInContent\"]"));
		
		//Verify the login result
		try {
			assertEquals("Julie", driver.findElement(By.xpath("//span[@id=\"usersName\"]")).getText());
			assertTrue(driver.findElement(By.xpath("//button[@id=\"logoutButton\"]")).isDisplayed());
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
