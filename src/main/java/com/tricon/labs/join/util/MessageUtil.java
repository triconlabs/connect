package com.tricon.labs.join.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.plivo.helper.api.client.RestAPI;
import com.plivo.helper.api.response.message.MessageResponse;
import com.plivo.helper.exception.PlivoException;
import com.tricon.labs.join.exceptions.ApplicationException;
import com.tricon.labs.join.exceptions.MessageException;
import com.tricon.labs.join.exceptions.MessageException.MessageExceptionType;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

public class MessageUtil {

	private static Logger LOGGER = LoggerFactory.getLogger(MessageUtil.class);

	public static String retval = "";

	/**
	 * 
	 * @param phoneNumber
	 * @param messageBody
	 * @return
	 * @throws ApplicationException
	 */
	public static String sendMessage(String phoneNumber, String messageBody)
			throws ApplicationException {
		if (phoneNumber.startsWith("+91"))
			return sendMessageInIndia(phoneNumber, messageBody);
		String ACCOUNT_SID = GenUtil.getEnvProperty(
				MessageConstants.ACCOUNT_SID, true);
		String AUTH_TOKEN = GenUtil.getEnvProperty(MessageConstants.AUTH_TOKEN,
				true);
		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
		// Build a filter for the MessageList
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("Body", messageBody));
		params.add(new BasicNameValuePair("To", phoneNumber));
		params.add(new BasicNameValuePair("From", GenUtil.getEnvProperty(
				MessageConstants.FROM_NUMBER, true)));
		MessageFactory messageFactory = client.getAccount().getMessageFactory();
		try {
			Message message = messageFactory.create(params);
			return message.getStatus();
		} catch (TwilioRestException e) {
			throw new MessageException(MessageExceptionType.TWILIO, e);
		}
	}

	/**
	 * TODO this is temporary, improve this code.
	 * 
	 * @param messageBody
	 * @param phoneNumber
	 * @return
	 */
	public static String sendMessageInIndia(String phoneNumber,
			String messageBody) {

		String authId = "MAOGZINMQ1ZWQ4ZTQ0OT";
		String authToken = "MGM1M2JiZGYwOTdlYmJkNzg2ZTFjZTI3NDc4MWRi";
		String src = "19713022416";
		String dst = phoneNumber.substring(1);

		RestAPI api = new RestAPI(authId, authToken, "v1");

		LinkedHashMap<String, String> parameters = new LinkedHashMap<String, String>();
		parameters.put("src", src);
		parameters.put("dst", dst);
		parameters.put("text", messageBody);

		try {
			MessageResponse msgResponse = api.sendMessage(parameters);
			return msgResponse.message;
		} catch (PlivoException e) {
			System.out.println(e.getLocalizedMessage());
		}
		return null;
	}

	public static String SMSSender(String to, String msg) {
		String rsp = "priyeshtricon";
		String user = "709133";
		String pwd = "";
		String sid = "WEBSMS";
		String fl = "0";

		try {
			// Construct The Post Data
			String data = URLEncoder.encode("user", "UTF-8") + "="
					+ URLEncoder.encode(user, "UTF-8");
			data += "&" + URLEncoder.encode("pwd", "UTF-8") + "="
					+ URLEncoder.encode(pwd, "UTF-8");
			data += "&" + URLEncoder.encode("to", "UTF-8") + "="
					+ URLEncoder.encode(to, "UTF-8");
			data += "&" + URLEncoder.encode("msg", "UTF-8") + "="
					+ URLEncoder.encode(msg, "UTF-8");
			data += "&" + URLEncoder.encode("sid", "UTF-8") + "="
					+ URLEncoder.encode(sid, "UTF-8");
			data += "&" + URLEncoder.encode("fl", "UTF-8") + "="
					+ URLEncoder.encode(fl, "UTF-8");

			// Push the HTTP Request
			URL url = new URL(
					"http://login.smsgatewayhub.com/smsapi/pushsms.aspx?");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);

			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write(data);
			wr.flush();

			// Read The Response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				// Process line…
				retval += line;
			}
			wr.close();
			rd.close();

			System.out.println(retval);
			rsp = retval;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rsp;
	}

	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		String authId = "MAOGZINMQ1ZWQ4ZTQ0OT";
		String authToken = "MGM1M2JiZGYwOTdlYmJkNzg2ZTFjZTI3NDc4MWRi";
		String src = "19713022416";
		String dst = "918095017471";

		RestAPI api = new RestAPI(authId, authToken, "v1");

		LinkedHashMap<String, String> parameters = new LinkedHashMap<String, String>();
		parameters.put("src", src);
		parameters.put("dst", dst);
		parameters.put("text", "Hello shailesh");

		try {
			MessageResponse msgResponse = api.sendMessage(parameters);
			System.out.println(msgResponse.apiId);
			if (msgResponse.serverCode == 202) {
				System.out.println(msgResponse.messageUuids.get(0).toString());
			} else {
				System.out.println(msgResponse.error);
			}
		} catch (PlivoException e) {
			System.out.println(e.getLocalizedMessage());
		}
		
		SMSSender("8050781001", "hello");
	}*/

}