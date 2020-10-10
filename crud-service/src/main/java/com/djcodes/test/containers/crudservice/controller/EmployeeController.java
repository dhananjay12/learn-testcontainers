package com.djcodes.test.containers.crudservice.controller;

import com.djcodes.test.containers.crudservice.controller.mapper.EmployeeMapper;
import com.djcodes.test.containers.crudservice.dto.EmployeeDTO;
import com.djcodes.test.containers.crudservice.exceptions.EntityNotFoundException;
import com.djcodes.test.containers.crudservice.persistence.EmployeeEntity;
import com.djcodes.test.containers.crudservice.persistence.EmployeeRepository;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable long id) {
        Optional<EmployeeEntity> result = employeeRepository.findById(id);
        if (result.isPresent()) {
            return ResponseEntity.ok(EmployeeMapper.makeDTO(result.get()));
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public EmployeeDTO createCar(@Valid @RequestBody EmployeeDTO employeeDTO) {
        EmployeeEntity employeeEntity = EmployeeMapper.makeEntity(employeeDTO);
        EmployeeEntity result = employeeRepository.save(employeeEntity);
        return EmployeeMapper.makeDTO(result);
    }

    @PutMapping("/{id}")
    public EmployeeDTO updateCar(
        @PathVariable long id, @Valid @RequestBody EmployeeDTO employeeDTO) throws EntityNotFoundException {

        EmployeeEntity employeeEntity = findEmployeeById(id);
        employeeEntity.setFname(employeeDTO.getFname());
        employeeEntity.setLname(employeeDTO.getLname());
        employeeEntity.setSalary(employeeDTO.getSalary());
        EmployeeEntity result = employeeRepository.save(employeeEntity);
        return EmployeeMapper.makeDTO(result);
    }


    private EmployeeEntity findEmployeeById(Long id) throws EntityNotFoundException {
        return employeeRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + id));
    }

}
