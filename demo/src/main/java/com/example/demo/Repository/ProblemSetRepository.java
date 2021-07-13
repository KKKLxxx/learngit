package com.example.demo.Repository;

import com.example.demo.Entity.ProblemSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemSetRepository extends JpaRepository<ProblemSet, Long>
{
    List<ProblemSet> findProblemSetByOwner(String owner);

    ProblemSet findProblemSetByOwnerAndName(String owner, String name);
}
