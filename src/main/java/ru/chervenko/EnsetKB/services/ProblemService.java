package ru.chervenko.EnsetKB.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chervenko.EnsetKB.models.Problem;
import ru.chervenko.EnsetKB.repositories.ProblemRepository;

import java.util.List;
import java.util.Optional;

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

    public Page<Problem> findByNameContains(String request, Pageable pageable){
        return problemRepository.findByNameContainsIgnoreCase(request, pageable);
    }

    public List<Problem> findAllByPage(Pageable pageable){
        return problemRepository.findAll(pageable).getContent();
    }

    public Optional<Problem> findById (int id) {
        return problemRepository.findById(id);
    }

    public List<Problem> findByName(String name){
        return problemRepository.findByName(name);
    }

    @Transactional
    public void save(Problem problem){
        problemRepository.save(problem);
    }

    @Transactional
    public void update(int id, Problem updatedProblem) {
        updatedProblem.setId(id);
        problemRepository.save(updatedProblem);
    }

    @Transactional
    public void delete(int id){
        problemRepository.deleteById(id);
    }


}
