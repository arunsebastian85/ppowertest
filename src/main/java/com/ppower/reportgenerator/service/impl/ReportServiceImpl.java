package com.ppower.reportgenerator.service.impl;

import com.ppower.reportgenerator.domain.BetDetails;
import com.ppower.reportgenerator.domain.SelectionLiabilityCurrencyReport;
import com.ppower.reportgenerator.service.ReportService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Override
    public  List<SelectionLiabilityCurrencyReport> getSelectionLiabilityByCurrencyReport(List<BetDetails> betDetailsList) {

        Map<String, Map<String,List<BetDetails>>> betDetailsGrouped= betDetailsList.stream().collect(Collectors.groupingBy(e->e.getSelectionName(),
                Collectors.groupingBy(BetDetails::getCurrency)));

        List<SelectionLiabilityCurrencyReport> selectionLiabilityCurrencyReports = new ArrayList<>();
        betDetailsGrouped.entrySet().forEach(entry -> {
            entry.getValue().entrySet().forEach(keySet->{
                SelectionLiabilityCurrencyReport selectionLiabilityCurrencyReport = SelectionLiabilityCurrencyReport.builder()
                        .selectionName(entry.getKey())
                        .currency(keySet.getKey())
                        .build();
                float totalStake=0L;
                float totalLiability=0L;
                for(BetDetails data:keySet.getValue()){
                   totalStake = totalStake + data.getStake();
                   totalLiability = totalLiability + (data.getStake()*data.getPrice());
                }
                selectionLiabilityCurrencyReport.setTotalStakes(totalStake);
                selectionLiabilityCurrencyReport.setTotalLiability(totalLiability);
                selectionLiabilityCurrencyReports.add(selectionLiabilityCurrencyReport);
            });
        });
        return selectionLiabilityCurrencyReports;
    }

    @Override
    public String getTotalLiabilityByCurrencyReport() {
        return "success";
    }
}
