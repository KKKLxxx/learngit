package com.example.demo.Controllor;

import com.example.demo.Entity.ProblemSet;
import com.example.demo.Entity.Word;
import com.example.demo.Repository.WordRepository;
import com.example.demo.Service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class WordController
{
    @Autowired
    private WordService wordService;

    @PostMapping(value = "/getWordList")
    public ArrayList<Word> getWordList(@RequestParam("tableName") String tableName) throws SQLException
    {
        return wordService.getWordList(tableName);
    }

    // 新添单词
    @PostMapping("/addWord")
    public Long addWord(@RequestParam("tableName") String tableName,
                        @RequestParam("spelling") String spelling,
                        @RequestParam("explanation") String explanation,
                        @RequestParam("createtime") String createtime,
                        @RequestParam("proficiency") String proficiency,
                        @RequestParam("nextreviewtime") String nextreviewtime,
                        @RequestParam("size") String size,
                        @RequestParam("problemsetid") String problemsetid) throws SQLException
    {
        return wordService.addWord(tableName, spelling, explanation, createtime,
                Long.valueOf(proficiency), nextreviewtime, Long.valueOf(size), Long.valueOf(problemsetid));
    }

    // 修改单词
    @PostMapping("/updateWord")
    public Boolean updateWord(@RequestParam("tableName") String tableName,
                              @RequestParam("wordid") String wordid,
                              @RequestParam("spelling") String spelling,
                              @RequestParam("explanation") String explanation,
                              @RequestParam("createtime") String createtime) throws SQLException
    {
        return wordService.updateWord(tableName, wordid, spelling, explanation, createtime);
    }

    // 修改单词
    @PostMapping("/deleteWord")
    public Boolean deleteWord(@RequestParam("tableName") String tableName,
                              @RequestParam("spelling") String spelling,
                              @RequestParam("size") String size,
                              @RequestParam("problemsetid") String problemsetid) throws SQLException
    {
        return wordService.deleteWord(tableName, spelling, Long.valueOf(size), Long.valueOf(problemsetid));
    }

    @PostMapping("/reviewWord")
    public ArrayList<Word> getReviewWords(@RequestParam("tableName") String tableName) throws SQLException
    {
        return wordService.getReviewWords(tableName);
    }

    @PostMapping("/refreshWord")
    public Boolean refreshWord(@RequestParam("tableName") String tableName,
                               @RequestParam("proficiency") String proficiency,
                               @RequestParam("nextreviewtime") String nextreviewtime,
                               @RequestParam("wordid") String wordid) throws SQLException
    {
        return wordService.refreshWord(tableName, Long.valueOf(proficiency), nextreviewtime, wordid);
    }
}
