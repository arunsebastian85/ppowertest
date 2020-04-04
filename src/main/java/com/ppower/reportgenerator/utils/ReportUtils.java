package com.ppower.reportgenerator.utils;

import org.springframework.stereotype.Component;

@Component
public class ReportUtils {
    private static final String SLCRHEADER= "Selection | Currency | NumOfBets | Total Stakes | Total Liability";
    private static final String TLCRHEADER= "Currency | NumOfBets | Total Stakes | Total Liability";

    public String getReportHeader(String type){
        return type.equalsIgnoreCase("SLCReport") ? SLCRHEADER : TLCRHEADER ;
    }
}
