package com.garage.upskills.employeetransformer.service;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Response;
import com.cloudant.client.api.query.Expression;
import com.cloudant.client.api.query.QueryBuilder;
import com.cloudant.client.org.lightcouch.NoDocumentException;
import com.garage.upskills.employeetransformer.domain.CloudantEmployee;
import com.garage.upskills.employeetransformer.exceptions.BadEmployeeDataException;
import com.garage.upskills.employeetransformer.exceptions.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Profile("cloudant")
public class CloudantEmployeeService implements EmployeeService<CloudantEmployee, String> {

    @Autowired
    private CloudantClient client;

    @Autowired
    private Database db;

    public List<String> getAllDB() {
        return client.getAllDbs();
    }

    @Override
    public List<CloudantEmployee> getEmployees() {
        try {
            List<CloudantEmployee> employeeList = db.getAllDocsRequestBuilder()
                    .includeDocs(true)
                    .build().getResponse()
                    .getDocsAs(CloudantEmployee.class);
            return employeeList;
        } catch (Exception e) {
            throw new EmployeeNotFoundException("No Employee Data");
        }

    }

    @Override
    public CloudantEmployee getEmployeeById(String id) {
        try {
            return db.find(CloudantEmployee.class, id);
        } catch (NoDocumentException e) {
            throw new EmployeeNotFoundException("Employee ID: " + id + " doesn't exist.");
        }
    }

    @Override
    public List<CloudantEmployee> getEmployeeByRole(String role) {
        List<CloudantEmployee> employeeList = queryByField("role", role);

        if (employeeList.size() == 0)
            throw new EmployeeNotFoundException("No Employee of role - " + role);

        return employeeList;
    }

    @Override
    public List<CloudantEmployee> getEmployeeByCity(String city) {
        List<CloudantEmployee> employeeList = queryByField("city", city);

        if (employeeList.size() == 0)
            throw new EmployeeNotFoundException("No Employee lives in city - " + city);

        return employeeList;
    }

    @Override
    public CloudantEmployee saveEmployee(CloudantEmployee employee) {
        Response response = db.save(validateEmployee(employee));
        return db.find(CloudantEmployee.class, response.getId());
    }

    @Override
    public List<CloudantEmployee> saveEmployees(List<CloudantEmployee> employees) {
//        List<Response> responseList = db.bulk(employees);
//        return responseList.stream()
//                .map(response -> db.find(CloudantEmployee.class, response.getId()))
//                .collect(Collectors.toList())
//                ;
        // call addEmployee to do data checking
        return employees.stream()
                .map(employee -> this.saveEmployee(employee))
                .collect(Collectors.toList());
    }

    @Override
    public String deleteEmployeeById(String id) {
        CloudantEmployee employee = this.getEmployeeById(id);  // will throw exception if employee id doesn't exist

        db.remove(employee);
        return employee + " has been deleted.";
    }

    @Override
    public String updateEmployee(CloudantEmployee employee) {
        this.getEmployeeById(employee.getId());
        db.update(validateEmployee(employee));
        return "Employee ID: " + employee.getId() + " has been updated:\n" + employee;
    }

    @Override
    public CloudantEmployee validateEmployee(CloudantEmployee employee) throws BadEmployeeDataException {
        String empId = employee.getId();
        String firstName = employee.getFirstName();
        String lastName = employee.getLastName();

        if (firstName == null || lastName == null || firstName.length() == 0 || lastName.length() == 0)
            throw new BadEmployeeDataException("Employee First Name and Last Name can't be null.\n" + employee);

        return employee;
    }

    private List<CloudantEmployee> queryByField(String field, String value) {
        String query = new QueryBuilder(Expression.eq(field, value)).build();
        return db.query(query, CloudantEmployee.class).getDocs();
    }

}
