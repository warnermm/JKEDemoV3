package com.ibm.devops.sample.test.selenium.services.scripts;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JkeBankingServiceTestScript_Transaction{
	
	private static final String MMT_SERVICE_SUFIX = "/transactions/create?account=200&org=American%20Cancer%20Society&date=31+Aug+2010&percent=-5";
	private static final String MMT_USER_NAME = "jbrown";
	private static final int TIME_OUT = 60000;
	private static final String RQM_VAR_PREFIX = "qm_";
	private static final String PROTOCOL_PREFIX = "http://";
	private static final int REQUIRED_STATUS_BAD_REQUEST = 400;
	private static final int REQUIRED_STATUS_OK = 200;
	private String baseUrl;
	private HttpClient httpClient = null;
	
	@Before
	public void setUp() throws Exception {
		initializeEnv();
		createHttpClient();
	}
	
	@Test
	public void testHomepage() throws ClientProtocolException, IOException{
		runGetTest(baseUrl,REQUIRED_STATUS_OK);
	}
	
	@Test
	public void testLogin() throws ClientProtocolException, IOException{
		String serviceUrl = baseUrl + "/user/"+MMT_USER_NAME;
		runGetTest(serviceUrl,REQUIRED_STATUS_OK);
	}
	
	@Test
	public void testNegativeContribution() throws ClientProtocolException, IOException{
		String serviceUrl = baseUrl + MMT_SERVICE_SUFIX;
		HttpPost transactionPost = new HttpPost(serviceUrl);
		HttpResponse response = httpClient.execute(transactionPost);
		assertEquals(REQUIRED_STATUS_BAD_REQUEST,response.getStatusLine().getStatusCode());
	}
	
	@After
	public void tearDown() throws Exception {
		httpClient.getConnectionManager().shutdown();
		httpClient = null;
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
	
	private void createHttpClient(){
		httpClient = new DefaultHttpClient();
		httpClient.getParams().setIntParameter("http.connection.timeout", TIME_OUT);
	}
	
	private void runGetTest(String serviceUrl, int expectedStatus) throws ClientProtocolException, IOException{
		HttpGet getMethod = new HttpGet(serviceUrl);
		HttpResponse response = httpClient.execute(getMethod);
		assertEquals(expectedStatus,response.getStatusLine().getStatusCode());
	}
}
