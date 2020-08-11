package com.theater.app.controller.admin;

import com.theater.app.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;

@Controller
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/repertoireReport/{format}")
    public String generateReport(@PathVariable String format) throws FileNotFoundException, JRException {
        return reportService.repertoireReport(format);
    }

    @GetMapping("/cancelledPlaysReport/{format}")
    public String generateCancelledPlaysReport(@PathVariable String format) throws FileNotFoundException, JRException {
        return reportService.cancelledPlaysReport(format);
    }

    @GetMapping("/monthlyProfitReport/{format}")
    public String generateMonthlyProfitReport(@PathVariable String format) throws FileNotFoundException, JRException {
        return reportService.monthlyProfitReport(format);
    }
    @GetMapping("/playAttendanceReport/{format}")
    public String generatePlayAttendanceReport(@PathVariable String format) throws FileNotFoundException, JRException {
        return reportService.playAttendance(format);
    }
}
