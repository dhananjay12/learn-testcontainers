package com.djcodes.test.containers.crudservice.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    public EmployeeEntity findByFname(String fname);

    public List<EmployeeEntity> findBySalaryGreaterThan(double amount);

    public List<EmployeeEntity> findBySalaryGreaterThanOrderByFnameAsc(double amount);


}
