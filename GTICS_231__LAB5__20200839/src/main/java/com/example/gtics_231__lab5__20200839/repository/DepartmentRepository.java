package com.example.gtics_231__lab5__20200839.repository;

import com.example.gtics_231__lab5__20200839.entity.Department;
import com.example.gtics_231__lab5__20200839.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
