package com.mercans.accounts.controller;

import java.io.StringReader;
import java.util.Date;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercans.accounts.common.Constants;
import com.mercans.accounts.common.Util;
import com.mercans.accounts.model.Person;
import com.mercans.accounts.model.SalaryComponent;
import com.mercans.accounts.repository.PersonRespository;
import com.mercans.accounts.repository.SalaryComponentRespository;

/**
 * A Rest Controller class
 * @author Hiren.Patel
 *
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class AccountController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	PersonRespository personRespository;

	@Autowired
	SalaryComponentRespository salaryComponentRespository;

	/**
	 * This method is reading from JSON file, manipulation and saving values in the database
	 * @param body
	 * @return
	 */
	@PostMapping("/person")
	public List<Person> createPerson(@RequestBody String body) {

		LOGGER.info("******* Start AccountController ********");

		JsonReader jsonReader = Json.createReader(new StringReader(body));
		JsonObject in_jsonObject = jsonReader.readObject();
		jsonReader.close();
		Person person = null;

		JsonArray jsonArr = in_jsonObject.getJsonArray(Constants.PAYLOAD);

		if (jsonArr != null && !jsonArr.isEmpty()) {
			for (int row = 0; row < jsonArr.size(); row++) {
				person = null;
				JsonObject payLoadJson = jsonArr.getJsonObject(row);

				String action = Util.getString(payLoadJson.get(Constants.ACTION));

				person = getPerson(payLoadJson, action);

				if (person != null) {
					personRespository.save(person);

					SalaryComponent salComp = getSalaryComponents(payLoadJson, action, person.getId());

					if (salComp != null)
						salaryComponentRespository.save(salComp);
				}
			}
		}
		LOGGER.info(jsonArr.size() + " Records saved.");
		return personRespository.findAll();
	}

	/**
	 * Read from Json file and manipulation and prepare Person model
	 * 
	 * @param payLoadJson
	 * @param action
	 * @return
	 */
	public Person getPerson(JsonObject payLoadJson, String action) {

		Person person = null;
		String employeecode = Util.getString(payLoadJson.get(Constants.EMPLOYEE_CODE));

		JsonObject data = payLoadJson.getJsonObject(Constants.DATA);
		String fullName = Util.getString(data.get(Constants.FULLNAME));
		String gender = Util.getString(data.get(Constants.GENDER));
		Date birthDate = Util.jsonValueToDate(data.get(Constants.BIRTHDATE));
		Date hireDate = Util.jsonValueToDate(data.get(Constants.HIREDATE));
		Date terminationDate = Util.jsonValueToDate(data.get(Constants.TERMINATION_DATE));

		if (gender.isEmpty())
			gender = null;

		if (employeecode.isEmpty()) {
			employeecode = Util.generateEmployeeCode(hireDate);
		}

		if (action.equals(Constants.HIRE)) {
			return new Person(fullName, gender, birthDate, employeecode, hireDate, terminationDate);
		} else if (action.equals(Constants.CHANGE)) {
			person = personRespository.findByEmployeeCode(employeecode);
			person.setFullName(fullName);
			person.setGender(gender);
			person.setBirthDate(birthDate);
			person.setHireDate(hireDate);
			person.setTerminationDate(terminationDate);
			
			return person;
		}
		return person;

	}

	/**
	 * Read from Json file and manipulation and prepare Salary Component Model
	 * 
	 * @param payLoadJson
	 * @param action
	 * @param personId
	 * @return
	 */
	public SalaryComponent getSalaryComponents(JsonObject payLoadJson, String action, int personId) {
		SalaryComponent salComp = null;

		JsonArray payCompJsonArr = payLoadJson.getJsonArray(Constants.PAYCOMPONENTS);
		if (payCompJsonArr != null && !payCompJsonArr.isEmpty()) {
			for (int col = 0; col < payCompJsonArr.size(); col++) {
				JsonObject payCompJson = payCompJsonArr.getJsonObject(col);
				String amtStr = Util.getString(payCompJson.get(Constants.AMOUNT));
				double amount = amtStr.isEmpty() ? 0 : Double.parseDouble(amtStr);
				String currency = Util.getString(payCompJson.get(Constants.CURRENCY));
				Date startDate = Util.jsonValueToDate(payCompJson.get(Constants.START_DATE));
				Date endDate = Util.jsonValueToDate(payCompJson.get(Constants.END_DATE));

				if (action.equals(Constants.HIRE) && startDate != null && endDate != null && !currency.isEmpty()) {
					return new SalaryComponent(personId, amount, currency, startDate, endDate);
				} else if (action.equals(Constants.CHANGE) && startDate != null && endDate != null
						&& !currency.isEmpty()) {
					salComp = salaryComponentRespository.findByPersonId(personId);
					if (salComp != null) {
						salComp.setAmount(amount);
						salComp.setCurrency(currency);
						salComp.setStartDate(startDate);
						salComp.setEndDate(endDate);
						return salComp;
					} else {
						return new SalaryComponent(personId, amount, currency, startDate, endDate);
					}
				}
			}
		}
		return salComp;
	}
}
