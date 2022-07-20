package net.javaguides.springboot.controller;

import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeRepository employeeRepository;

//    get employees
    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

//    get employee by id
    @GetMapping("/employees/{emailId}")
    public ResponseEntity<Employee> getEmployeeByEmailId(@PathVariable(value = "emailId") String employeeEmail) {
        Employee employee = employeeRepository.findByEmailId(employeeEmail);
        return ResponseEntity.ok().body(employee);
    }

//    save employee
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        logger.info("Create Employees");
        return employeeRepository.save(employee);
    }

//    update employee
    @PutMapping("/employees/{emailId}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "emailId") String employeeEmail,
                                                   @Validated @RequestBody Employee employeeDetails) {
        Employee employee = employeeRepository.findByEmailId(employeeEmail);
        employee.setEmailId(employeeDetails.getEmailId());
        employee.setLastName(employeeDetails.getLastName());
        employee.setFirstName(employeeDetails.getFirstName());
        final Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

//    delete employee
    @DeleteMapping("/employees/{emailId}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "emailId") String employeeEmail) {
        Employee employee = employeeRepository.findByEmailId(employeeEmail);
        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}