package com.ppower.reportgenerator.service;

import com.ppower.reportgenerator.domain.BetDetails;
import com.ppower.reportgenerator.domain.SelectionLiabilityCurrencyReport;
import com.ppower.reportgenerator.domain.TotalLiabilityCurrencyReport;

import java.util.List;

public interface ReportService {

   List<SelectionLiabilityCurrencyReport> getSelectionLiabilityByCurrencyReport(List<BetDetails> betDetailsList);

   List<TotalLiabilityCurrencyReport> getTotalLiabilityByCurrencyReport(List<BetDetails> betDetailsList);

   List<BetDetails> getBetDetailsFromCSV(String filePath);

   //List<BetDetails> getBetDetailsFromJson();

}
