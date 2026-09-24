package com.documentapproval.controller;

import com.documentapproval.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummaryReport() {
        Map<String, Object> report = reportService.getSummaryReport();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/document-status")
    public ResponseEntity<Map<String, Long>> getDocumentStatusBreakdown() {
        Map<String, Long> breakdown = reportService.getDocumentStatusBreakdown();
        return ResponseEntity.ok(breakdown);
    }
}
