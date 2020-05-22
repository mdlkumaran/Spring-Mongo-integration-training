package com.mdl.legostore.api;

import com.mdl.legostore.model.AvgRatingModel;
import com.mdl.legostore.service.ReportingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/legostore/api/reports")
public class ReportingController {
    private ReportingService reportingService;

    public ReportingController(ReportingService reportingService) {
        this.reportingService = reportingService;
    }

    @GetMapping("avgRatingsReport")
    public List<AvgRatingModel> getAvgRatingReport() {
        return this.reportingService.getAvgRatingReport();
    }
}
