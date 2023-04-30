package com.example.gtics_231__lab5__20200839.controllers;

import com.example.gtics_231__lab5__20200839.dto.Empleados;
import com.example.gtics_231__lab5__20200839.entity.Employee;
import com.example.gtics_231__lab5__20200839.entity.Job;
import com.example.gtics_231__lab5__20200839.repository.EmployeeRepository;
import com.example.gtics_231__lab5__20200839.repository.JobRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/empleado")
public class EmpleadoController {
    private final EmployeeRepository employeeRepository;
    private final JobRepository jobRepository;

    public EmpleadoController(EmployeeRepository employeeRepository,
                              JobRepository jobRepository) {
        this.employeeRepository = employeeRepository;
        this.jobRepository = jobRepository;
    }

    @GetMapping(value = {"/list", ""})
    public String listarEmpleados(Model model) {

        List<Empleados> lista = employeeRepository.empleadoList();
        model.addAttribute("listaEmpleado", lista);

        return "/empleado/vistaEmpleado";
    }
    @PostMapping("/buscar")
    public String buscarEmpleado(@RequestParam("textoBuscar") String textoBuscar,
                                               Model model) {

        List<Empleados> lista = employeeRepository.empleadoBuscar(textoBuscar);
        model.addAttribute("listaEmpleado", lista);
        model.addAttribute("textoBuscado", textoBuscar);
        return "/empleado/vistaEmpleado";
    }
    @GetMapping("/delete")
    public String borrarEmpleado(Model model,@RequestParam("id") int id) {

        Optional<Employee> optEmployee = employeeRepository.findById(id);

        if (optEmployee.isPresent()) {
            employeeRepository.deleteById(id);
        }
        return "redirect:/empleado/list";

    }
    @GetMapping("/new")
    public String nuevoEmpleadoForm(Model model) {
        List<Job> lista = jobRepository.findAll();
        List<Employee> lista2 = employeeRepository.findAll();
        model.addAttribute("listPuesto", lista);
        model.addAttribute("listManagerId", lista2);
        return "empleado/newEmpleado";
    }

    @PostMapping("/save")
    public String guardarNuevoEmpleado(@RequestParam("nombre") String nombre, @RequestParam("apellido") String apellido, @RequestParam("email") String email, @RequestParam("contrasena") String contrasena, @RequestParam("puesto") String idPuesto, @RequestParam("sueldo") Integer sueldo, @RequestParam("jefe") Integer idManager,@RequestParam("departamento") Integer idDepartamento,RedirectAttributes attr) {
        employeeRepository.nuevoEmpleado(nombre,apellido,email,contrasena,idPuesto,sueldo,idManager,idDepartamento);
        return "redirect:/empleado/list";
    }



}
