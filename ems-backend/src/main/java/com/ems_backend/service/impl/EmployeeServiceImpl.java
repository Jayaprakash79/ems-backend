package com.ems_backend.service.impl;

import com.ems_backend.Entity.Employee;
import com.ems_backend.dto.EmployeeDto;
import com.ems_backend.exception.ResourceNotFoundException;
import com.ems_backend.mapper.EmployeeMapper;
import com.ems_backend.repository.EmployeeRepository;
import com.ems_backend.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {
     //dependency injection
    //field injection
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("The employee does not exists with the given id" + employeeId));
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map((employee) -> EmployeeMapper.mapToEmployeeDto(employee))
                .collect(Collectors.toUnmodifiableList());

    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {
        //if you want to update the details of employee based on employee id, first we need to check
        //if the employee exists with the given employee id or not
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(()-> new ResourceNotFoundException("The employee does not exists with given id" + employeeId));
        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());
        Employee savedEmployeeObj = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployeeObj);
    }

    @Override
    public EmployeeDto deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("The employee does not exists with given id" + employeeId));
        employeeRepository.deleteById(employeeId);
        return EmployeeMapper.mapToEmployeeDto(employee);
        //here we for showing what we have deleted, return type should be EmployeeDto.
        //we can keep void as return type and then we can return string in controller class
    }

    /*
    @Override
    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }
     */




}
