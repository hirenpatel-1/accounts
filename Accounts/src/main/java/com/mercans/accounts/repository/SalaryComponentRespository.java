package com.mercans.accounts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mercans.accounts.model.SalaryComponent;

public interface SalaryComponentRespository extends JpaRepository<SalaryComponent, Integer>{

	//Find By PersonId
	SalaryComponent findByPersonId(int personId);
}
