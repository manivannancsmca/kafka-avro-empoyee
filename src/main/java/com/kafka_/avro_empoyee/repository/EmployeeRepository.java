package com.kafka_.avro_empoyee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kafka_.avro_empoyee.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {}
