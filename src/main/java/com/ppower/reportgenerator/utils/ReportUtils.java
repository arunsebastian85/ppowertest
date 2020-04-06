package com.ppower.reportgenerator.utils;

import com.ppower.reportgenerator.boundary.ReportInputObject;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.function.Function;

@Component
public class ReportUtils {

    private static final String SLCR_HEADER= "Selection | Currency | NumOfBets | Total Stakes | Total Liability";
    private static final String TLCR_HEADER= "Currency | NumOfBets | Total Stakes | Total Liability";
    private static final String INPUT_FORMAT_ERROR_MSG="Invalid InputFormat param value. Allowed only [CSV,JSON] |";
    private static final String OUTPUT_FORMAT_ERROR_MSG="Invalid OutputFormat param value. Allowed only [CSV,CONSOLE] |";
    private static final String REPORT_TYPE_ERROR_MSG="Invalid ReportType param value. Allowed only [SLCReport,TLCReport]";
    private static final String INPUT_FILE_EXT_ERROR_MSG="Invalid Input file format. Only .csv file allowed]";
    private static final String OUTPUT_FILE_EXT_ERROR_MSG="Invalid Output file format. Only .csv file allowed]";
    private static final String SLC_REPORT = "SLCReport";

    public String getReportHeader(String type){
        return type.equalsIgnoreCase(SLC_REPORT) ? SLCR_HEADER : TLCR_HEADER ;
    }

    public String formatAmount( Float value){
        return String.format("%.2f", value);
    }

    public void sanitizeInput(ReportInputObject reportInputObject) throws RuntimeException{

        StringBuffer errorMessage =inputValidationFunction.apply(reportInputObject);
        if(!inputValidationFunction.apply(reportInputObject).toString().isEmpty()){
            throw new RuntimeException(errorMessage.toString());
        }

    }

    Function<ReportInputObject,StringBuffer> inputValidationFunction = reportInputObject -> {
        StringBuffer errorMessage = new StringBuffer();
        if(!Arrays.stream(InputFormat.values()).anyMatch((i) ->
                i.name().equals(capitalize(reportInputObject.getInputFormat())))){
            errorMessage.append(INPUT_FORMAT_ERROR_MSG);
        }
        if(!Arrays.stream(OutputFormat.values()).anyMatch((o) ->
                o.name().equals(capitalize(reportInputObject.getOutputFormat())))){
            errorMessage.append(OUTPUT_FORMAT_ERROR_MSG);
        }
        if(!Arrays.stream(ReportType.values()).anyMatch((r) ->
                r.name().equals(capitalize(reportInputObject.getReportType())))) {
            errorMessage.append(REPORT_TYPE_ERROR_MSG);
        }
        if(capitalize(reportInputObject.getInputFormat()).equals("CSV")
                && !capitalize(reportInputObject.getInputFile()).endsWith(".CSV")){
            errorMessage.append(INPUT_FILE_EXT_ERROR_MSG);
        }
        if(!capitalize(reportInputObject.getOutputFile()).endsWith(".CSV")){
            errorMessage.append(OUTPUT_FILE_EXT_ERROR_MSG);
        }
        return errorMessage;
    };

    private static String capitalize(String value){
        return value.toUpperCase();
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
