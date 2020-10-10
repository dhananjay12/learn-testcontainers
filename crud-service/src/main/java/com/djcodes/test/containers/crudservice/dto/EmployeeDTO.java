package com.djcodes.test.containers.crudservice.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDTO {

    @NotNull(message = "salary can not be null!")
    private Double salary;

    @JsonIgnore
    private Long id;
    @NotNull(message = "fname can not be null!")
    private String fname;
    @NotNull(message = "lname can not be null!")
    private String lname;

    private EmployeeDTO() {
    }

    private EmployeeDTO(Long id, String fname, String lname, Double salary) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.salary = salary;
    }

    public static EmployeeDTOBuilder newBuilder() {
        return new EmployeeDTOBuilder();
    }

    public Double getSalary() {
        return salary;
    }

    @JsonProperty
    public Long getId() {
        return id;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public static class EmployeeDTOBuilder {

        public Double salary;
        private Long id;
        private String fname;
        private String lname;

        public EmployeeDTOBuilder setSalary(Double salary) {
            this.salary = salary;
            return this;
        }

        public EmployeeDTOBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public EmployeeDTOBuilder setFname(String fname) {
            this.fname = fname;
            return this;
        }

        public EmployeeDTOBuilder setLname(String lname) {
            this.lname = lname;
            return this;
        }

        public EmployeeDTO createEmployeeDTO() {
            return new EmployeeDTO(id, fname, lname, salary);
        }
    }


}
