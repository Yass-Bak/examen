package com.academy.spring.datajpa.controller;

import com.academy.spring.datajpa.model.Autore;
import com.academy.spring.datajpa.model.Tutorial;
import com.academy.spring.datajpa.service.AutoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/authors")
public class AutoreController {
    @Autowired
    AutoreService autoreService;

    @GetMapping
    public ResponseEntity<List<Autore>> getAllAuthors(@RequestParam(required = false) String cognome) {
        try {
            List<Autore> autori = autoreService.getAllBySurname(cognome);
            if (autori.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(autori, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Autore> updateAutore(@RequestBody Autore newAutore, @PathVariable("id") Long id) {
        Optional<Autore> autoreData = autoreService.getAutore(id);
        if (autoreData.isPresent()) {
            Autore a = autoreData.get();
            a.setNome(newAutore.getNome());
            a.setCognome(newAutore.getCognome());
            a.setDataDiNascita(newAutore.getDataDiNascita());
            return new ResponseEntity<>(autoreService.save(a), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping()
    public ResponseEntity<Autore> createAutore(@RequestBody Autore newAutore) {
        try {
            Autore autore = autoreService.createAutore(newAutore);
            return new ResponseEntity<>(autore, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Autore> deleteAutore(@PathVariable("id") Long id) {
        try {
            autoreService.deleteAutore(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Autore> findById(@PathVariable("id") Long id) {
        Optional<Autore> a = autoreService.getAutore(id);
        if (a.isPresent()) {
            return new ResponseEntity<>(a.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/{datebefore}")
    public ResponseEntity<List<Autore>> findByDateBefore(@PathVariable("datebefore") Date date) {
        try {
            List<Autore> autori = autoreService.getAutoreBornBefore(date);
            if (autori.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(autori, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{dateafter}")
    public ResponseEntity<List<Autore>> findByDateAfter(@PathVariable("dateafter") Date date) {
        try {
            List<Autore> autori = autoreService.getAutoreBornAfter(date);
            if (autori.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(autori, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tutorial/{id}")
    public ResponseEntity<List<Tutorial>> getAllTutorialByAuthor(@RequestParam(required = true) Long id) {
        try {
            List<Tutorial> tutorials = autoreService.getAllTutorialsByAuthor(id);
            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/allordered")
//    public ResponseEntity<List<Autore>> findAllByCognomeNomeOrdered() {
//        try {
//            List<Autore> result = autoreService.getAllAutoriOrderedByCognomeNome();
//            if(result.isEmpty()){
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//            return new ResponseEntity<>(result, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
