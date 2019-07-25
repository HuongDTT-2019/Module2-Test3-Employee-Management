package com.codegym.controller;

import com.codegym.model.Department;
import com.codegym.model.Employee;
import com.codegym.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @GetMapping("/departments")
    public ModelAndView listProvinces(){
        Iterable<Department> departments = departmentService.findAll();
        ModelAndView modelAndView = new ModelAndView("/department/list");
        modelAndView.addObject("departments",departments);
        return modelAndView;
    }
    @GetMapping("/create-department")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("/department/create");
        modelAndView.addObject("department", new Department());
        return modelAndView;
    }
    @PostMapping("/create-department")
    public ModelAndView saveProvince(@ModelAttribute("department") Department department){
        departmentService.save(department);

        ModelAndView modelAndView = new ModelAndView("/department/create");
        modelAndView.addObject("department", new Department());
        modelAndView.addObject("message", "New department created successfully");
        return modelAndView;
    }

    @GetMapping("/edit-department/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Department department = departmentService.findById(id);
        if(department != null) {
            ModelAndView modelAndView = new ModelAndView("/department/edit");
            modelAndView.addObject("department", department);
            return modelAndView;

        }else {
            ModelAndView modelAndView = new ModelAndView("/error404");
            return modelAndView;
        }
    }

    @PostMapping("/edit-department")
    public ModelAndView updateProvince(@ModelAttribute("department") Department department){
        departmentService.save(department);
        ModelAndView modelAndView = new ModelAndView("/department/edit");
        modelAndView.addObject("department", department);
        modelAndView.addObject("message", "Department updated successfully");
        return modelAndView;
    }

    @GetMapping("/delete-department/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Department department = departmentService.findById(id);
        if(department != null) {
            ModelAndView modelAndView = new ModelAndView("/department/delete");
            modelAndView.addObject("department", department);
            return modelAndView;

        }else {
            ModelAndView modelAndView = new ModelAndView("/error404");
            return modelAndView;
        }
    }

    @PostMapping("/delete-department")
    public String deleteDepartment(@ModelAttribute("department") Department department){
        departmentService.remove(department.getId());
        return "redirect:departments";
    }
//    @GetMapping("/view-department/{id}")
//    public ModelAndView viewDepartment(@PathVariable("id") Long id){
//        Department department = departmentService.findById(id);
//        if(department == null){
//            return new ModelAndView("/error404");
//        }
//        Iterable<Employee> employees = departmentService.findAllByDepartment(department);
//        ModelAndView modelAndView = new ModelAndView("/department/view");
//        modelAndView.addObject("department", department);
//        modelAndView.addObject("employee", employees);
//        return modelAndView;
//    }

}
