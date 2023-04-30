package com.example.gtics_231__lab5__20200839.controllers;

import com.example.gtics_231__lab5__20200839.dto.Empleados;
import com.example.gtics_231__lab5__20200839.dto.Jefe;
import com.example.gtics_231__lab5__20200839.entity.Department;
import com.example.gtics_231__lab5__20200839.entity.Employee;
import com.example.gtics_231__lab5__20200839.entity.Job;
import com.example.gtics_231__lab5__20200839.repository.DepartmentRepository;
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
    private final DepartmentRepository departmentRepository;

    public EmpleadoController(EmployeeRepository employeeRepository,
                              JobRepository jobRepository,
                              DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.jobRepository = jobRepository;
        this.departmentRepository = departmentRepository;
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
    public String borrarEmpleado(Model model,@RequestParam("id") int id,RedirectAttributes attr) {

        Optional<Employee> optEmployee = employeeRepository.findById(id);

        if (optEmployee.isPresent()) {
            employeeRepository.deleteById(id);
        }
        attr.addFlashAttribute("msg", "Empleado borrado exitosamente");
        return "redirect:/empleado/list";

    }
    @GetMapping("/new")
    public String nuevoEmpleadoForm(Model model) {
        List<Job> listaPuesto = jobRepository.findAll();
        List<Jefe> listaJefe = employeeRepository.buscarJefe();
        List<Department> listaDepartamentos = departmentRepository.findAll();
        model.addAttribute("listPuesto", listaPuesto);
        model.addAttribute("listJefe",listaJefe);
        model.addAttribute("listDepartamentos", listaDepartamentos);
        return "empleado/newEmpleado";
    }

    @PostMapping("/saveNuevoEmpleado")
    public String guardarNuevoEmpleado(@RequestParam("nombre") String nombre, @RequestParam("apellido") String apellido, @RequestParam("email") String email, @RequestParam("contrasena") String contrasena, @RequestParam("puesto") String idPuesto, @RequestParam("sueldo") BigDecimal sueldo, @RequestParam("jefe") Integer idManager,@RequestParam("departamento") Integer idDepartamento,RedirectAttributes attr) {
        employeeRepository.nuevoEmpleado(nombre,apellido,email,contrasena,idPuesto,sueldo,idManager,idDepartamento);
        attr.addFlashAttribute("msg", "Empleado creado exitosamente");
        return "redirect:/empleado/list";
    }
    @PostMapping("/actualizarEmpleado")
    public String actualizarEmpleado(@RequestParam("id") Integer id,@RequestParam("nombre") String nombre, @RequestParam("apellido") String apellido, @RequestParam("email") String email, @RequestParam("contrasena") String contrasena, @RequestParam("puesto") String idPuesto, @RequestParam("sueldo") BigDecimal sueldo, @RequestParam("jefe") Integer idManager,@RequestParam("departamento") Integer idDepartamento,RedirectAttributes attr) {
        employeeRepository.actualizarEmpleado(nombre,apellido,email,contrasena,idPuesto,sueldo,idManager,idDepartamento,id);
        attr.addFlashAttribute("msg", "Empleado actualizado exitosamente");
        return "redirect:/empleado/list";
    }
    @GetMapping("/editar")
    public String editarEmpleado(Model model,@RequestParam("id") int id) {

        Optional<Employee> optEmpledo = employeeRepository.findById(id);
        List<Job> listaPuesto = jobRepository.findAll();
        List<Jefe> listaJefe = employeeRepository.buscarJefe();
        List<Department> listaDepartamentos = departmentRepository.findAll();
        if (optEmpledo.isPresent()) {
            Employee empleado = optEmpledo.get();
            model.addAttribute("empleado", empleado);
            model.addAttribute("listPuesto", listaPuesto);
            model.addAttribute("listJefe",listaJefe);
            model.addAttribute("listDep", listaDepartamentos);
            return "empleado/editEmpleado";
        } else {
            return "redirect:/empleado/list";
        }
    }



}
