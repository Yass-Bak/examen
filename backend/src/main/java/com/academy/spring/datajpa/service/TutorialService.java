package com.academy.spring.datajpa.service;

import com.academy.spring.datajpa.model.Student;
import com.academy.spring.datajpa.model.Tutorial;
import com.academy.spring.datajpa.repository.StudentRepository;
import com.academy.spring.datajpa.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TutorialService {
    @Autowired
    private TutorialRepository tutorialRepository;
    @Autowired
    private StudentRepository studentRepository;

    public List<Tutorial> getAllByTitle(String title) {
        if (title == null) {
            return tutorialRepository.findAll();
        }
        try {
            return tutorialRepository.findByTitleContaining(title);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Tutorial> getAllPublished() {
        try {
            return tutorialRepository.findByPublished(true);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public Tutorial createTutorial(Tutorial t) {
        Tutorial result = new Tutorial();
        result.setAutore(t.getAutore());
        result.setTitle(t.getTitle());
        result.setDescription(t.getDescription());
        result.setPublished(t.isPublished());
        return result;
    }

    public Optional<Tutorial> getTutorial(Long id) {
        return tutorialRepository.findById(id);
    }

    public void deleteTutorial(Long id) {
        tutorialRepository.deleteById(id);
    }

    public void deleteAllTutorial() {
        tutorialRepository.deleteAll();
    }

    public Tutorial save(Tutorial t) {
        return tutorialRepository.saveAndFlush(t);
    }

    public List<Student> findStudentsFollowingTutorial(Long id) {
        Optional<Tutorial> optional = tutorialRepository.findById(id);
        if (optional.isPresent()) {
            if (studentRepository.findAllByTutorialSeguito(optional.get()).isEmpty()) {
                return null;
            } else {
                return studentRepository.findAllByTutorialSeguito(optional.get());
            }
        }
        return null;


    }

}
