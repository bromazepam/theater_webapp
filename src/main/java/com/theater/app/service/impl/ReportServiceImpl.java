package com.theater.app.service.impl;

import com.theater.app.domain.reportDAO.OrderReport;
import com.theater.app.domain.Repertoire;
import com.theater.app.domain.reportDAO.RepertoireReport;
import com.theater.app.repository.OrderRepository;
import com.theater.app.repository.PlayRepository;
import com.theater.app.repository.RepertoireRepository;
import com.theater.app.service.OrderService;
import com.theater.app.service.RepertoireService;
import com.theater.app.service.ReportService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {

    private final RepertoireRepository repertoireRepository;
    private final OrderService orderService;
    private final RepertoireService repertoireService;

    @Override
    public String repertoireReport() throws FileNotFoundException, JRException {
        String path = "C:\\Users\\David\\Desktop";
        List<Repertoire> repertoireList = (List<Repertoire>) repertoireRepository.findAll();
        //load file and compile it
        File file = ResourceUtils.getFile("src/main/resources/reports/repertoire.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(repertoireList);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "David");

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\repertoar.html");
        return "admin/reports";
    }

    @Override
    public String cancelledPlaysReport() throws FileNotFoundException, JRException {
        String path = "C:\\Users\\David\\Desktop";
        List<Repertoire> repertoireList = repertoireRepository.findByStatusIsTrue();
        //load file and compile it
        File file = ResourceUtils.getFile("src/main/resources/reports/cancelledPlays.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(repertoireList);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "David");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\cancelledPlays.html");
        return "admin/reports";
    }

    @Override
    public String monthlyProfitReport() throws FileNotFoundException, JRException {
        String path = "C:\\Users\\David\\Desktop";
        List<OrderReport> orderList = orderService.findOrders();
        //load file and compile it
        File file = ResourceUtils.getFile("src/main/resources/reports/monthlyProfit.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(orderList);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "David");

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\monthlyProfit.html");
        return "admin/reports";
    }

    @Override
    public String playAttendance() throws FileNotFoundException, JRException {
        String path = "C:\\Users\\David\\Desktop";
        List<Repertoire> repertoireList = repertoireRepository.findByStatusIsFalse();
        //load file and compile it
        File file = ResourceUtils.getFile("src/main/resources/reports/playAttendance.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(repertoireList);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "David");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\playAttendance.html");
        return "admin/reports";
    }

    @Override
    public String monthlyAttendanceReport() throws FileNotFoundException, JRException {
        String path = "C:\\Users\\David\\Desktop";
        List<RepertoireReport> orderList = repertoireService.findMonthlyAttendance();

        //load file and compile it
        File file = ResourceUtils.getFile("src/main/resources/reports/monthlyAttendance.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(orderList);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "David");

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\monthlyAttendance.html");
        return "admin/reports";
    }
}
