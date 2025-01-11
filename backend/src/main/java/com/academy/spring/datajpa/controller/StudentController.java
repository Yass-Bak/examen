package com.academy.spring.datajpa.controller;

import com.academy.spring.datajpa.model.Student;
import com.academy.spring.datajpa.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    StudentService studentService;

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents(@RequestParam(required = false) String cognome){
        try{
            List<Student> students=studentService.getAllBySurname(cognome);
            if(students.isEmpty()){
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(students,HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentsById(@PathVariable("id") long id){
        Optional<Student> student=studentService.getStudent(id);
        if(student.isPresent()){
            return  new ResponseEntity<>(student.get(),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student){
        try{
            Student s=studentService.createStudent(student);
            return new ResponseEntity<>(s,HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus>  delete(@PathVariable("id")Long id){
        try{
            studentService.deleteStudent(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus>  deleteAll(){
        try{
            studentService.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") long id,@RequestBody Student newStudent){
        Optional<Student> student=studentService.getStudent(id);
        if(student.isPresent()){
            Student s=student.get();
            s.setCognome(newStudent.getCognome());
            s.setNome(newStudent.getNome());
            s.setDataDiNascita(newStudent.getDataDiNascita());
            return new ResponseEntity<>(s,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
