package com.garage.upskills.employeetransformer.repository;

import com.garage.upskills.employeetransformer.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findByRole(String role);
    List<Employee> findByCity(String city);
}
