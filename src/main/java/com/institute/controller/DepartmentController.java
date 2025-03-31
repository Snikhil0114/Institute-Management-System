package com.institute.controller;

import com.institute.model.Department;
import com.institute.service.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class DepartmentController {
    private DepartmentService departmentService;

   @PostMapping("/adddepartment")
   public Department addDepartment(@RequestBody Department department){
        return departmentService.addDepartment(department);
    }
    @GetMapping("/get/all")
    public List<Department> getAllDepartment(){
       return departmentService.getAll();
    }
    @PostMapping("/updateDepartment")
    public Department updateDepartment(@RequestBody Long id,Department department){
       return departmentService.updateDepartment(id,department);
    }
    @PostMapping("/deleteDepartment")
    public void deleteDepartment(@RequestBody Long id){
         departmentService.deleteDepartment(id);
    }

}
