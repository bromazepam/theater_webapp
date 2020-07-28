package com.theater.app.service.impl;

import com.theater.app.domain.Play;
import com.theater.app.domain.Repertoire;
import com.theater.app.domain.Stage;
import com.theater.app.repository.PlayRepository;
import com.theater.app.repository.RepertoireRepository;
import com.theater.app.repository.StageRepository;
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
    private final StageRepository stageRepository;

    public ReportServiceImpl(RepertoireRepository repertoireRepository, PlayRepository playRepository,
                             StageRepository stageRepository) {
        this.repertoireRepository = repertoireRepository;
        this.playRepository = playRepository;
        this.stageRepository = stageRepository;
    }

    @Override
    public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
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
}
