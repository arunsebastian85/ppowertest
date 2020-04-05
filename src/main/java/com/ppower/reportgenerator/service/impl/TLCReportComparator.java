package com.ppower.reportgenerator.service.impl;

import com.ppower.reportgenerator.domain.TotalLiabilityCurrencyReport;
import com.ppower.reportgenerator.service.ReportComparatorService;

public class TLCReportComparator implements ReportComparatorService{
    @Override
    public int compare(Object o1, Object o2) {
        TotalLiabilityCurrencyReport report1 = (TotalLiabilityCurrencyReport) o1;
        TotalLiabilityCurrencyReport report2 = (TotalLiabilityCurrencyReport) o2;
        int currencyComparison = report2.getCurrency().compareTo(report1.getCurrency());
        return currencyComparison ==0 ? report2.getTotalLiabilityFloat().compareTo(report1.getTotalLiabilityFloat())
                : currencyComparison;
    }
}
