package com.mercans.accounts.common;

public class Constants {
	
	private Constants() {
		throw new IllegalStateException("Constant class");
	}

	
	//Action code
	public static final String ADD = "add";
	public static final String UPDATE = "update";
	public static final String DELETE = "delete";
	public static final String HIRE = "hire";
	public static final String CHANGE = "change";
	public static final String TERMINATE = "terminate";
	
	//Gender
	public static final String MALE = "male";
	public static final String FEMALE = "female";
	
	//Date and time format
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	public static final String YYMMDD_DATE_FORMAT = "yyMMdd";
	public static final String DDMMYY_DATE_FORMAT = "ddMMyy";
	public static final String FILE_OUPUUT_DATE_FORMAT = "YYY_MM_DD_HH_mm_ss";
	
	public static final String BASE = "BASE";
	public static final String INPUT = "INPUT";
	public static final String UTF_FORMAT = "UTF-8";
	
	//Json fields
	public static final String PAYLOAD = "payload";
	public static final String EMPLOYEE_CODE = "employeeCode";
	public static final String ACTION = "action";	
	public static final String DATA = "data";
	public static final String FULLNAME = "fullName";
	public static final String GENDER = "gender";	
	public static final String BIRTHDATE = "birthDate";
	public static final String HIREDATE = "hireDate";
	public static final String TERMINATION_DATE = "terminationDate";	
	public static final String PAYCOMPONENTS = "payComponents";
	public static final String AMOUNT = "amount";
	public static final String CURRENCY = "currency";
	public static final String START_DATE = "startDate";
	public static final String END_DATE = "endDate";
	
	
	
	
	
	
	
	
}
