package com.theater.app.controller.admin;

import com.theater.app.service.ReportService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;

@RequiredArgsConstructor
@Controller
public class ReportController {

    private final ReportService reportService;

    @RequestMapping("/reports")
    public String reports() {
        return "admin/reports";
    }

    @PostMapping("/reports")
    public String reports(HttpServletRequest request) throws FileNotFoundException, JRException {
        String type = request.getParameter("type");
        switch (type) {
            case "repertoire":
                return reportService.repertoireReport();
            case "cancelledPlays":
                return reportService.cancelledPlaysReport();
            case "monthlyAttendance":
                return reportService.monthlyAttendanceReport();
            case "monthlyProfit":
                return reportService.monthlyProfitReport();
            case "playsPerMonth":
                return reportService.playAttendance();
        }
        return "admin/reports";
    }

}
