package com.ppower.reportgenerator.controller;

import com.ppower.reportgenerator.boundary.ReportInputObject;
import com.ppower.reportgenerator.boundary.ReportResponseData;
import com.ppower.reportgenerator.service.ReportGeneratorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/ppower")
@Api(tags = "Report Controller")
public class ReportController {

    @Autowired
    ReportGeneratorService reportGeneratorService;

    private static String CSV = "CSV";
    private static String JSON = "JSON";

    @RequestMapping(value = {"/v1/generate/fromcsv"}, method = RequestMethod.GET)
    @ApiOperation(value = "Generate Reports from CSV",
            notes = "Generate report from a CSV source as input",
            response = ReportResponseData.class)
    public ResponseEntity<ReportResponseData> generateRreportfromCSV
            (@RequestParam(value = "reportType" , defaultValue = "SLCReport") String reportType,
             @RequestParam(value = "outputFile", defaultValue = "SLCReport.csv") String outputFile,
             @RequestParam(value = "outputFormat", defaultValue = "CSV") String outputFormat,
             @RequestParam(value = "inputFile", defaultValue = "bet_details.csv") String inputFile){
        ReportInputObject reportInputObject = ReportInputObject.builder()
                .inputFile(inputFile)
                .inputFormat(CSV)
                .outputFile(outputFile)
                .outputFormat(outputFormat)
                .reportType(reportType)
                .build();
        ReportResponseData reportResponseData = reportGeneratorService.generateReport(reportInputObject);
        if(Objects.nonNull(reportResponseData)){
            return new ResponseEntity<ReportResponseData>(reportResponseData, HttpStatus.OK);
        } else {
            return new ResponseEntity<ReportResponseData>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = {"/v1/generate/fromhttp"}, method = RequestMethod.GET)
    @ApiOperation(value = "Generate Reports from JSON",
            notes = "Generate report from an HTTP service response JSON as input",
            response = ReportResponseData.class)
    public ResponseEntity<ReportResponseData> generateRreportsFromJson
            (@RequestParam(value = "reportType", defaultValue = "SLCReport") String reportType,
             @RequestParam(value = "outputFile", defaultValue = "SLCReport.csv") String outputFile,
             @RequestParam(value = "outputFormat", defaultValue = "csv") String outputFormat,
             @RequestParam(value = "serviceUrl", defaultValue = "<http://serviceurl>") String serviceUrl){
        ReportInputObject reportInputObject = ReportInputObject.builder()
                .inputFile(serviceUrl)
                .inputFormat(CSV)
                .outputFile(outputFile)
                .outputFormat(outputFormat)
                .reportType(reportType)
                .build();
        ReportResponseData reportResponseData = reportGeneratorService.generateReport(reportInputObject);
        if(Objects.nonNull(reportResponseData)){
            return new ResponseEntity<ReportResponseData>(reportResponseData, HttpStatus.OK);
        } else {
            return new ResponseEntity<ReportResponseData>(HttpStatus.NOT_FOUND);
        }
    }

}
