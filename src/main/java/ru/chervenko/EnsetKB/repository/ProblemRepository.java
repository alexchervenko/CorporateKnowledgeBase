package ru.chervenko.EnsetKB.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.chervenko.EnsetKB.model.Problem;

import java.util.List;
@Repository
public interface ProblemRepository extends MongoRepository<Problem, String> {
    List<Problem> findByName(String name);

    Page<Problem> findByNameContainsIgnoreCase(String request, Pageable pageable);

    Page<Problem> findAll(Pageable pageable);

}
