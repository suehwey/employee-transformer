package com.garage.upskills.employeetransformer.controller;

import com.garage.upskills.employeetransformer.domain.Employee;
import com.garage.upskills.employeetransformer.service.EmployeeService;
import com.garage.upskills.employeetransformer.service.MySqlEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Profile("default")
public class EmployeeController {

    private MySqlEmployeeService employeeService;

    @Autowired
    public EmployeeController (MySqlEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/getEmployees")
    public List<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    @GetMapping("/getEmployeeById/{id}")
    public Employee getEmployeeById(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/getEmployeeByRole/{role}")
    public List<Employee> getEmployeeByRole(@PathVariable String role) {
        return employeeService.getEmployeeByRole(role);
    }

    @GetMapping("/getEmployeeByCity/{city}")
    public List<Employee> getEmployeeByCity(@PathVariable String city) {
        return employeeService.getEmployeeByCity(city);
    }

    @PostMapping("/addEmployee")
    public Employee saveEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @PostMapping("/addEmployees")
    public List<Employee> saveEmployees(@RequestBody List<Employee> employees) {
        return employeeService.saveEmployees(employees);
    }

    @DeleteMapping("/deleteEmployee/{id}")
    public String deleteEmployeeById(@PathVariable int id) {
        return employeeService.deleteEmployeeById(id);
    }

    @PostMapping("/updateEmployee")
    public String updateEmployee(@RequestBody Employee employee) {
        return employeeService.updateEmployee(employee);
    }
}
