package com.institute.service;

import com.institute.config.MultiTenantDataSourceConfig;
import com.institute.config.TenantContext;
import com.institute.model.Department;
import com.institute.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private MultiTenantDataSourceConfig multiTenantDataSourceConfig;

    public Department addDepartment(Department department){
        String tenant= TenantContext.getCurrentTenant();
        System.out.println("Current Tenant in DepartmentService"+tenant);
        if (tenant==null){
           throw new RuntimeException("No Tenant Selected");
        }
        DataSource dataSource=multiTenantDataSourceConfig.resolveDataSource(tenant);
//        JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
//        String insertQuery= "INSERT INTO Department(name,code,duration)"+"Values(?,?,?)";
//        jdbcTemplate.update(insertQuery,Department.getName(),Department.getCode(),Department.getDuration());
        return departmentRepository.save(department);
    }
    public List<Department> getAll(){
        String tenant= TenantContext.getCurrentTenant();
        System.out.println("Current Tenant in DepartmentService"+tenant);
        if (tenant==null){
            throw new RuntimeException("No Tenant Selected");
        }
        DataSource dataSource=multiTenantDataSourceConfig.resolveDataSource(tenant);
        return departmentRepository.findAll();
    }
    public Department updateDepartment(Long id, Department updatedDepartment) {
        String tenant = TenantContext.getCurrentTenant();
        System.out.println("Current Tenant in DepartmentService: " + tenant);
        if (tenant == null) {
            throw new RuntimeException("No Tenant Selected");
        }
        DataSource dataSource = multiTenantDataSourceConfig.resolveDataSource(tenant);

        Optional<Department> existingCourse = departmentRepository.findById(id);
        if (existingCourse.isPresent()) {
            Department Department = existingCourse.get();
            Department.setName(updatedDepartment.getName());
            Department.setCode(updatedDepartment.getCode());
            Department.setDuration(updatedDepartment.getDuration());
            return departmentRepository.save(Department);
        } else {
            throw new RuntimeException("Department not found with id: " + id);
        }
    }
    public void deleteDepartment(Long id) {
        String tenant = TenantContext.getCurrentTenant();
        System.out.println("Current Tenant in DepartmentService: " + tenant);
        if (tenant == null) {
            throw new RuntimeException("No Tenant Selected");
        }
        DataSource dataSource = multiTenantDataSourceConfig.resolveDataSource(tenant);

        if (departmentRepository.existsById(id)) {
            departmentRepository.deleteById(id);
        } else {
            throw new RuntimeException("Department not found with id: " + id);
        }
    }

}
