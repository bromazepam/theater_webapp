package com.theater.app.service.impl;

import com.theater.app.domain.Order;
import com.theater.app.domain.Repertoire;
import com.theater.app.repository.OrderRepository;
import com.theater.app.repository.PlayRepository;
import com.theater.app.repository.RepertoireRepository;
import com.theater.app.service.ReportService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    private final RepertoireRepository repertoireRepository;
    private final PlayRepository playRepository;
    private final OrderRepository orderRepository;

    public ReportServiceImpl(RepertoireRepository repertoireRepository, PlayRepository playRepository,
                             OrderRepository orderRepository) {
        this.repertoireRepository = repertoireRepository;
        this.playRepository = playRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public String repertoireReport(String reportFormat) throws FileNotFoundException, JRException {
        String path = "C:\\Users\\David\\Desktop";
        List<Repertoire> repertoireList = (List<Repertoire>) repertoireRepository.findAll();
        //load file and compile it
        File file = ResourceUtils.getFile("src/main/resources/reports/repertoire.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(repertoireList);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "David");

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\repertoar.html");
        }
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\repertoar.pdf");
        }
        return "admin/reports";
    }

    @Override
    public String cancelledPlaysReport(String reportFormat) throws FileNotFoundException, JRException {
        String path = "C:\\Users\\David\\Desktop";
        List<Repertoire> repertoireList = (List<Repertoire>) repertoireRepository.findByStatusIsTrue();
        //load file and compile it
        File file = ResourceUtils.getFile("src/main/resources/reports/cancelledPlays.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(repertoireList);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "David");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\cancelledPlays.html");
        }
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\cancelledPlays.pdf");
        }
        return "admin/reports";
    }

    @Override
    public String monthlyProfitReport(String reportFormat) throws FileNotFoundException, JRException {
        String path = "C:\\Users\\David\\Desktop";
        List<Order> orderList = (List<Order>) orderRepository.findAllAndSum();
        //load file and compile it
        File file = ResourceUtils.getFile("src/main/resources/reports/monthlyProfit.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(orderList);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "David");
        for(Order order : orderList){
            System.out.println("u nis se nece jedu becke snicle!!!" + order.getTotal());
        }
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\monthlyProfit.html");
        }
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\monthlyProfit.pdf");
        }
        return "admin/reports";
    }

    @Override
    public String playAttendance(String reportFormat) throws FileNotFoundException, JRException {
        String path = "C:\\Users\\David\\Desktop";
        List<Repertoire> repertoireList = repertoireRepository.findByStatusIsFalse();
        //load file and compile it
        File file = ResourceUtils.getFile("src/main/resources/reports/playAttendance.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(repertoireList);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "David");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\playAttendance.html");
        }
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\playAttendance.pdf");
        }
        return "admin/reports";
    }
}
