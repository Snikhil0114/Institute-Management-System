package com.institute.controller;

import com.institute.model.Course;
import com.institute.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

   @PostMapping("/addCourse")
   public Course addCourses(@RequestBody Course course){
        return courseService.addCourses(course);
    }
    @GetMapping("/get/all")
    public List<Course> getAllCourse(){
       return courseService.getAll();
    }
    @PostMapping("/updateCourse")
    public Course updateCourse(@RequestBody Long id, Course course){
       return courseService.updateCourse(id,course);
    }
    @PostMapping("/deleteCourse")
    public void deleteCourse(@RequestBody Long id){
         courseService.deleteCourse(id);
    }

}
