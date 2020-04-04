package com.ppower.reportgenerator.utils;

import com.ppower.reportgenerator.service.ReportComparatorService;
import com.ppower.reportgenerator.service.impl.SLCReportComparator;
import com.ppower.reportgenerator.service.impl.TLCReportComparator;

public class ReportComparatorFactory {
    private static final String SLCR = "SLCR";
    private static final String TLCR = "TLCR";
    public static ReportComparatorService getInstance(String type){
        if(type.equalsIgnoreCase(SLCR)){
            return new SLCReportComparator();
        } else {
            return new TLCReportComparator();
        }
    }
}
