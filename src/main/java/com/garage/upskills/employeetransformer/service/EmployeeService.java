package com.garage.upskills.employeetransformer.service;

import com.garage.upskills.employeetransformer.exceptions.BadEmployeeDataException;
import com.garage.upskills.employeetransformer.exceptions.EmployeeNotFoundException;

import java.util.List;

public interface EmployeeService<T, ID> {

    public List<T> getEmployees();

    public T getEmployeeById(ID id);

    public List<T> getEmployeeByRole(String role);

    public List<T> getEmployeeByCity(String city);

    public T saveEmployee(T employee);

    public List<T> saveEmployees(List<T> employees);

    public String deleteEmployeeById(ID id);

    public String updateEmployee(T employee) throws EmployeeNotFoundException;

    public T validateEmployee(T employee) throws BadEmployeeDataException;
}
