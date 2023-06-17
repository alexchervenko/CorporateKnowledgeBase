package ru.chervenko.EnsetKB.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chervenko.EnsetKB.models.Problem;

import java.util.List;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Integer> {
    List<Problem> findByName(String name);
}
