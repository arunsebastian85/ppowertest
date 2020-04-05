package com.ppower.reportgenerator.utils;

import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ReportUtils {
    private static final String SLCRHEADER= "Selection | Currency | NumOfBets | Total Stakes | Total Liability";
    private static final String TLCRHEADER= "Currency | NumOfBets | Total Stakes | Total Liability";
    private static final String INPUT_FORMAT_ERROR_MSG="Invalid InputFormat param value. Allowed only [CSV,JSON] |";
    private static final String OUTPUT_FORMAT_ERROR_MSG="Invalid OutputFormat param value. Allowed only [CSV,CONSOLE] |";
    private static final String REPORT_TYPE_ERROR_MSG="Invalid ReportType param value. Allowed only [SLCReport,TLCReport]";


    public String getReportHeader(String type){
        return type.equalsIgnoreCase("SLCReport") ? SLCRHEADER : TLCRHEADER ;
    }

    public String formatAmount( Float value){
        return String.format("%.2f", value);
    }

    public void sanitizeInput(String inputFormat, String outputFormat, String reportType)
            throws RuntimeException{

        StringBuffer errorMessage = new StringBuffer();
        if(!Arrays.stream(InputFormat.values()).anyMatch((t) -> t.name().equals(inputFormat))){
            errorMessage.append(INPUT_FORMAT_ERROR_MSG);
        }
        if(!Arrays.stream(OutputFormat.values()).anyMatch((t) -> t.name().equals(outputFormat))){
            errorMessage.append(OUTPUT_FORMAT_ERROR_MSG);
        }
        if(!Arrays.stream(ReportType.values()).anyMatch((t) -> t.name().equals(reportType))) {
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
