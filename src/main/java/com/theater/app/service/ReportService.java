package com.theater.app.service;

import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;

public interface ReportService {

    String repertoireReport(String reportFormat) throws FileNotFoundException, JRException;
    String cancelledPlaysReport(String reportFormat) throws FileNotFoundException, JRException;
}
