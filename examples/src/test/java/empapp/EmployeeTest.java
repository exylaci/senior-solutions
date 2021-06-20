package empapp;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {
    @Test
    void testGetAge() {
        // Given
        Employee employee = new Employee("John Doe", 1970);

        // When
        int age = employee.getAge(2019);

        // Then
        assertEquals(49, age);
    }
}