package com.tricon.labs.join.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tricon.labs.join.exceptions.ApplicationException;
import com.tricon.labs.join.exceptions.CallException;
import com.tricon.labs.join.exceptions.CallException.CallExceptionType;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.CallFactory;
import com.twilio.sdk.resource.instance.Call;

/**
 * 
 * @author Shailesh
 * 
 */
public class CallUtil {
	
	private static Logger LOGGER = LoggerFactory.getLogger(CallUtil.class);

	/**
	 * TODO fix for generic implementation.
	 * @param to
	 * @param url
	 * @return
	 * @throws ApplicationException
	 */
	public static String callNumber(String to, String url) throws ApplicationException {
		String ACCOUNT_SID = GenUtil.getEnvProperty(MessageConstants.ACCOUNT_SID, true);
		String AUTH_TOKEN = GenUtil.getEnvProperty(MessageConstants.AUTH_TOKEN, true);
		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

		// Build a filter for the CallList
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("Url", url));
		params.add(new BasicNameValuePair("To", to));
		params.add(new BasicNameValuePair("From", GenUtil.getEnvProperty(MessageConstants.FROM_NUMBER, true)));

		CallFactory callFactory = client.getAccount().getCallFactory();

		try {
			Call call = callFactory.create(params);
			return call.getStatus();
		} catch (TwilioRestException e) {
			throw new CallException(CallExceptionType.TWILIO, e);
		}
	}

}
