package com.mercans.accounts.common;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mercans.accounts.model.Data;
import com.mercans.accounts.model.DataOutputItem;
import com.mercans.accounts.model.PayComponents;
import com.mercans.accounts.model.PayLoad;
import com.mercans.accounts.model.SourceInput;
import com.mercans.accounts.model.UConfig;

/**
 * A Json Utility class to read data from CSV file and generate a JSON file
 * 
 * @author Hiren.Patel
 *
 */
@Component
public class JsonUtility {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtility.class);

	@Autowired
	private UConfig uConfig;

	ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
	private List<SourceInput> inputList = new ArrayList<SourceInput>();
	private SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.FILE_OUPUUT_DATE_FORMAT);

	/**
	 * Reading csv file and Parsing CSV file and adding into Array List
	 */
	public void setValues() {
		Map<String, String> files = uConfig.getFiles();
		try {
			String inputFileName = files.get(Constants.INPUT);
			CSVParser parser = CSVParser.parse(new File(files.get(Constants.BASE) + inputFileName),
					Charset.forName(Constants.UTF_FORMAT), CSVFormat.DEFAULT.withHeader());
			for (CSVRecord record : parser.getRecords()) {
				if (record.size() == parser.getHeaderNames().size()) {

					List<String> headers = parser.getHeaderNames();
					SourceInput input = new SourceInput();
					for (int i = 0; i < headers.size(); i++) {
						invokeSetter(headers.get(i), input, record.get(i));
					}
					inputList.add(input);
				}
			}

			transform(inputFileName);
		} catch (Exception e) {
			LOGGER.error("Error in setValues:" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Process start to convert CSV file to JSON format
	 */
	public void process() {
		setValues();
	}

	/**
	 * Setting filedName in the model with the value
	 * 
	 * @param fieldName
	 * @param obj
	 * @param value
	 */
	public void invokeSetter(String fieldName, Object obj, Object value) {
		try {
			FieldUtils.writeDeclaredField(obj, fieldName, value);

		} catch (IllegalAccessException e) {
			LOGGER.error("Error in invokeSetter:" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Reading data from Array list and transform to JSON format
	 * 
	 * @param filename
	 */
	public void transform(String filename) {
		List<PayLoad> payLoadList = new ArrayList<PayLoad>();
		List<String> errorList = new ArrayList<String>();
		try {
			if (!inputList.isEmpty()) {
				for (SourceInput input : inputList) {

					String empCode = input.getContract_workerId();
					String fullName = input.getWorker_name();
					String action = input.getACTION();
					String terminationDate = input.getContract_endDate();
					String hireDate = input.getContract_workStartDate();

					if (!action.equalsIgnoreCase("add") && empCode.isEmpty()) {
						errorList.add(input.getWorker_name() + " don't have employee code and action is "
								+ Util.getAction(action));
					} else if (terminationDate.isEmpty() && action.equalsIgnoreCase("delete")) {
						errorList.add("Employee code " + input.getContract_workerId()
								+ " don't have termination date and action is " + Util.getAction(action));
					} else if (fullName.isEmpty()) {
						errorList.add("Employee code " + input.getContract_workerId()
								+ " having empty Full Name field and it's mandatory field in the database");
					} else if (hireDate.isEmpty()) {
						errorList.add(input.getWorker_name()
								+ " don't have the hire date and it's mandatory in the database");
					} else {

						// pay components
						List<PayComponents> payCompList = new ArrayList<PayComponents>();

						PayComponents payComp = new PayComponents();
						payComp.setAmount(input.getPay_amount());
						payComp.setCurrency(input.getPay_currency());
						payComp.setStartDate(Util.convertDdMMyyToYyyyMMdd(input.getPay_effectiveFrom()));
						payComp.setEndDate(Util.convertDdMMyyToYyyyMMdd(input.getPay_effectiveTo()));

						payCompList.add(payComp);

						// Data for entity person
						Data data = new Data();
						data.setFullName(fullName);
						data.setGender(Util.getGender(input.getWorker_gender()));
						data.setBirthDate(Util.convertyyMMddToyyyyMMdd(input.getWorker_personalCode()));
						data.setHireDate(Util.convertDdMMyyToYyyyMMdd(input.getContract_workStartDate()));
						data.setTerminationDate(Util.convertDdMMyyToYyyyMMdd(terminationDate));

						// pad load
						PayLoad payLoad = new PayLoad();
						payLoad.setEmployeeCode(empCode);
						payLoad.setAction(Util.getAction(action));
						payLoad.setPayComponents(payCompList);
						payLoad.setData(data);

						payLoadList.add(payLoad);
					}

				}
			}

			DataOutputItem dataOutItem = new DataOutputItem();
			dataOutItem.setUuid(Util.getUUID());
			dataOutItem.setFname(filename);
			dataOutItem.setErrors(errorList);
			dataOutItem.setPayload(payLoadList);

			writeToFile(dataOutItem);
		} catch (Exception e) {
			LOGGER.error("Error in transform:" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Write a file in JSON format
	 * 
	 * @param dataOutputItem
	 */
	public void writeToFile(DataOutputItem dataOutputItem) {
		try {
			FileWriter writer = new FileWriter(
					new File(uConfig.getFiles().get("BASE") + "Output_" + dateFormat.format(new Date())));
			mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

			String jsonStr = mapper.writeValueAsString(dataOutputItem);

			writer.append(jsonStr);
			writer.close();

		} catch (IOException e) {
			LOGGER.error("Error in writeToFile:" + e.getMessage());
			e.printStackTrace();
		}
	}

}