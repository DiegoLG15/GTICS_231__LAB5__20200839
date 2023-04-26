package com.example.gtics_231__lab5__20200839.repository;

import com.example.gtics_231__lab5__20200839.entity.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface employeeRepository extends JpaRepository<Employee, Integer> {
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "SELECT e.first_name, e.last_name, j.job_title, l.city\n" +
            "FROM hr.employees e\n" +
            "JOIN hr.departments d ON e.department_id = d.department_id\n" +
            "JOIN hr.jobs j ON e.job_id = j.job_id\n" +
            "join hr.locations l ON d.location_id=l.location_id")
    List<Employee> listaEmpleados();

}
