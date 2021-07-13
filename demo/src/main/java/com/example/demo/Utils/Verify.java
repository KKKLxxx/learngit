package com.example.demo.Utils;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

public class Verify
{
    private static final String SYMBOLS = "0123456789";
    // SecureRandom 真随机数
    private static final Random RANDOM = new SecureRandom();

    public static String generateVerCode()
    {
        char[] authCode = new char[6];
        for (int i = 0; i < authCode.length; i++)
        {
            authCode[i] = SYMBOLS.charAt(RANDOM.nextInt(authCode.length));
        }
        return new String(authCode);
    }

    public static int getMinute(Date fromDate, Date toDate)
    {
        return (int) (toDate.getTime() - fromDate.getTime()) / (60 * 1000);
    }
}
