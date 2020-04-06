package com.ppower.reportgenerator.service;

import com.ppower.reportgenerator.boundary.ReportInputObject;
import com.ppower.reportgenerator.boundary.ReportResponseData;
import com.ppower.reportgenerator.domain.BetDetails;
import com.ppower.reportgenerator.domain.SelectionLiabilityCurrencyReport;
import com.ppower.reportgenerator.domain.TotalLiabilityCurrencyReport;

import java.util.List;

public interface ReportGeneratorService {

   List<SelectionLiabilityCurrencyReport> getSelectionLiabilityByCurrencyReport(List<BetDetails> betDetailsList);

   List<TotalLiabilityCurrencyReport> getTotalLiabilityByCurrencyReport(List<BetDetails> betDetailsList);

   ReportResponseData generateReport(ReportInputObject reportInputObject);
   //List<BetDetails> getBetDetailsFromJson();

}
