package com.mercans.accounts.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="SalaryComponent")
@Table(name="salary_component")
public class SalaryComponent {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name="person_id")
	private int personId;
    
    @Column(name="amount")
    private double amount;
    
    @Column(name="currency")
    private String currency;
    
    @Column(name="start_date")
    private Date startDate;
    
    @Column(name="end_date")
    private Date endDate;

    public SalaryComponent() {  }

    public SalaryComponent(int  personId, double amount, String currency, Date startDate, Date endDate) {
        this.setPersonId(personId);
        this.setAmount(amount);
        this.setCurrency(currency);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
    }

    public SalaryComponent(int id, int  personId, double amount, String currency, Date startDate, Date end_Date) {
        this.setId(id);
    	this.setPersonId(personId);
        this.setAmount(amount);
        this.setCurrency(currency);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

   

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", personId='" + personId + 
                ", amount='" + amount  +
                 ", currency='" + currency + 
                ", startDate='" + startDate  +
                ", enddate='" + endDate  +
                '}';
    }
}
