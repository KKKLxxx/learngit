package com.example.demo.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SendCreateTime
{
    public static String sendTime()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

    public static String sendDate()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");
        return LocalDateTime.now().format(formatter);
    }
}
