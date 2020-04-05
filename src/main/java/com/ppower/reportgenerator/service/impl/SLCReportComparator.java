package com.ppower.reportgenerator.service.impl;

import com.ppower.reportgenerator.domain.SelectionLiabilityCurrencyReport;
import com.ppower.reportgenerator.service.ReportComparatorService;

public class SLCReportComparator implements ReportComparatorService {

    @Override
    public int compare(Object o1, Object o2) {
        SelectionLiabilityCurrencyReport report1 = (SelectionLiabilityCurrencyReport) o1;
        SelectionLiabilityCurrencyReport report2 = (SelectionLiabilityCurrencyReport) o2;
        int currencyComparison = report2.getCurrency().compareTo(report1.getCurrency());
        return currencyComparison ==0 ? report2.getTotalLiabilityFloat().compareTo(report1.getTotalLiabilityFloat())
                : currencyComparison;
    }

}
