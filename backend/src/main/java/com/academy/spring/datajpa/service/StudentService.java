package com.academy.spring.datajpa.service;

import com.academy.spring.datajpa.model.Student;
import com.academy.spring.datajpa.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;


    public List<Student> getAllByName(String nome) {
        if (nome == null) {
            return studentRepository.findAll();
        }
        try {
            return studentRepository.findByNome(nome);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    public List<Student> getAllBySurname(String cognome) {
        if (cognome == null) {
            return studentRepository.findAll();
        }
        try {
            return studentRepository.findByNome(cognome);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Optional<Student> getStudent(Long id) {
        return studentRepository.findById(id);
    }

    public Student createStudent(Student s) {
        Student.StudentBuilder sb = Student.builder();
        sb.cognome(s.getCognome());
        sb.nome(s.getNome());
        sb.dataDiNascita(s.getDataDiNascita());

        return sb.build();
    }

    public void deleteStudent(Long id) {
        try {
            studentRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAll() {
        try {
            studentRepository.deleteAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Student save(Student s) {
        try {
            return studentRepository.saveAndFlush(s);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
