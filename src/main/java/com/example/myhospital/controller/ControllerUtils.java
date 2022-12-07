package com.example.myhospital.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ControllerUtils {

    private final static Date date = new Date(System.currentTimeMillis());
    private final static SimpleDateFormat formattedDate =new SimpleDateFormat("dd/MM/yyyy");
    public final static String loggedDate = formattedDate.format(date);
}
