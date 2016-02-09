package com.ibm.devops.sample.test.selenium.ui.scripts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ibm.devops.sample.test.selenium.ui.base.TestScript;

/**
 * 
 * @author Steven Zou (zhjiacdl@cn.ibm.com)
 * Selenium junit script class for testing JKE Banking sample
 * Scenario: Do bank transaction after successful login
 * Steps:
 *   1> Input user name "jbrown"
 *   2> Input password "jbrown"
 *   3> Click login button to login
 *   4> Click the link "Checking Account" to see the account details
 *   5> Click the link button "Dividend Contribution"
 *   5> Input "5" to the "Percentage" text box and left other as default
 *   6> Click the "Next" button
 *   7> Confirm and verify the related transaction information
 *   8> Click the "Confirm" button
 *   9> Confirm and verify the account balance change
 *   10> Click the button "Transaction History" to the transaction history
 *   11> Confirm and verify the new transaction history record
 *
 */
public class JkeBankingTestScript_Transaction extends TestScript{
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testJkeBankingTransaction() throws Exception {
		
		//Load page
		driver.get(getBaseUrl() + "/#state=welcome");

		//Log in
		WebElement usernameBox = driver.findElement(By.id("usernameBox"));
		usernameBox.clear();
		usernameBox.sendKeys("jbrown");
		WebElement passwordBox= driver.findElement(By.id("passwordBox"));
		passwordBox.clear();
		passwordBox.sendKeys("jbrown");
		driver.findElement(By.id("loginButton")).click();
		
		//Check and store the account balance
		driver.findElement(By.xpath("//a[@href=\"#\" and text()=\"Checking Account\"]")).click();
		String accountLableXpath = "//div[@id=\"functionalArea\"]//h3[@dojoattachpoint=\"accountLabel\"]";
		waitForElementPresent(By.xpath(accountLableXpath));
		assertEquals(driver.findElement(By.xpath(accountLableXpath)).getText(),"Checking Account - 200");
		
		String baseBalanceXpath = "//div[@id=\"functionalArea\"]//table[@class=\"mainTable\"]//td[text()=\"::baseText\"]/following-sibling::td";
		String balanceText = "Balance";
		String pDividendsText = "Dividends contributed this period";
		String dDividendsText = "Dividends contributed to date";
		
		String balanceInfo  = driver.findElement(By.xpath(baseBalanceXpath.replaceFirst("::baseText", balanceText))).getText().trim();
		balanceInfo = balanceInfo.replaceAll("\\$|,|\\(|\\)", "");
		float balance = Float.parseFloat(balanceInfo);
		String pDividendsInfo  = driver.findElement(By.xpath(baseBalanceXpath.replaceFirst("::baseText", pDividendsText))).getText().trim();
		pDividendsInfo = pDividendsInfo.replaceAll("\\$|,|\\(|\\)", "");
		float pDividends = Float.parseFloat(pDividendsInfo);
		String dDividendsInfo  = driver.findElement(By.xpath(baseBalanceXpath.replaceFirst("::baseText", dDividendsText))).getText().trim();
		dDividendsInfo = dDividendsInfo.replaceAll("\\$|,|\\(|\\)", "");
		float dDividends = Float.parseFloat(dDividendsInfo);
		
		//Open the transaction page
		driver.findElement(By.xpath("//a[@id=\"dividendContLink\"]")).click();
		
		//Confirm the page and options is loaded
		String accountOptionXpath = "//td[text()=\"Account:\"]/following-sibling::td//table[starts-with(@widgetid,\"dijit_form_Select_\")]";
		waitForElementPresent(By.xpath(accountOptionXpath));
		waitForText(By.xpath(accountOptionXpath),"Checking Account - 200");
		waitForText(By.xpath("//td[text()=\"Organization:\"]/following-sibling::td//td[starts-with(@id,\"dijit_form_Select_\")]/span/span"),"Salvation Army");
		
		//Input the percent number 5
		WebElement numberTextBox = driver.findElement(By.xpath("//input[starts-with(@id, 'dijit_form_NumberTextBox_')]"));
		numberTextBox.clear();
		numberTextBox.sendKeys("5");
		
		//Click next to show the details confirmation
		driver.findElement(By.xpath("//span[text()=\"Next\"]/..")).click();
		
		//Verify the transaction information
		String accountSummaryXpath = "//div[@id=\"functionalArea\"]//span[@dojoattachpoint=\"accountSummary\"]";
		String charitySummaryXpath = "//div[@id=\"functionalArea\"]//span[@dojoattachpoint=\"charitySummary\"]";
		String percentSummaryXpath = "//div[@id=\"functionalArea\"]//span[@dojoattachpoint=\"percentSummary\"]";
		assertEquals("200", driver.findElement(By.xpath(accountSummaryXpath)).getText());
		assertEquals("Salvation Army", driver.findElement(By.xpath(charitySummaryXpath)).getText());
		assertEquals("5", driver.findElement(By.xpath(percentSummaryXpath)).getText());
		
		//Confirm and finish the transaction
		waitForElementVisible(By.xpath("//span[text()=\"Confirm\"]/.."));
		driver.findElement(By.xpath("//span[text()=\"Confirm\"]/..")).click();
		
		//Get the updated balance
		String updateBalanceInfo  = driver.findElement(By.xpath(baseBalanceXpath.replaceFirst("::baseText", balanceText))).getText().trim();
		updateBalanceInfo = updateBalanceInfo.replaceAll("\\$|,|\\(|\\)", "");
		float updateBalance = Float.parseFloat(updateBalanceInfo);
		String updatepDividendsInfo  = driver.findElement(By.xpath(baseBalanceXpath.replaceFirst("::baseText", pDividendsText))).getText().trim();
		updatepDividendsInfo = updatepDividendsInfo.replaceAll("\\$|,|\\(|\\)", "");
		float updatepDividends = Float.parseFloat(updatepDividendsInfo);
		String updatedDividendsInfo  = driver.findElement(By.xpath(baseBalanceXpath.replaceFirst("::baseText", dDividendsText))).getText().trim();
		updatedDividendsInfo = updatedDividendsInfo.replaceAll("\\$|,|\\(|\\)", "");
		float updatedDividends = Float.parseFloat(updatedDividendsInfo);
		
		//Verify the account balance
		assertEquals(balance,updateBalance,50);
		assertEquals(pDividends,updatepDividends,50);
		assertEquals(dDividends,updatedDividends,50);
		
		//Show the transaction history
		driver.findElement(By.xpath("//span[text()=\"Transaction History\"]/..")).click();
		
		//Verify the history
		String historyTableBaseXpath = "//div[@id=\"functionalArea\"]//table[@class=\"mainTable\"]";
		waitForElementPresent(By.xpath(historyTableBaseXpath));
		List<WebElement> elements = driver.findElements(By.xpath(historyTableBaseXpath+"//tr"));
		assertTrue(elements.size()!=0);
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
