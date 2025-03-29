package com.institute.controller;

import com.institute.model.Student;
import com.institute.repository.StudentRepository;
import com.institute.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

 @Autowired
    private StudentService studentService;
    @PostMapping("/add")
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }
    @GetMapping("/get/all")
    public List<Student> studentList(){
        return  studentService.getAllStudents();
    }
}

