package com.academy.spring.datajpa.service;

import com.academy.spring.datajpa.model.Autore;
import com.academy.spring.datajpa.model.Tutorial;
import com.academy.spring.datajpa.repository.AutoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AutoreService {

    @Autowired
    private AutoreRepository autoreRepository;
    @Autowired
    EntityManager em;

    public List<Autore> getAllAutoriOrderedByCognomeNome() {
        Query q = em.createNativeQuery("SELECT a.name, a.surname FROM authors a ORDER BY a.surname, a.name");
        List authors = q.getResultList();
        List<Autore> resultList = new ArrayList<>();
        for (Object elem :
                authors) {
            resultList.add((Autore) elem);
        }
        return resultList;
    }

    public List<Autore> getAllBySurname(String cognome) {
        if (cognome == null) {
            return autoreRepository.findAll();
        }
        try {
            return autoreRepository.findByCognome(cognome);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Autore createAutore(Autore a) {
        Autore result = new Autore();
        result.setNome(a.getNome());
        result.setCognome(a.getCognome());
        result.setDataDiNascita(a.getDataDiNascita());
        return result;
    }

    public Optional<Autore> getAutore(Long id) {
        return autoreRepository.findById(id);
    }

    public void deleteAutore(Long id) {
        autoreRepository.deleteById(id);
    }

    public List<Autore> getAutoreBornBefore(Date data) {
        if (data == null) {
            return autoreRepository.findAll();
        }
        try {
            return autoreRepository.findAutoreByDataDiNascitaBefore(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Autore> getAutoreBornAfter(Date data) {
        if (data == null) {
            return autoreRepository.findAll();
        }
        try {
            return autoreRepository.findAutoreByDataDiNascitaAfter(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<Tutorial> getAllTutorialsByAuthor(Long id){
        Optional<Autore> a=autoreRepository.findById(id);
        try{
            return autoreRepository.findById(id).get().getListaTutorial();}
     catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}

    public Autore save(Autore a) {
        return autoreRepository.saveAndFlush(a);
    }
}
