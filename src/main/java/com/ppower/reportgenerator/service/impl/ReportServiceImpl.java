package com.ppower.reportgenerator.service.impl;

import com.ppower.reportgenerator.domain.BetDetails;
import com.ppower.reportgenerator.service.ReportService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Override
    public String getSelectionLiabilityByCurrencyReport(List<BetDetails> betDetailsList) {

        Map<String, Map<String,List<BetDetails>>> betDetailsGrouped= betDetailsList.stream().collect(Collectors.groupingBy(e->e.getSelectionName(),
                Collectors.groupingBy(BetDetails::getCurrency)));
        return "success";
    }

    @Override
    public String getTotalLiabilityByCurrencyReport() {
        return "success";
    }
}
