package com.ppower.reportgenerator.service;

import com.ppower.reportgenerator.domain.BetDetails;

import java.util.List;

public interface ReportService {

   String getSelectionLiabilityByCurrencyReport(List<BetDetails> betDetailsList);

   String getTotalLiabilityByCurrencyReport();
}
