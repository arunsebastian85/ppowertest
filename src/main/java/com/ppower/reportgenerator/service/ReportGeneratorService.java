package com.ppower.reportgenerator.service;

import com.ppower.reportgenerator.boundary.ReportResponseData;
import com.ppower.reportgenerator.domain.BetDetails;
import com.ppower.reportgenerator.domain.GenericReport;
import com.ppower.reportgenerator.domain.SelectionLiabilityCurrencyReport;
import com.ppower.reportgenerator.domain.TotalLiabilityCurrencyReport;

import java.util.List;

public interface ReportGeneratorService {

   List<SelectionLiabilityCurrencyReport> getSelectionLiabilityByCurrencyReport(List<BetDetails> betDetailsList);

   List<TotalLiabilityCurrencyReport> getTotalLiabilityByCurrencyReport(List<BetDetails> betDetailsList);

   ReportResponseData generateReport(String reportType, String outputFormat, String inputFormat
           , String inputFile, String outputFile );
   //List<BetDetails> getBetDetailsFromJson();

}
