//package com.tricon.labs.join.util;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.scheduling.annotation.Async;
//
//import com.google.android.gcm.server.Message;
//import com.google.android.gcm.server.MulticastResult;
//import com.google.android.gcm.server.Result;
//import com.google.android.gcm.server.Sender;
//import com.notnoop.apns.APNS;
//import com.notnoop.apns.ApnsService;
//import com.notnoop.exceptions.InvalidSSLConfig;
//import com.tricon.labs.join.entity.ConnectDevice;
//import com.tricon.labs.join.exceptions.ApplicationException;
//
//public class ConnectPushUtil {
//	
//	private static Logger LOGGER = LoggerFactory.getLogger(ConnectPushUtil.class);
//	
//	public static final String MESSAGE_KEY = "message";
//	public static final String GOOGLE_SERVER_KEY = "google.server.key";
//	public static final String APNS_CERTIFICATE_LOCATION_DEV = "apns.certificate.location.dev";
//	public static final String APNS_CERTIFICATE_LOCATION_PROD = "apns.certificate.location.prod";
//	public static final String APNS_CERTIFICATE_PASSWORD_DEV = "apns.certificate.password.dev";
//	public static final String APNS_CERTIFICATE_PASSWORD_PROD = "apns.certificate.password.prod";
//	
//	public static void sendPush(ConnectDevice device, String alert, Map<String, String> entries) {
//		try {
//			switch (device.getDeviceType()) {
//			case IOS:
//				ConnectPushUtil.sendIOSNotificationUnicast(device.getDeviceId(), alert, entries);
//			case ANDROID:
//				ConnectPushUtil.sendAndroidNotificationUnicast(device.getDeviceId(), alert, entries);
//			default:
//				break;
//			}
//		} catch (ApplicationException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public static void sendPush(List<ConnectDevice> devices, String alert, Map<String, String> entries) {
//		List<String> androidList = new ArrayList<String>();
//		List<String> iosList = new ArrayList<String>();
//		for (ConnectDevice device : devices) {
//			switch (device.getDeviceType()) {
//			case IOS:
//				iosList.add(device.getDeviceId());
//			case ANDROID:
//				androidList.add(device.getDeviceId());
//			default:
//				break;
//			}
//		}
//		try {
//			ConnectPushUtil.sendAndroidNotificationMulticast(androidList, alert, entries);
//			ConnectPushUtil.sendIOSNotificationMulticast(iosList, alert, entries);
//		} catch (ApplicationException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * for Android
//	 * @param userMessage
//	 * @param regId
//	 * @param alert 
//	 * @throws ApplicationException 
//	 */
//	@Async
//	private static void sendAndroidNotificationUnicast(String regId, String alert, Map<String, String> data) throws ApplicationException {
//		data.put(MESSAGE_KEY, alert);
//		Result result = null;
//		Sender sender = new Sender(GenUtil.getEnvProperty(GOOGLE_SERVER_KEY, true));
//		Message message = new Message.Builder().timeToLive(30).delayWhileIdle(true).setData(data).build();
//		try {
//			result = sender.send(message, regId, 3);
//			LOGGER.debug(result.toString());
//			System.out.println(result.toString());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	/**
//	 * 
//	 * @param regIds
//	 * @param data
//	 * @throws ApplicationException 
//	 */
//	@Async
//	private static void sendAndroidNotificationMulticast(List<String> regIds, String alert, Map<String, String> data) throws ApplicationException{
//		data.put(MESSAGE_KEY, alert);
//		MulticastResult result = null;
//		Sender sender = new Sender(GenUtil.getEnvProperty(GOOGLE_SERVER_KEY, true));
//		Message message = new Message.Builder().timeToLive(1).delayWhileIdle(false).setData(data).build();
//		try {
//			result = sender.send(message, regIds, 3);
//			LOGGER.debug(result.toString());
//			System.out.println(result.toString());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 
//	 * @param tokens
//	 * @param userMessage
//	 * @param data
//	 * @throws ApplicationException 
//	 * @throws InvalidSSLConfig 
//	 */
//	@Async
//	private static void sendIOSNotificationMulticast(List<String> tokens, String alert, Map<String, String> data) throws InvalidSSLConfig, ApplicationException {
//		String envProperty = GenUtil.getEnvProperty("current.env", true);
//		ApnsService service = null;
//		switch (envProperty) {
//		case "prod":
//			service = APNS.newService().withCert(ConnectPushUtil.class.getResourceAsStream(GenUtil.getEnvProperty(APNS_CERTIFICATE_LOCATION_PROD, true)), GenUtil.getEnvProperty(APNS_CERTIFICATE_PASSWORD_PROD, true)).withProductionDestination().build();
//			break;
//		default:
//			service = APNS.newService().withCert(ConnectPushUtil.class.getResourceAsStream(GenUtil.getEnvProperty(APNS_CERTIFICATE_LOCATION_DEV, true)), GenUtil.getEnvProperty(APNS_CERTIFICATE_PASSWORD_DEV, true)).withSandboxDestination().build();
//			break;
//		}
//		String payload = APNS.newPayload().alertBody(alert).customFields(data).build();
//		service.push(tokens, payload);
//		LOGGER.debug(service.toString());
//		System.out.println(service.toString());
//	}
//
//	/**
//	 * TODO figure out status of sending push and take corresponding action.
//	 * @param token
//	 * @param alert 
//	 * @param userMessage
//	 * @param data
//	 * @throws ApplicationException 
//	 * @throws InvalidSSLConfig 
//	 */
//	@Async
//	private static void sendIOSNotificationUnicast(String token, String alert, Map<String, String> data) throws InvalidSSLConfig, ApplicationException {
//		String envProperty = GenUtil.getEnvProperty("current.env", true);
//		ApnsService service = null;
//		switch (envProperty) {
//		case "prod":
//			service = APNS.newService().withCert(ConnectPushUtil.class.getResourceAsStream(GenUtil.getEnvProperty(APNS_CERTIFICATE_LOCATION_PROD, true)), GenUtil.getEnvProperty(APNS_CERTIFICATE_PASSWORD_PROD, true)).withProductionDestination().build();
//			break;
//		default:
//			service = APNS.newService().withCert(ConnectPushUtil.class.getResourceAsStream(GenUtil.getEnvProperty(APNS_CERTIFICATE_LOCATION_DEV, true)), GenUtil.getEnvProperty(APNS_CERTIFICATE_PASSWORD_DEV, true)).withSandboxDestination().build();
//			break;
//		}
//		String payload = APNS.newPayload().alertBody(alert).customFields(data).build();
//		service.push(token, payload);
//		LOGGER.debug(service.toString());
//		System.out.println(service.toString());
//	}
//	
//	/*public static void main(String[] args) throws ApplicationException {
//		Map<String, String> data = new HashMap<String, String>();
//		data.put("notificationType", "UPDATEMEMORY");
//		data.put("memoryId", "memoryId");
//		data.put(MESSAGE_KEY, "gautam");
////		sendAndroidNotificationUnicast("APA91bE9hT-Nmw4ay7F9SdwqxCd6C8zMxkNSd_SXoXFaxjg9kwnRrduNiKDaSnGf4E2krv-8NCT1z2TLTcsUDOXrFaHkKCRiZdihiAOsrMIiHPVoeIrKGjxb-z_yazij-ldVcVxLeiUiu-zv2c-YlLNiWWpvruh1xQ",
////				"hello", data);
//		*//** IOS Testing **//*
////		ApnsService service = APNS.newService().withCert(ConnectPushUtil.class.getResourceAsStream("/cherryApns.p12"), "1234abcd1234").withSandboxDestination().build();
////		String payload = APNS.newPayload().alertBody("hello").build();
////		service.push("e5cfe670 d5e9fc11 a5992de2 357829e9 74e9b60a 0ce1bb42 d51f6d3c d9db15ca", payload);
////		System.out.println(service.toString());
////		
//		*//** Android Testing **//*
//		Result result = null;
//		Sender sender = new Sender("AIzaSyA60LGUVEPfSdAbOD-D8Vm2fyWCcjGRkHk");
//		Message message = new Message.Builder().timeToLive(30).delayWhileIdle(true).setData(data).build();
//		try {
//			result = sender.send(message, "APA91bE9hT-Nmw4ay7F9SdwqxCd6C8zMxkNSd_SXoXFaxjg9kwnRrduNiKDaSnGf4E2krv-8NCT1z2TLTcsUDOXrFaHkKCRiZdihiAOsrMIiHPVoeIrKGjxb-z_yazij-ldVcVxLeiUiu-zv2c-YlLNiWWpvruh1xQ", 3);
//			LOGGER.debug(result.toString());
//			System.out.println(result.toString());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}*/
//	
//}
