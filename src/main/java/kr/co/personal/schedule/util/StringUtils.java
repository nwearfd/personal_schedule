package kr.co.personal.schedule.util;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class StringUtils {
    /**
     * 주어진 문자열이 null 또는 공백일 경우 참 반환
	 * @param str 문자열
	 * @return null 또는 공백일 경우 true
     */
    public static boolean isEmpty(String str) {
        return (str == null || str.trim().isEmpty());
    }

	/**
	 * 주어진 문자열들이 null 또는 공백이 아닐 경우 참 반환
	 * @param str 문자열
	 * @return null 또는 공백이 아닐 경우 true
	 */
	public static boolean isEmpty(String... str) {
		for (String s : str) {
			if (isEmpty(s)) {
				return true;
			}
		}
		return false;
	}

    /**
     * s0이 null또는 공백일경우 s1을 반환하고 아닐경우 s0을 반환
     * @param s0 문자열
     * @param s1 대체 문자열
     * @return 문자열이 null 또는 공백일 경우 대체 문자열 반환
     */

	public static String avoidNull(String s0, String s1) {
        return isEmpty(s0) ? s1 : s0;
    }

    /**
     * s가 null또는 공백일경우 ""을 반환하고 아닐경우 s를 반환
     * @param s 문자열
     * @return 문자열이 null 또는 공백일 경우 "" 반환
     */

	public static String avoidNull(String s) {
		return avoidNull(s, "");
	}

    /**
     * 문자열 같은지 비교
     * @param str1 비교 문자열
     * @param str2 비교 문자열
     * @return 문자열이 같으면 true, 아니면 false
     */
	public static boolean equals(String str1, String str2) {
		return str1 == null ? str2 == null : str1.equals(str2);
	}
	
	public static String replace(String str, String replacedStr, String replaceStr) {
		String newStr = "";
		
		if (!(str.contains(replacedStr))) {
			return str;
		}
		final String s1 = str.substring(0, str.indexOf(replacedStr));
		final String s2 = str.substring(str.indexOf(replacedStr) + replacedStr.length());
		newStr = s1 + replaceStr + s2;
		
		return newStr;
	}

	/**
	 * 1000단위 콤마
	 * @param str
	 * @return
	 */
	public static String addComma(String str) {
		if (str == null || str.isEmpty()) {
			return str;
		}
		return str.replaceAll("(\\d)(?=(\\d{3})+(?!\\d))", "$1,");
	}

	/**
	 * date to String
	 */
	public static String formatDate(Date date, String format){
		return new SimpleDateFormat(format).format(date);
	}

}
