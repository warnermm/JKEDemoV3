package com.ibm.devops.sample.test.selenium.ui.scripts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ibm.devops.sample.test.selenium.ui.base.TestScript;

public class JkeBankingTestScript_StockQuote extends TestScript {

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}
	
	@Test
	public void testJkeBankingStockQuote() throws Exception {
		
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
		
		//Open the stockQuote page
		driver.findElement(By.xpath("//a[@id=\"getStockQuoteLink\"]")).click();
		
		//Wait for stockQuote page to load
		waitForElementVisible(By.xpath("//div[@id=\"enterTickerContent\"]"));
		
		//Input the stock ticker symbol
		WebElement tickerSymbolTextBox = driver.findElement(By.xpath("//input[@dojoattachpoint=\"tickerSymbolBox\"]"));
		tickerSymbolTextBox.clear();
		tickerSymbolTextBox.sendKeys("IBM");
		
		//Click "Get Quote" to submit ticker symbol text to web service
		driver.findElement(By.xpath("//button[@dojoattachpoint=\"stockQuoteButton\"]")).click();
		
		//Wait for stockQuote results to load
		waitForElementVisible(By.xpath("//div[id=\"displayTickerContent\"]"));
		
		//Verify the stock quote result
		try {
			assertEquals("IBM", driver.findElement(By.xpath("//span[@id=\"displayTickerSymbol\"]")).getText());
			assertEquals("100000.00", driver.findElement(By.xpath("//span[@id=\"displayTickerPrice\"]")).getText());
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
