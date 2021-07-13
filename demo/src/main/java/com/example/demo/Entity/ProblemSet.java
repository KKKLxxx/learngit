package com.example.demo.Entity;

import javax.persistence.*;


@Entity
@Table(name = "problemsets")
public class ProblemSet
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long problemsetid;
    String name;
    String owner;
    Long number;
    String createtime;

    public ProblemSet()
    {
    }

    public ProblemSet(Long problemsetid, String name, String owner, Long number, String createtime)
    {
        this.problemsetid = problemsetid;
        this.name = name;
        this.owner = owner;
        this.number = number;
        this.createtime = createtime;
    }

    public Long getProblemsetid()
    {
        return problemsetid;
    }

    public void setProblemsetid(Long problemsetid)
    {
        this.problemsetid = problemsetid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getOwner()
    {
        return owner;
    }

    public void setOwner(String owner)
    {
        this.owner = owner;
    }

    public Long getNumber()
    {
        return number;
    }

    public void setNumber(Long number)
    {
        this.number = number;
    }

    public String getCreatetime()
    {
        return createtime;
    }

    public void setCreatetime(String createtime)
    {
        this.createtime = createtime;
    }
}
