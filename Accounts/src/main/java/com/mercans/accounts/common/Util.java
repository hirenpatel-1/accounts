package com.mercans.accounts.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.json.JsonValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * A common Util class
 * 
 * @author Hiren.Patel
 *
 */

public class Util {

	private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);

	/**
	 * Generate uuid
	 * 
	 * @return string
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	/**
	 * Get Gender
	 * 
	 * @param gender
	 * @return string
	 */
	public static String getGender(String gender) {
		String gen = "";
		if (gender.equals(Constants.MALE))
			gen = "M";
		else if (gender.equals(Constants.FEMALE))
			gen = "F";

		return gen;
	}

	/**
	 * Convert string date to date
	 * 
	 * @param strDate
	 * @param dateFormat
	 * @return date
	 */
	public static Date stringToDate(String strDate, String dateFormat) {
		Date date = null;
		if (strDate != null && !strDate.isEmpty()) {
			SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

			try {
				date = formatter.parse(strDate);
			} catch (ParseException e) {
				LOGGER.error(strDate + " is not a valid Date");
			}
		}
		return date;
	}

	/**
	 * Convert date format ddMMyy to yyyy-MM-dd
	 * 
	 * @param str
	 * @return
	 */
	public static String convertDdMMyyToYyyyMMdd(String str) {
		if (str == null || "".equals(str))
			return str;
		if (str.length() < 6) {
			str = "0" + str;
		}
		SimpleDateFormat sdfEbank = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT);
		SimpleDateFormat sdfCoreBank = new SimpleDateFormat(Constants.DDMMYY_DATE_FORMAT);
		Date date = null;
		try {
			date = (Date) sdfCoreBank.parse(str);
		} catch (Exception ex) {
			LOGGER.error("Error in convertDdMMyyToYyyyMMdd:"+ex.getMessage());
			ex.printStackTrace();
			return "";
		}

		return sdfEbank.format(date);
	}

	/**
	 * Convert date format yyMMdd to yyyy-MM-dd
	 * @param str
	 * @return string
	 */
	public static String convertyyMMddToyyyyMMdd(String str) {
		if (str == null || "".equals(str))
			return str;
		str = str.substring(0, 8);
		SimpleDateFormat sdfEbank = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT);
		SimpleDateFormat sdfCoreBank = new SimpleDateFormat(Constants.YYMMDD_DATE_FORMAT);
		Date date = null;
		try {
			date = (Date) sdfCoreBank.parse(str);
		} catch (Exception ex) {
			LOGGER.error("Error in convertyyMMddToyyyyMMdd:"+ex.getMessage());
			ex.printStackTrace();
			return "";
		}

		return sdfEbank.format(date);
	}

	/**
	 * 
	 * @param jsonValue
	 * @return
	 */
	public static String getString(JsonValue jsonValue) {
		String val = null;
		if (jsonValue != null && jsonValue != jsonValue.NULL) {
			val = jsonValue.toString();
			val = val.replaceAll("\"", "");
		}
		return val;
	}

	/**
	 * 
	 * @param jsonNumber
	 * @return
	 */
	public static double getNumber(javax.json.JsonNumber jsonNumber) {
		double val = 0;
		if (jsonNumber != null) {
			val = jsonNumber.doubleValue();
		}
		return val;
	}

	/**
	 * 
	 * @param dateJsonValue
	 * @return
	 */
	public static Date jsonValueToDate(JsonValue dateJsonValue) {
		return stringToDate(getString(dateJsonValue), Constants.DEFAULT_DATE_FORMAT);
	}

	/**
	 * Generate Employee code
	 * @param hireDate
	 * @return
	 */
	public static String generateEmployeeCode(Date hireDate) {
		SimpleDateFormat sdfCoreBank = new SimpleDateFormat(Constants.YYMMDD_DATE_FORMAT);

		return sdfCoreBank.format(hireDate) + "10";
	}

	/**
	 * Get Action code
	 * @param action
	 * @return
	 */
	public static String getAction(String action) {
		String act = "";
		if (action.equalsIgnoreCase(Constants.ADD))
			act = Constants.HIRE;
		else if (action.equalsIgnoreCase(Constants.UPDATE))
			act = Constants.CHANGE;
		else if (action.equalsIgnoreCase(Constants.DELETE))
			act = Constants.TERMINATE;

		return act;

	}

}