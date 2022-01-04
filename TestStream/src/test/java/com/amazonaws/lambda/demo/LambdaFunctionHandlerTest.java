package com.amazonaws.lambda.demo;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.CloudWatchLogsEvent;
import com.amazonaws.services.lambda.runtime.events.CloudWatchLogsEvent.AWSLogs;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class LambdaFunctionHandlerTest {

	private static CloudWatchLogsEvent input;

	@BeforeClass
	public static void createInput() throws IOException {
		// TODO: set up your sample input object here.
		input = new CloudWatchLogsEvent();
	
		AWSLogs awsLogs = new AWSLogs();
		awsLogs.setData("{awslogs: {data: H4sIAAAAAAAAAEWQXWuDMBSG/8oIu2wx5jveCXOFfcBAYRe1lGgyG9BYNK2U0v++pGPbzSF5z3POm7xXMJh5Vp2pLkcDMvCUV/n+vSjLfFOAFRgXZ6YgU0kZkxQKnIog92O3mcbTMXQStcxJr4ZGq+SjV9atX9RZrSsz+x+w9JNRQyARRCiBaQJJsn18y6uirHa4kazFQhGlEWkxVy1EmhuIODMt4zSsmE/N3E726O3onm3vzTSDbAt8NNjdHYqzcT6KV2B1MMKMQUQQCW8VjMN4EVxQmhIpCJJUIMS4kAhyLAkjnCBIME9hMPM2xOHVEH6WMpJiKAlPGaar35jC+ho0o77UIHuowbWu6zDkexMPWSxlmO7N6+i+VHPqlR+nT6s742NvFYtTwz89hMT2i3V6XP6AxWp/uBMUwrtyMLY7+F/pFjkHbrvbN9jN/YC+AQAA}}");
		
		
		input.setAwsLogs(awsLogs);
	

	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// TODO: customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testLambdaFunctionHandler() {
		LambdaFunctionHandler handler = new LambdaFunctionHandler();
		Context ctx = createContext();
		
		handler.handleRequest(input, ctx);
		
		/*
		 * String output = handler.handleRequest(input, ctx);
		 * 
		 * // TODO: validate output here if needed.
		 * Assert.assertEquals("Hello from Lambda!", output);
		 */
	}
}
