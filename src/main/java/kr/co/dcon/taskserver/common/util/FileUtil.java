package kr.co.dcon.taskserver.common.util;

import kr.co.dcon.taskserver.common.constants.CommonConstants;
import kr.co.dcon.taskserver.common.constants.ResultCode;
import kr.co.dcon.taskserver.common.exception.RuntimeExceptionBase;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;


public class FileUtil {

	public static final String MSIE = "MSIE";
	public static final String TRIDENT = "Trident";
	public static final String EDGE = "Edge";
	public static final String CHROME = "Chrome";
	public static final String OPERA = "Opera";
	public static final String SAFARI = "Safari";
	public static final String FIREFOX = "Firefox";

	private FileUtil() {}

	public static String chunkFileSurfix(Integer index) {
		return MessageFormat.format(CommonConstants.CHUNK_SPILTER, index);
	}

	public static String getBrowser(HttpServletRequest req) {
		String userAgent = req.getHeader("User-Agent");
		if (userAgent.contains(MSIE) || userAgent.contains(TRIDENT) || userAgent.contains(EDGE)) {
			return "MSIE";
		} else if (userAgent.contains(CHROME)) {
			return CHROME;
		} else if (userAgent.contains(OPERA)) {
			return OPERA;
		} else if (userAgent.contains(SAFARI)) {
			return SAFARI;
		} else if (userAgent.contains(FIREFOX)) {
			return FIREFOX;
		} else {
			return null;
		}
	}

	public static String getFileName(String fileName) {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = null;
		if (requestAttributes != null) {
			request = ((ServletRequestAttributes) requestAttributes).getRequest();
		} else {
			throw new RuntimeExceptionBase(ResultCode.FILE_NAME_EXCEPTION);
		}

		String browser = getBrowser(request);
		browser = browser == null ? "" : browser;
		String reFileNm = null;
		try {
			if (browser.equals(MSIE) || browser.equals(TRIDENT) || browser.equals(EDGE)) {
				reFileNm = URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()).replace("\\+", "%20");
			} else {
				reFileNm = checkBrowser(fileName, browser);
			}
		} catch (Exception e) {
			throw new RuntimeExceptionBase(ResultCode.FILE_NAME_EXCEPTION);
		}
		return reFileNm;
	}

	private static String checkBrowser(String fileName, String browser) throws UnsupportedEncodingException {
		String reFileNm;
		if (browser.equals(CHROME)) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < fileName.length(); i++) {
				char c = fileName.charAt(i);
				if (c > '~') {
					sb.append(URLEncoder.encode(Character.toString(c), StandardCharsets.UTF_8.name()));
				} else {
					sb.append(c);
				}
			}
			reFileNm = sb.toString();
		} else {
			reFileNm = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
		}
		if (browser.equals(SAFARI) || browser.equals(FIREFOX)) {
			reFileNm = URLDecoder.decode(reFileNm, StandardCharsets.UTF_8.name());
		}
		return reFileNm;
	}

	public static String getFileNameWithoutExtension(String filename) {
		if((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename != null ? filename.toLowerCase() : "";
	}
}
