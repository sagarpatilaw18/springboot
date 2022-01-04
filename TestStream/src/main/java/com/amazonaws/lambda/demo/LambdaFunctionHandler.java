package com.amazonaws.lambda.demo;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.CloudWatchLogsEvent;
import com.amazonaws.services.lambda.runtime.events.CloudWatchLogsEvent.AWSLogs;

public class LambdaFunctionHandler implements RequestHandler<CloudWatchLogsEvent, String> {

	public String handleRequest(CloudWatchLogsEvent request, Context context) {
		context.getLogger().log(request.toString());

		AWSLogs awsLogs = request.getAwsLogs();

		
		context.getLogger().log("awsLogs :: " +awsLogs);
		
		String data = awsLogs.getData();
		
		context.getLogger().log("data :: " +data);

		String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance().getTime());
		context.getLogger().log("Invocation started: " + timeStamp);

		context.getLogger().log(request.toString());

		timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance().getTime());
		context.getLogger().log("Invocation completed: " + timeStamp);

		Test.encodeData(data);

		return "completed";
	}
}