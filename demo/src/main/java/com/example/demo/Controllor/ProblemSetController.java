package com.example.demo.Controllor;

import com.example.demo.Entity.ProblemSet;
import com.example.demo.Service.ProblemSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
public class ProblemSetController
{
    @Autowired
    ProblemSetService problemSetService;

    //增加题集
    @PostMapping("/addSet")
    public ProblemSet addSet(@RequestParam("userid") String userid,
                             @RequestParam("setName") String setName) throws SQLException
    {
        return problemSetService.createCheck(userid, setName);
    }

    // 获取所有题集
    @PostMapping("/getAllSets")
    public List<ProblemSet> getAllSets(@RequestParam("userid") String userid) throws SQLException
    {
        return problemSetService.getAllSetCheckByOwner(userid);
    }

    //删题集
    @PostMapping("/deleteSet")
    public Boolean deleteSet(@RequestParam("userid") String userid,
                             @RequestParam("setName") String setName) throws SQLException
    {
        return problemSetService.deleteCheck(userid, setName);
    }

    //编辑题集名称
    @PostMapping("/updateSet")
    public Boolean updateSet(@RequestParam("problemsetid") String problemsetid,
                             @RequestParam("newName") String newName,
                             @RequestParam("userid") String userid,
                             @RequestParam("oldName") String oldName) throws SQLException
    {
        return problemSetService.updateCheck(Long.valueOf(problemsetid), newName, userid, oldName);
    }
}
