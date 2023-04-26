package com.example.gtics_231__lab5__20200839.controllers;

import com.example.gtics_231__lab5__20200839.entity.Employee;
import com.example.gtics_231__lab5__20200839.entity.EmployeeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/empleado")
public class EmpleadoController {
    private final EmployeeRepository employeeRepository;

    public EmpleadoController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping(value = {"/list", ""})
    public String listarEmpleados(Model model) {

        List<Employee> lista = employeeRepository.;
        model.addAttribute("listaEmpleado", lista);

        return "vistaEmpleado";
    }
    @PostMapping("/buscar")
    public String buscarEmpleado(@RequestParam("textoBuscar") String textoBuscar,
                                               Model model) {

        List<Employee> lista = employeeRepository.buscarParcial(textoBuscar);
        model.addAttribute("shipperList", lista);
        model.addAttribute("textoBuscado", textoBuscar);

        return "vistaEmpleado";
    }


}
