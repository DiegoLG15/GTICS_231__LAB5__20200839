package com.example.gtics_231__lab5__20200839.controllers;

import com.example.gtics_231__lab5__20200839.dto.Empleados;
import com.example.gtics_231__lab5__20200839.dto.Salario;
import com.example.gtics_231__lab5__20200839.repository.EmployeeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/reportes")
public class ReporteController {
    private final EmployeeRepository employeeRepository;

    public ReporteController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping(value = { ""})
    public String dashboardReporte(Model model) {
        return "/reportes/reporteDashboard";
    }
    @GetMapping(value = { "/sueldo"})
    public String sueldoReporte(Model model) {

        List<Salario> lista = employeeRepository.empleadoSalario();
        model.addAttribute("listaSalario", lista);

        return "/reportes/reporteEmpleado";
    }
    @GetMapping(value = { "/aumento"})
    public String dashboardReporteAumento(Model model) {

        return "/reportes/reporteAumento";
    }
}
