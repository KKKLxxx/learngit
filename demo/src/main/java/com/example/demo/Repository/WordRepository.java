package com.example.demo.Repository;

import com.example.demo.Entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<Word, Long>
{

}
