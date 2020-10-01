package com.theater.app.service.impl;

import com.theater.app.domain.Order;
import com.theater.app.domain.OrderReport;
import com.theater.app.domain.Repertoire;
import com.theater.app.repository.OrderRepository;
import com.theater.app.repository.PlayRepository;
import com.theater.app.repository.RepertoireRepository;
import com.theater.app.service.OrderService;
import com.theater.app.service.ReportService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Service
public class ReportServiceImpl implements ReportService {

    private final RepertoireRepository repertoireRepository;
    private final PlayRepository playRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;

//    public ReportServiceImpl(RepertoireRepository repertoireRepository, PlayRepository playRepository,
//                             OrderRepository orderRepository) {
//        this.repertoireRepository = repertoireRepository;
//        this.playRepository = playRepository;
//        this.orderRepository = orderRepository;
//    }


    public ReportServiceImpl(RepertoireRepository repertoireRepository, PlayRepository playRepository, OrderRepository orderRepository, OrderService orderService) {
        this.repertoireRepository = repertoireRepository;
        this.playRepository = playRepository;
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

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
        List<Repertoire> repertoireList = (List<Repertoire>) repertoireRepository.findByStatusIsTrue();
        //load file and compile it
        File file = ResourceUtils.getFile("src/main/resources/reports/cancelledPlays.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(repertoireList);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "David");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\cancelledPlays.html");
//        JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\cancelledPlays.pdf");
        return "admin/reports";
    }

    @Override
    public String monthlyProfitReport() throws FileNotFoundException, JRException {
        String path = "C:\\Users\\David\\Desktop";
//        List<Order> orderList = orderRepository.getAggregates();
//        if(orderList.isPresent()) {
//            List<Object[]> objects = orderList.get();
//            Stream.of(objects.toString()).forEach(System.out::println);
//        }

        List<OrderReport> orderList = orderService.findOrders();
        for(OrderReport order: orderList){
            System.out.println(order.getMonth() + "  " + order.getDate());
        }
        //load file and compile it
        File file = ResourceUtils.getFile("src/main/resources/reports/monthlyProfit.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(orderList);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "David");

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\monthlyProfit.html");
//        JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\monthlyProfit.pdf");
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
//        JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\playAttendance.pdf");
        return "admin/reports";
    }

}
