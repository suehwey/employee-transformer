package com.garage.upskills.employeetransformer.service;

import com.garage.upskills.employeetransformer.domain.Employee;
import com.garage.upskills.employeetransformer.exceptions.BadEmployeeDataException;
import com.garage.upskills.employeetransformer.exceptions.EmployeeNotFoundException;
import com.garage.upskills.employeetransformer.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MySqlEmployeeService implements EmployeeService<Employee, Integer> {

    private EmployeeRepository employeeRepository;

    public List<Employee> getEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();
        if (employeeList.size() == 0)
            throw new EmployeeNotFoundException("No Employee Data");

        return employeeList;
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @Override
    public List<Employee> getEmployeeByRole(String role) {
        List<Employee> employeeList = employeeRepository.findByRole(role);
        if (employeeList.size() == 0)
            throw new EmployeeNotFoundException("No Employee of role - " + role);

        return employeeList;
    }

    @Override
    public List<Employee> getEmployeeByCity(String city) {
        List<Employee> employeeList = employeeRepository.findByCity(city);
        if (employeeList.size() == 0)
            throw new EmployeeNotFoundException("No Employee lives in city - " + city);

        return employeeList;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(validateEmployee(employee));
    }

    @Override
    public List<Employee> saveEmployees(List<Employee> employees) {
//        return employeeRepository.saveAll(employees);
        return employees.stream()
                .map(employee -> this.saveEmployee(employee))
                .collect(Collectors.toList());
    }

    @Override
    public String deleteEmployeeById(Integer id) {
        Employee employee = this.getEmployeeById(id);  // will throw exception if employee id doesn't exist

        employeeRepository.deleteById(id);
        return employee + " has been deleted.";
    }

    @Override
    public String updateEmployee(Employee employee) throws EmployeeNotFoundException{
        this.getEmployeeById(employee.getEmpId());
        employeeRepository.save(validateEmployee(employee));
        return "Employee ID: " + employee.getEmpId() + " has been updated:\n" + employee;
    }

    @Override
    public Employee validateEmployee(Employee employee) throws BadEmployeeDataException {
        int empId = employee.getEmpId();
        String firstName = employee.getFirstName();
        String lastName = employee.getLastName();

        if (firstName == null || lastName == null || firstName.length() == 0 || lastName.length() == 0)
            throw new BadEmployeeDataException("Employee First Name and Last Name can't be null.\n" + employee);

        return employee;
    }
}
