package com.garage.upskills.employeetransformer.controller;

import com.garage.upskills.employeetransformer.domain.CloudantEmployee;
import com.garage.upskills.employeetransformer.service.CloudantEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Profile("cloudant")
public class CloudantEmployeeController {

    @Autowired
    private CloudantEmployeeService service;

    @GetMapping("/listDatabases")
    public List<String> listDB() {
        return service.getAllDB();
    }

    @GetMapping("/getEmployees")
    public List<CloudantEmployee> getEmployees() {
        return service.getEmployees();
    }

    @GetMapping("/getEmployeeById/{id}")
    public CloudantEmployee getEmployeeById(@PathVariable String id) {
        return service.getEmployeeById(id);
    }

    @GetMapping("/getEmployeeByRole/{role}")
    public List<CloudantEmployee> getEmployeeByRole(@PathVariable String role) {
        return service.getEmployeeByRole(role);
    }

    @GetMapping("/getEmployeeByCity/{city}")
    public List<CloudantEmployee> getEmployeeByCity(@PathVariable String city) {
        return service.getEmployeeByCity(city);
    }

    @PostMapping("/addEmployee")
    public CloudantEmployee saveEmployee(@RequestBody CloudantEmployee employee) {
        return service.saveEmployee(employee);
    }

    @PostMapping("/addEmployees")
    public List<CloudantEmployee> saveEmployees(@RequestBody List<CloudantEmployee> employees) {
        return service.saveEmployees(employees);
    }

    @DeleteMapping("/deleteEmployee/{id}")
    public String deleteEmployeeById(@PathVariable String id) {
        return service.deleteEmployeeById(id);
    }

    @PostMapping("/updateEmployee")
    public String updateEmployee(@RequestBody CloudantEmployee employee) {
        return service.updateEmployee(employee);
    }
}
