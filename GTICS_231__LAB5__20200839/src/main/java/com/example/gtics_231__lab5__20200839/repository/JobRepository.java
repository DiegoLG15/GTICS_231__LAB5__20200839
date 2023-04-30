package com.example.gtics_231__lab5__20200839.repository;

import com.example.gtics_231__lab5__20200839.entity.Employee;
import com.example.gtics_231__lab5__20200839.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
}
