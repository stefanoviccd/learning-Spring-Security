package com.example.demo.controller;

import com.example.demo.model.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class AppController {
    private final List<Student> STUDENTS= Arrays.asList(
            new Student(1, "James Bond"),
            new Student(2, "James Arthur"),
            new Student(3, "Alissa Smith")

    );
    @GetMapping("/students/{id}")
    private Student getStudent(@PathVariable("id") Integer studentId){
        return STUDENTS.stream().filter(student -> studentId.equals(student.getId()))
                .findFirst().
                orElse(new Student(1, "James Bond"));


    }

}
