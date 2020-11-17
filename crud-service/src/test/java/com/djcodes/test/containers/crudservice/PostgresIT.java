package com.djcodes.test.containers.crudservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.djcodes.test.containers.crudservice.dto.EmployeeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=create-drop", "spring.profiles.active=postgres"})
public class PostgresIT {

     @Container
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:13.0")
        .withUsername("test").withPassword("test");

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    ObjectMapper objectMapper;


    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        //https://www.testcontainers.org/modules/databases/jdbc/
        //Either use jdbc url with `tc` prefix or leave it
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }

    @AfterAll
    public static void closeAll() {
        postgreSQLContainer.stop();
    }

    @Test
    public void createEmployeeTest_201() throws ParseException {
        //Arrange
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse("10-01-1990"));
        EmployeeDTO employeeRequest = EmployeeDTO.builder().fname("John").lname("Doe").email("john@test.com").dob(cal).build();

        //Act
        ResponseEntity<EmployeeDTO> response = testRestTemplate
            .postForEntity("/employee", employeeRequest, EmployeeDTO.class);

        //Assert
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    public void createEmployeeTest_400() throws ParseException {
        //Arrange
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse("10-01-1990"));
        EmployeeDTO employeeRequest = EmployeeDTO.builder().id(10).fname("John").lname("Doe").email("john@test.com").dob(cal).build();

        //Act
        ResponseEntity<String> response = testRestTemplate
            .postForEntity("/employee", employeeRequest, String.class);

        //Assert
        assertEquals(400, response.getStatusCodeValue());
    }

}
