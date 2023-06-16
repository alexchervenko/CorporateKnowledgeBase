package ru.chervenko.EnsetKB.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chervenko.EnsetKB.models.Problem;
import ru.chervenko.EnsetKB.repositories.ProblemRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProblemService {
    private final ProblemRepository problemRepository;

    @Autowired
    public ProblemService(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    public List<Problem> findAll(){
        return problemRepository.findAll();
    }

}
