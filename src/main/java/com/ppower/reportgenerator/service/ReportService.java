package com.ppower.reportgenerator.service;

import com.ppower.reportgenerator.domain.BetDetails;
import com.ppower.reportgenerator.domain.SelectionLiabilityCurrencyReport;

import java.util.List;

public interface ReportService {

   List<SelectionLiabilityCurrencyReport> getSelectionLiabilityByCurrencyReport(List<BetDetails> betDetailsList);

   String getTotalLiabilityByCurrencyReport();
}
