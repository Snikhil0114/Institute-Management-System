package com.institute.service;

import com.institute.config.MultiTenantDataSourceConfig;
import com.institute.config.TenantContext;
import com.institute.model.Course;
import com.institute.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private MultiTenantDataSourceConfig multiTenantDataSourceConfig;

    public Course addCourses(Course course){
        String tenant= TenantContext.getCurrentTenant();
        System.out.println("Current Tenant in CourseService: "+tenant);
        if (tenant==null){
           throw new RuntimeException("No Tenant Selected");
        }
        DataSource dataSource=multiTenantDataSourceConfig.resolveDataSource(tenant);
//        JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
//        String insertQuery= "INSERT INTO Department(name,code,duration)"+"Values(?,?,?)";
//        jdbcTemplate.update(insertQuery,Department.getName(),Department.getCode(),Department.getDuration());
        Course saveCourse=courseRepository.save(course);
        return saveCourse;
    }
    public List<Course> getAll(){
        String tenant= TenantContext.getCurrentTenant();
        System.out.println("Current Tenant in CourseService: "+tenant);
        if (tenant==null){
            throw new RuntimeException("No Tenant Selected");
        }
        DataSource dataSource=multiTenantDataSourceConfig.resolveDataSource(tenant);
        return courseRepository.findAll();
    }
    public Course updateCourse(Long id, Course updatedDepartment) {
        String tenant = TenantContext.getCurrentTenant();
        System.out.println("Current Tenant in CourseService: " + tenant);
        if (tenant == null) {
            throw new RuntimeException("No Tenant Selected");
        }
        DataSource dataSource = multiTenantDataSourceConfig.resolveDataSource(tenant);

        Optional<Course> existingCourse = courseRepository.findById(id);
        if (existingCourse.isPresent()) {
            Course course = existingCourse.get();
            course.setName(updatedDepartment.getName());
            course.setCode(updatedDepartment.getCode());
            course.setDuration(updatedDepartment.getDuration());
            return courseRepository.save(course);
        } else {
            throw new RuntimeException("Course not found with id: " + id);
        }
    }
    public void deleteCourse(Long id) {
        String tenant = TenantContext.getCurrentTenant();
        System.out.println("Current Tenant in CourseService: " + tenant);
        if (tenant == null) {
            throw new RuntimeException("No Tenant Selected");
        }
        DataSource dataSource = multiTenantDataSourceConfig.resolveDataSource(tenant);

        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
        } else {
            throw new RuntimeException("Course not found with Id: " + id);
        }
    }

}
