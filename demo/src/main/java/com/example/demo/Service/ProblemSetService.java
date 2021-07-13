package com.example.demo.Service;

import com.example.demo.Entity.ProblemSet;
import com.example.demo.Repository.ProblemSetRepository;
import com.example.demo.Utils.SendCreateTime;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProblemSetService
{
    @Autowired
    HikariDataSource dataSource;
    @Autowired
    ProblemSetRepository problemSetRepository;

    public List<ProblemSet> getAllSetCheckByOwner(String userid) throws SQLException
    {
        Connection connection = dataSource.getConnection();
        String sql = "SELECT * FROM problemsets WHERE owner = '" + userid + "' " + "ORDER BY createtime DESC";
        //String sql = "SELECT * FROM problemsets WHERE owner =  ?  ORDER BY createtime DESC";
        PreparedStatement ps = connection.prepareStatement(sql);
        //ps.setObject(1, owner);   // 注意：索引从1开始，索引开始处与sql语句相关，而不是数据库实际的列
        ResultSet rs = ps.executeQuery(sql);

        ArrayList<ProblemSet> problemSetList = new ArrayList<ProblemSet>();
        while (rs.next())
        {
            Long problemsetid = rs.getLong("problemsetid");
            String name = rs.getString("name");
            String owner = rs.getString("owner");
            Long number = rs.getLong("number");
            String createtime = rs.getString("createtime");
            ProblemSet problemSet = new ProblemSet(problemsetid, name, owner, number, createtime);
            problemSetList.add(problemSet);
        }

        connection.close();
        return problemSetList;
    }

    public ProblemSet createCheck(String userId, String setName) throws SQLException
    {
        if (problemSetRepository.findProblemSetByOwnerAndName(userId, setName) != null) return null;
        else
        {
            //存储建表信息
            ProblemSet problemSet = new ProblemSet();
            problemSet.setName(setName);
            problemSet.setCreatetime(SendCreateTime.sendTime());
            problemSet.setNumber(Long.valueOf(0));
            problemSet.setOwner(userId);
            problemSetRepository.save(problemSet);

            //创建独立表
            String tablename = userId + setName;
            Connection connection = dataSource.getConnection();
            String sql = "CREATE TABLE " + tablename + "(" +
                    "wordid BIGINT AUTO_INCREMENT NOT NULL," +
                    "spelling VARCHAR(255) NOT NULL," +
                    "explanation VARCHAR(255) NOT NULL," +
                    "createtime  VARCHAR(255) NOT NULL," +
                    "proficiency BIGINT NOT NULL," +
                    "nextreviewtime  VARCHAR(255) NOT NULL," +
                    "PRIMARY KEY(wordid)" +
                    ") Engine=INNODB DEFAULT CHARSET=UTF8;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();

            connection.close();
            return problemSet;
        }
    }

    public Boolean deleteCheck(String userid, String setName) throws SQLException
    {
        Connection connection = dataSource.getConnection();
        String setname = userid + setName;
        // 删表
        PreparedStatement ps = connection.prepareStatement("DROP TABLE " + setname + ";");
        ps.executeUpdate();

        // 删数据
        ps = connection.prepareStatement("DELETE FROM problemsets WHERE name = ? AND owner = ?");
        ps.setObject(1, setName);
        ps.setObject(2, userid);
        ps.executeUpdate();

        connection.close();
        return true;
    }

    public Boolean updateCheck(Long problemsetid, String newName, String userid, String oldName) throws SQLException
    {
        Connection connection = dataSource.getConnection();
        // 改表
        String sql = "ALTER TABLE " + userid + oldName + " RENAME TO " + userid + newName;
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.executeUpdate();

        // 改数据
        sql = "UPDATE problemsets SET name = ? WHERE problemsetid = ? ";
        ps = connection.prepareStatement(sql);
        ps.setObject(1, newName);
        ps.setObject(2, problemsetid);
        ps.executeUpdate();

        connection.close();
        return true;
    }
}
