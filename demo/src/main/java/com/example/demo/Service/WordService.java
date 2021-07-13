package com.example.demo.Service;

import com.example.demo.Entity.ProblemSet;
import com.example.demo.Entity.Word;
import com.example.demo.Repository.WordRepository;
import com.example.demo.Utils.SendCreateTime;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class WordService
{
    @Autowired
    HikariDataSource dataSource;
    @Autowired
    WordRepository wordRepository;

    public ArrayList<Word> getWordList(String tableName) throws SQLException
    {
        String sql = "SELECT * FROM " + tableName + " ORDER BY createtime DESC";
        ArrayList<Word> wordList = new ArrayList<Word>();

        System.out.println(wordList);

        try(Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery(sql);)
        {
            while (rs.next())
            {
                Long wordid = rs.getLong("wordid");
                String spelling = rs.getString("spelling");
                String explanation = rs.getString("explanation");
                String createtime = rs.getString("createtime");
                Long proficiency = rs.getLong("proficiency");
                String nextreviewtime = rs.getString("nextreviewtime");
                Word word = new Word(wordid, spelling, explanation, createtime, proficiency, nextreviewtime);
                wordList.add(word);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return wordList;
    }

    public Long addWord(String tableName, String spelling, String explanation, String createtime,
                        Long proficiency, String nextreviewtime, Long size, Long problemsetid) throws SQLException
    {
        Connection connection = dataSource.getConnection();
        // 在对应的表内添加单词
        String sql = "INSERT INTO " + tableName +
                " (spelling, explanation, createtime, proficiency, nextreviewtime) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setObject(1, spelling);   // 注意：索引从1开始，索引开始处与sql语句相关，而不是数据库实际的列
        ps.setObject(2, explanation);
        ps.setObject(3, createtime);
        ps.setObject(4, proficiency);
        ps.setObject(5, nextreviewtime);
        ps.executeUpdate();

        // 在problemsets中更新单词总数
        sql = "UPDATE problemsets SET number = ? WHERE problemsetid = ?";
        ps = connection.prepareStatement(sql);
        ps.setObject(1, size);   // 注意：索引从1开始，索引开始处与sql语句相关，而不是数据库实际的列
        ps.setObject(2, problemsetid);
        ps.executeUpdate();

        // 返回单词的wordid
        sql = "SELECT wordid FROM " + tableName + " WHERE spelling = '" + spelling + "'";
        ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery(sql);
        Long wordid2 = null;
        while (rs.next())
        {
            wordid2 = rs.getLong("wordid");
        }

        connection.close();
        return wordid2;
    }

    public Boolean updateWord(String tableName, String wordid, String spelling, String explanation, String createtime) throws SQLException
    {
        Connection connection = dataSource.getConnection();
        String sql = "UPDATE " + tableName + " SET spelling = ?, explanation = ?, createtime = ? WHERE wordid = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setObject(1, spelling);   // 注意：索引从1开始，索引开始处与sql语句相关，而不是数据库实际的列
        ps.setObject(2, explanation);
        ps.setObject(3, createtime);
        ps.setObject(4, Long.valueOf(wordid));
        ps.executeUpdate();

        connection.close();
        return true;
    }

    public Boolean deleteWord(String tableName, String spelling, Long size, Long problemsetid) throws SQLException
    {
        Connection connection = dataSource.getConnection();
        String sql = "DELETE FROM " + tableName + " WHERE spelling = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setObject(1, spelling);   // 注意：索引从1开始，索引开始处与sql语句相关，而不是数据库实际的列
        ps.executeUpdate();

        sql = "UPDATE problemsets SET number = ? WHERE problemsetid = ?";
        ps = connection.prepareStatement(sql);
        ps.setObject(1, size);   // 注意：索引从1开始，索引开始处与sql语句相关，而不是数据库实际的列
        ps.setObject(2, problemsetid);
        ps.executeUpdate();

        connection.close();
        return true;
    }

    public ArrayList<Word> getReviewWords(String tableName) throws SQLException
    {
        // days sub
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.now();
        ArrayList<Word> wordArrayList = (ArrayList<Word>) getWordList(tableName);
        ArrayList<Word> wordReview = new ArrayList<>();
        for (Word word : wordArrayList)
        {
            LocalDate reviewTime = LocalDate.parse(word.getNextReviewTime().substring(0, 10), formatter);
            if (word.getProficiency() < 100)
            {
                // 当天应当复习的
                if (reviewTime.toEpochDay() == localDate.toEpochDay())
                    wordReview.add(word);
                // 之前未复习的
                if (reviewTime.toEpochDay() < localDate.toEpochDay())
                    wordReview.add(word);
            }
        }
        return wordReview;
    }

    public Boolean refreshWord(String tableName, Long proficiency, String nextreviewtime, String wordid) throws SQLException
    {
        Connection connection = dataSource.getConnection();
        String sql = "UPDATE " + tableName + " SET proficiency = ?, nextreviewtime = ? WHERE wordid = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setObject(1, proficiency);
        ps.setObject(2, nextreviewtime);
        ps.setObject(3, Long.valueOf(wordid));
        ps.executeUpdate();
        connection.close();
        return true;
    }
}
