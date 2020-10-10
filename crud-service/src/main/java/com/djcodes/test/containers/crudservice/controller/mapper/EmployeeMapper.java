package com.djcodes.test.containers.crudservice.controller.mapper;

import com.djcodes.test.containers.crudservice.dto.EmployeeDTO;
import com.djcodes.test.containers.crudservice.persistence.EmployeeEntity;

public class EmployeeMapper {

    public static EmployeeEntity makeEntity(EmployeeDTO employeeDTO) {
        return new EmployeeEntity(employeeDTO.getId(), employeeDTO.getFname(),
            employeeDTO.getLname(), employeeDTO.getSalary());
    }

    public static EmployeeDTO makeDTO(EmployeeEntity entity) {
        EmployeeDTO.EmployeeDTOBuilder employeeDTOBuilder = EmployeeDTO.newBuilder()
            .setId(entity.getId()).setFname(entity.getFname()).setLname(entity.getLname())
            .setSalary(entity.salary);
        return employeeDTOBuilder.createEmployeeDTO();
    }

}
