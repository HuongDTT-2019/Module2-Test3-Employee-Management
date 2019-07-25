package com.codegym.controller;

import com.codegym.model.Department;
import com.codegym.model.Employee;
import com.codegym.model.EmployeeForm;
import com.codegym.service.DepartmentService;
import com.codegym.service.EmployeeService;
import net.bytebuddy.matcher.ElementMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
@PropertySource("classpath:global_config_app.properties")
public class EmployeeController {


    @Autowired
    private Environment env;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DepartmentService departmentService;

    @ModelAttribute("department")
    public Iterable<Department> departments() {
        return departmentService.findAll();
    }

    @GetMapping("/create-employee")
    public ModelAndView showCreateFrom() {
        ModelAndView modelAndView = new ModelAndView("/employee/create");
        modelAndView.addObject("employee", new EmployeeForm());
        return modelAndView;
    }

    @PostMapping("/save-employee")
    public ModelAndView saveEmployee(@ModelAttribute("employee") EmployeeForm employeeForm, BindingResult result, HttpServletRequest request) {
        // thong bao neu xay ra loi
        if (result.hasErrors()) {
            System.out.println("Result Error Occured" + result.getAllErrors());
        }

        // lay ten file
        MultipartFile multipartFile = employeeForm.getImages();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = env.getProperty("file_upload").toString();

        // luu file len server
        try {
            //multipartFile.transferTo(imageFile);
            FileCopyUtils.copy(employeeForm.getImages().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }


        // tao doi tuong de luu vao db

        Employee employeeObject = new Employee(employeeForm.getName(), employeeForm.getBirthDate(), employeeForm.getAddress(), fileName, employeeForm.getSalary(), employeeForm.getDepartment());
        employeeService.save(employeeObject);
        ModelAndView modelAndView = new ModelAndView("/employee/create");
        modelAndView.addObject("employee", new EmployeeForm());
        modelAndView.addObject("message", "New product created successfully");
        return modelAndView;
    }

    @GetMapping("/employee")
    public ModelAndView listEmployee(@PageableDefault(4) Pageable pageable) {
        Page<Employee> employees = employeeService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/employee/list");
        modelAndView.addObject("employees", employees);
        return modelAndView;
    }

    @GetMapping("/edit-employee/{id}")
    ModelAndView showEditForm(@PathVariable Long id) {
        Employee employee = employeeService.findById(id);
        if (employee != null) {
            EmployeeForm employeeForm = new EmployeeForm(employee.getId(), employee.getName(), employee.getBirthDate()
                    , employee.getAddress(), null, employee.getSalary(), employee.getDepartment());
            ModelAndView modelAndView = new ModelAndView("/employee/edit");
            modelAndView.addObject("employeeform", employeeForm);
            modelAndView.addObject("employee", employee);

            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error404");
            return modelAndView;
        }
    }

    @PostMapping("/edit-employee")
    public ModelAndView editEmployee(@ModelAttribute("employeeform") EmployeeForm employeeForm, BindingResult result) {
        // thong bao neu xay ra loi
        if (result.hasErrors()) {
            System.out.println("Result Error Occured" + result.getAllErrors());
        }

        // lay ten file
        MultipartFile multipartFile = employeeForm.getImages();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = env.getProperty("file_upload").toString();

        // luu file len server
        try {
            //multipartFile.transferTo(imageFile);
            FileCopyUtils.copy(employeeForm.getImages().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // tao doi tuong de luu vao db
        Employee employee = employeeService.findById(employeeForm.getId());
        if (fileName.equals("")) {
            Employee employeeObject = new Employee(employeeForm.getId(), employeeForm.getName(), employeeForm.getBirthDate(), employeeForm.getAddress(), employee.getAvatar(), employeeForm.getSalary(), employeeForm.getDepartment());
            employeeService.save(employeeObject);
        } else {
            Employee employeeObject = new Employee(employeeForm.getId(), employeeForm.getName(), employeeForm.getBirthDate(), employeeForm.getAddress(), fileName, employeeForm.getSalary(), employeeForm.getDepartment());
            employeeService.save(employeeObject);
        }
        ModelAndView modelAndView = new ModelAndView("/employee/edit");
        modelAndView.addObject("employee", employee);
        modelAndView.addObject("message", "Employee updated successfully");
        return modelAndView;
    }

    @GetMapping("/delete-employee/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Employee employee = employeeService.findById(id);
        if (employee != null) {
            ModelAndView modelAndView = new ModelAndView("/employee/delete");
            modelAndView.addObject("employee", employee);
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/error404");
            return modelAndView;
        }
    }

    @PostMapping("/delete-employee")
    public String deleteCustomer(@ModelAttribute("employee") Employee employee) {
        employeeService.remove(employee.getId());
        return "redirect:employee";
    }
    @RequestMapping("/search-employee")
    public ModelAndView searchForm(@RequestParam("department") Long departmentSearch,Pageable pageable){

        Department department = departmentService.findById(departmentSearch);
        Page<Employee> employees = employeeService.findAllByDepartment(department,pageable);
        ModelAndView modelAndView = new ModelAndView("/employee/result");
        modelAndView.addObject("departments",department);
        modelAndView.addObject("employee",employees);
        return modelAndView;

    }

}
