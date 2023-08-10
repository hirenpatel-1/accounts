package com.mercans.accounts.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="Person")
@Table(name="person")
public class Person {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name="full_name")
    private String fullName;
    
    @Column(name="gender")
    private String gender;
    
    @Column(name="birthdate")
    private Date birthDate;
    
    @Column(name="employee_code")
    private String employeeCode;
    
    @Column(name="hire_date")
    private Date hireDate;
    
    @Column(name="termination_date")
    private Date terminationDate;
   
    public Person() {  }

    public Person(String  fullName, String gender, Date birthDate, String employeeCode, Date hireDate, Date terminationDate) {
       this.setFullName(fullName);
       this.setGender(gender);
       this.setBirthDate(birthDate);
       this.setEmployeeCode(employeeCode);
       this.setHireDate(hireDate);
       this.setTerminationDate(terminationDate);
    }
    
    public Person(int id, String  fullName, String gender, Date birthDate, String employeeCode, Date hireDate, Date terminationDate) {
        this.setId(id);
    	this.setFullName(fullName);
        this.setGender(gender);
        this.setBirthDate(birthDate);
        this.setEmployeeCode(employeeCode);
        this.setHireDate(hireDate);
        this.setTerminationDate(terminationDate);
     }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public Date getTerminationDate() {
		return terminationDate;
	}

	public void setTerminationDate(Date terminationDate) {
		this.terminationDate = terminationDate;
	}
	
    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", fullName='" + fullName + 
                ", birthDate='" + birthDate  +
                 ", gender='" + gender + 
                ", employeeCode='" + employeeCode  +
                ", hireDate='" + hireDate  +
                 ", terminationDate='" + terminationDate  +
                '}';
    }
}
