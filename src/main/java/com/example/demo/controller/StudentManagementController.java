package com.example.demo.controller;

import com.example.demo.model.Student;
import org.checkerframework.framework.qual.PolyAll;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/management/api/v1/students")
public class StudentManagementController {
    private final List<Student> STUDENTS= Arrays.asList(
            new Student(1, "James Bond"),
            new Student(2, "James Arthur"),
            new Student(3, "Alissa Smith")

    );
@GetMapping
@PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    private List<Student> getAllStudents(){
        return STUDENTS;

    }
    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    private void registerNewStudent(@RequestBody Student student){
        System.out.println(student);
    }
    @DeleteMapping(path = "{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    private void deleteStudent(@PathVariable("studentId") Integer studentId){
        System.out.println(studentId);
    }
    @PutMapping(path = "{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    private void updateStudent(@PathVariable("studentId")  Integer studentId,@RequestBody Student student){
        System.out.println(String.format("%s %s",studentId, student));
    }

}
