package com.example.demo.Entity;

public class CustomResponse
{
    private String message;
    private String code;
    private String email;
    private Long id;


    public CustomResponse(String message, String email, String code, Long id)
    {
        this.message = message;
        this.code = code;
        this.email = email;
        this.id = id;
    }

    public CustomResponse(String message)
    {
        this.message = message;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }

    public String getCode()
    {
        return code;
    }

    public String getMessage()
    {
        return message;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
}
