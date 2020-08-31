package com.theater.app.service;

import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;

public interface ReportService {

    String repertoireReport() throws FileNotFoundException, JRException;
    String cancelledPlaysReport() throws FileNotFoundException, JRException;
    String monthlyProfitReport() throws FileNotFoundException, JRException;
    String playAttendance() throws FileNotFoundException, JRException;
}
