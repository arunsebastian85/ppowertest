package com.ppower.reportgenerator.utils;

import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ReportUtils {

    private static final String SLCR_HEADER= "Selection | Currency | NumOfBets | Total Stakes | Total Liability";
    private static final String TLCR_HEADER= "Currency | NumOfBets | Total Stakes | Total Liability";
    private static final String INPUT_FORMAT_ERROR_MSG="Invalid InputFormat param value. Allowed only [CSV,JSON] |";
    private static final String OUTPUT_FORMAT_ERROR_MSG="Invalid OutputFormat param value. Allowed only [CSV,CONSOLE] |";
    private static final String REPORT_TYPE_ERROR_MSG="Invalid ReportType param value. Allowed only [SLCReport,TLCReport]";
    private static final String SLC_REPORT = "SLCReport";

    public String getReportHeader(String type){
        return type.equalsIgnoreCase(SLC_REPORT) ? SLCR_HEADER : TLCR_HEADER ;
    }

    public String formatAmount( Float value){
        return String.format("%.2f", value);
    }

    public void sanitizeInput(String inputFormat, String outputFormat, String reportType)
            throws RuntimeException{
        StringBuffer errorMessage = new StringBuffer();
        if(!Arrays.stream(InputFormat.values()).anyMatch((i) -> i.name().equals(inputFormat))){
            errorMessage.append(INPUT_FORMAT_ERROR_MSG);
        }
        if(!Arrays.stream(OutputFormat.values()).anyMatch((o) -> o.name().equals(outputFormat))){
            errorMessage.append(OUTPUT_FORMAT_ERROR_MSG);
        }
        if(!Arrays.stream(ReportType.values()).anyMatch((r) -> r.name().equals(reportType))) {
            errorMessage.append(REPORT_TYPE_ERROR_MSG);
        }
        if(!errorMessage.toString().isEmpty()){
            throw new RuntimeException(errorMessage.toString());
        }
    }

}

enum InputFormat {
    CSV, JSON
};

enum OutputFormat {
    CSV, CONSOLE
};

enum ReportType {
    SLCREPORT, TLCREPORT
};
