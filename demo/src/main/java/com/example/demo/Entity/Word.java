package com.example.demo.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Word
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long wordid;
    String spelling;
    String explanation;
    String createtime;
    Long proficiency;
    String nextReviewTime;

    public Word(Long wordid, String spelling, String explanation, String createtime, Long proficiency, String nextReviewTime)
    {
        this.wordid = wordid;
        this.spelling = spelling;
        this.explanation = explanation;
        this.createtime = createtime;
        this.proficiency = proficiency;
        this.nextReviewTime = nextReviewTime;
    }

    public Word()
    {

    }

    public Long getWordid()
    {
        return wordid;
    }

    public void setWordid(Long wordid)
    {
        this.wordid = wordid;
    }

    public String getSpelling()
    {
        return spelling;
    }

    public void setSpelling(String spelling)
    {
        this.spelling = spelling;
    }

    public String getExplanation()
    {
        return explanation;
    }

    public void setExplanation(String explanation)
    {
        this.explanation = explanation;
    }

    public String getCreatetime()
    {
        return createtime;
    }

    public void setCreatetime(String createtime)
    {
        this.createtime = createtime;
    }

    public Long getProficiency()
    {
        return proficiency;
    }

    public void setProficiency(Long proficiency)
    {
        this.proficiency = proficiency;
    }

    public String getNextReviewTime()
    {
        return nextReviewTime;
    }

    public void setNextReviewTime(String nextReviewTime)
    {
        this.nextReviewTime = nextReviewTime;
    }
}
