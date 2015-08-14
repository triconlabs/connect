package com.tricon.labs.join.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.text.Normalizer;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.RandomStringUtils;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;
import com.tricon.labs.join.exceptions.ApplicationException;
import com.tricon.labs.join.exceptions.ExceptionType;
import com.tricon.labs.join.exceptions.PropertyNotFoundException;

@Component
public class GenUtil {

	protected GenUtil() {
	}

	private static final Gson gson = new Gson();

	private static Logger LOGGER = LoggerFactory.getLogger(GenUtil.class);

	//final static Map<String, String> systemEnvironment = System.getenv();

	public static Environment applicationEnvironment;
	
	private static JSONParser jsonParser = new JSONParser();

	public static Object getObjectFromJson(String json)
			throws ApplicationException {
		try {
			return jsonParser.parse(json);
		} catch (Exception e) {
			throw new ApplicationException(ExceptionType.JSON_CONVERSION, "Given JSON could not be converted to any Object type", e);
		}
	}

	/**
	 * 
	 * @param json
	 * @param t
	 * @return
	 * @throws ApplicationException
	 */
	public static <T> T getObjectFromJson(String json, Class<T> t)
			throws ApplicationException {
		try {
			return gson.fromJson(json, t);
		} catch (Exception e) {
			throw new ApplicationException(ExceptionType.JSON_CONVERSION, "Given JSON could not be converted to Object type" + t.getName(),	e);
		}
	}

	public static <T> T getObjectFromJson(String json, Type type)
			throws ApplicationException {
		try {
			return gson.fromJson(json, type);
		} catch (Exception e) {
			throw new ApplicationException(ExceptionType.JSON_CONVERSION, "Given JSON could not be converted to Object type" + type.toString(),	e);
		}
	}

	/**
	 * 
	 * @param json
	 * @param t
	 * @return
	 */
	public static <T> T getObjectFromJson(Reader json, Class<T> t) {
		try {
			return gson.fromJson(json, t);
		} catch (Exception e) {
			// TODO
			return null;
		}
	}

	/**
	 * 
	 * @param city
	 * @return
	 */
	public static String getJsonFromObject(Object object) {
		try {
			return gson.toJson(object);
		} catch (Exception e) {
			// TODO
			return null;
		}
	}

	/**
	 * 
	 * @return
	 * @throws ApplicationException 
	 */
	public static String getCurrentDomain() throws ApplicationException {
		return getEnvProperty(Constants.APPLICATION_DOMAIN, true);
	}
	
	/**
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	public static String getCurrentEnvironment() throws ApplicationException {
		return getEnvProperty(Constants.APPLICATION_ENV, true);
	}

	/**
	 * 
	 * @param propertyFile
	 * @return
	 * @throws ApplicationException 
	 */
	private static String getDomainPropertyFile(String propertyFile) throws ApplicationException {
		String domainPropertyFile = propertyFile.replaceAll(
				Constants.PLACE_HOLDER_DOMAIN, getCurrentDomain());
		return domainPropertyFile;
	}

	/**
	 * To get properties from property file.
	 * 
	 * @param property
	 * @return
	 * @throws ApplicationException 
	 */
	public static String getDomainProperty(String property, String propertyFile) throws ApplicationException {
		return getProperty(property, getDomainPropertyFile(propertyFile));
	}

	/**
	 * To get properties from property file.
	 * 
	 * @param property
	 * @return
	 */
	private static String getProperty(String property, String propertyFile) {
		String result = "";
		Properties props = new Properties();
		InputStream is = GenUtil.class.getResourceAsStream(propertyFile);
		result = props.getProperty(property);
		try {
			props.load(is);
			is.close();
			result = props.getProperty(property);
		} catch (Exception e) {
			LOGGER.error(
					"Error reading input from properties file, error - {}",
					e.getMessage());
		}
		LOGGER.info("[GenUtil] [getProperty] result_property : " + result);
		return result == null ? "" : result;
	}

	/**
	 * 
	 * @param object
	 * @param bookingResponseXslFile
	 * @return
	 */
	/*
	 * public static String getHtml(Object object, String xslFile) { InputStream
	 * xslFlieStream = GenUtil.class.getResourceAsStream(xslFile); return
	 * transformXML(getXml(object), xslFlieStream); }
	 */

	/**
	 * 
	 * @param object
	 * @return
	 */
	/*
	 * public static String getXml(Object object) { XStream xStream = new
	 * XStream(new DomDriver()); xStream.processAnnotations(object.getClass());
	 * return xStream.toXML(object); }
	 */

	/**
	 * 
	 * @param xmlString
	 * @param xslFlieStream
	 * @return
	 */
	protected static String transformXML(String xmlString,
			InputStream xslFlieStream) {
		String formattedOutput = "";
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer(new StreamSource(
					xslFlieStream));
			transformer.setOutputProperty("method", "xml");
			StreamSource xmlSource = new StreamSource(new ByteArrayInputStream(
					xmlString.getBytes()));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			transformer.transform(xmlSource, new StreamResult(baos));
			formattedOutput = baos.toString();
		} catch (Exception e) {
			LOGGER.error("error : " + e.getMessage());
		}
		LOGGER.info("formattedOutput - {}", formattedOutput);
		return formattedOutput;
	}

	public static String formSatckTraceMessage(
			StackTraceElement[] stackTraceElements) {
		String stackTraceMessage = "stackTraceMessage \n";
		for (StackTraceElement stackTraceElement : stackTraceElements) {
			stackTraceMessage = stackTraceMessage
					+ stackTraceElement.toString() + "\n";
		}
		LOGGER.info("stackTraceMessage - {}", stackTraceMessage);
		return stackTraceMessage;
	}

	/*
	 * public static String createPdf(String htmlString, String fileName) {
	 * fileName = fileName + ".pdf"; try { final ITextRenderer iTextRenderer =
	 * new ITextRenderer(); iTextRenderer.setDocumentFromString(htmlString);
	 * iTextRenderer.layout(); final FileOutputStream fileOutputStream = new
	 * FileOutputStream( new File(fileName));
	 * iTextRenderer.createPDF(fileOutputStream, true);
	 * fileOutputStream.close(); } catch (final DocumentException e) {
	 * e.printStackTrace(); } catch (FileNotFoundException e) {
	 * e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }
	 * return fileName; }
	 */

	public static File getFileDir(File file) throws ApplicationException {
		if (!file.exists()) {
			if (!file.mkdir())
				LOGGER.error("Directory cannont be created - {}",
						file.getAbsolutePath());
			throw new ApplicationException(
					ExceptionType.FILE_TRANSFER,
					"Directory cannont be created :: " + file.getAbsolutePath(),
					null);
		}
		return file;
	}

	/**
	 * Reads UTF-8 text file from classpath and returns it's String content
	 * 
	 * @param filePath
	 * @throws IOException
	 */
	/*public static String readFile(String filePath) throws IOException {
		final StringBuilder strBuilder = new StringBuilder();
		final InputStream inputStream = GenUtil.class
				.getResourceAsStream(filePath);
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputStream))) {
			String line = null;
			while ((line = reader.readLine()) != null)
				strBuilder.append(line).append("\n");
		}
		return strBuilder.toString();
	}

	public static String removeSpecialChars(String string, String charge) {
		try {
			string = string.trim().replaceAll("[^\\dA-Za-z ]", "")
					.replaceAll("\\s+", "-");
			return string + "-" + charge;
		} catch (Exception e) {
			return null;
		}
	}*/

	public static String removeSpecialChars(String string) {
		try {
			return string.trim().replaceAll("[^\\dA-Za-z ]", "")
					.replaceAll("\\s+", "-");
		} catch (Exception e) {
			return null;
		}
	}

	public static String normaliseString(String string) {
		try {
			return Normalizer.normalize(string, Normalizer.Form.NFD)
					.replaceAll("[^\\p{ASCII}]", "");
		} catch (Exception e) {
			return null;
		}

	}

	public static String getRandomAlphaNum(int i) {
		return RandomStringUtils.random(i, true, true).toUpperCase();
	}

	public static String getRandomNum(int i) {
		return RandomStringUtils.random(i, false, true);
	}

	public static String getRandomAlpha(int i) {
		return RandomStringUtils.random(i, true, false).toUpperCase();
	}

	/**
	 * current user in session
	 * 
	 * @return
	 */
	public static String getCurrentUser() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes == null)
			return null;
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("userId") != null)
			return (String) session.getAttribute("userId");
		//StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		if (session == null)
			LOGGER.error("<<<<<<<<<<<<<<<<<<<<<<<<<<NO USER IN SESSION>>>>>>>>>>>>>>>>>>>>>>>>>");
		return null;
	}

	public static Boolean isAnonymousUser() {
		return SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal().equals("anonymousUser");
	}

	public static String getEnvProperty(String key, Boolean isMandatory) throws ApplicationException {
		//if (systemEnvironment.get(key) == null) {
			String property = applicationEnvironment.getProperty(key);
			if (property == null && isMandatory) {
				LOGGER.error("property {} not found", key);
				throw new PropertyNotFoundException("property " + key + " not found");
			}
			return property;
		//}
		//return systemEnvironment.get(key);
	}
}
