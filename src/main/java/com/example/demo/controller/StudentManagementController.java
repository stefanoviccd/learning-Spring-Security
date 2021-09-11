package com.example.demo.controller;

import com.example.demo.model.Student;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/management/api/v1/students")
public  class StudentManagementController {
    public static final  List<Student> STUDENTS=Arrays.asList(
           new Student(1, "James Bond"),
            new Student(2, "Maria Gomes")
    );


@GetMapping
@PreAuthorize("hasAnyAuthority('ROLE_STUDENT', 'ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    public List<Student> getAllStudents(){
    System.out.println("getAllStudents");
    System.out.println(STUDENTS);
        return this.STUDENTS;

    }
    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public void registerNewStudent(@RequestBody Student student){
        System.out.println("registerNewStudent");
        System.out.println(student);
    }
    @DeleteMapping(path = "{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable("studentId") Integer studentId){
        System.out.println("deleteStudent");
        System.out.println("Student with id "+studentId+" is deleted.");
    }
    @PutMapping(path = "{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void updateStudent(@PathVariable("studentId")  Integer studentId,@RequestBody Student student){
        System.out.println("updateStudent");
        System.out.println(String.format("%s %s",studentId, student));
    }

}
