package com.example.gtics_231__lab5__20200839.repository;

import com.example.gtics_231__lab5__20200839.dto.Empleados;
import com.example.gtics_231__lab5__20200839.dto.Jefe;
import com.example.gtics_231__lab5__20200839.dto.Salario;
import com.example.gtics_231__lab5__20200839.entity.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query(nativeQuery = true, value = "SELECT e.employee_id AS id, e.first_name AS nombre, e.last_name AS apellido, j.job_title AS puesto, d.department_name AS departamento, l.city AS ciudad\n" +
            "FROM hr.employees e\n" +
            "INNER JOIN hr.departments d ON e.department_id = d.department_id\n" +
            "INNER JOIN hr.jobs j ON e.job_id = j.job_id\n" +
            "INNER JOIN hr.locations l ON d.location_id = l.location_id\n" +
            "WHERE e.first_name LIKE %?1%" +
            "    OR e.last_name LIKE %?1%" +
            "    OR j.job_title LIKE %?1%" +
            "    OR l.city LIKE %?1%")
    List<Empleados> empleadoBuscar(String textoBuscar);
    @Query(nativeQuery = true, value = "SELECT e.employee_id as id,e.first_name as nombre, e.last_name as apellido, j.job_title as puesto, d.department_name as departamento,l.city as ciudad\n" +
            "FROM hr.employees e\n" +
            "INNER JOIN hr.departments d ON e.department_id = d.department_id\n" +
            "INNER JOIN hr.jobs j ON e.job_id = j.job_id\n" +
            "INNER JOIN hr.locations l ON d.location_id=l.location_id;")
    List<Empleados> empleadoList();
    @Query(nativeQuery = true, value = "SELECT \n" +
            "    j.job_title AS puesto, \n" +
            "    ROUND(MAX(e.salary), 2) AS salarioMax, \n" +
            "    ROUND(MIN(e.salary), 2) AS salarioMin, \n" +
            "    ROUND(SUM(e.salary), 2) AS salarioTotal, \n" +
            "    ROUND(AVG(e.salary), 2) AS salarioProm\n" +
            "FROM \n" +
            "    hr.employees e \n" +
            "    INNER JOIN hr.jobs j ON e.job_id = j.job_id \n" +
            "GROUP BY \n" +
            "    j.job_title;")
    List<Salario> empleadoSalario();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO employees (first_name, last_name, email, password, job_id, salary, manager_id, department_id) \n" +
            "VALUES (?1, ?2, ?3, SHA2(?4, 256), ?5, ?6, ?7, ?8)")
    void nuevoEmpleado(String nombre, String apellido, String correo, String contrasena, String jobId, BigDecimal salary, Integer managerId, Integer departmentId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE employees\n" +
            "SET first_name = ?1, last_name = ?2, email = ?3, password = SHA2(?4, 256) , job_id = ?5 , salary=?6, manager_id = ?7,department_id = ?8\n" +
            "WHERE employee_id = ?9", nativeQuery = true)
    void actualizarEmpleado(String nombre, String apellido,String correo,String contrasena,String jobId, BigDecimal salary,Integer managerId,Integer departmentId, Integer employeeid);

    @Query(nativeQuery = true, value = "SELECT \n" +
            "  employee_id as id, \n" +
            "  CONCAT(first_name, ' ', last_name) AS nombrecompleto\n" +
            "FROM \n" +
            "  hr.employees \n" +
            "WHERE \n" +
            "  employee_id IN (SELECT manager_id FROM hr.employees)")
    List<Jefe> buscarJefe();
}