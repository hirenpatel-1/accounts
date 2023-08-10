package com.mercans.accounts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mercans.accounts.model.Person;

public interface PersonRespository extends JpaRepository<Person, Integer>{
    
	//Find by EmployeeCode
    Person findByEmployeeCode(String empCode);

}
