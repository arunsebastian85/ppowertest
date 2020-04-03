package com.ppower.reportgenerator.service.impl;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.ppower.reportgenerator.domain.BetDetails;
import com.ppower.reportgenerator.domain.SelectionLiabilityCurrencyReport;
import com.ppower.reportgenerator.domain.TotalLiabilityCurrencyReport;
import com.ppower.reportgenerator.service.ReportService;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Override
    public  List<SelectionLiabilityCurrencyReport> getSelectionLiabilityByCurrencyReport(List<BetDetails> betDetailsList) {

        Map<String, Map<String,List<BetDetails>>> betDetailsGrouped= betDetailsList.stream().collect(Collectors
                .groupingBy(e->e.getSelectionName(),
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
                int numOfBets = 0;
                for(BetDetails data:keySet.getValue()){
                   totalStake = totalStake + data.getStake();
                   totalLiability = totalLiability + (data.getStake()*data.getPrice());
                   numOfBets ++;
                }
                selectionLiabilityCurrencyReport.setTotalStakes(totalStake);
                selectionLiabilityCurrencyReport.setTotalLiability(totalLiability);
                selectionLiabilityCurrencyReport.setNumOfBets(numOfBets);
                selectionLiabilityCurrencyReports.add(selectionLiabilityCurrencyReport);
            });
        });
        return selectionLiabilityCurrencyReports;
    }

    @Override
    public List<TotalLiabilityCurrencyReport>   getTotalLiabilityByCurrencyReport(List<BetDetails> betDetailsList) {

        Map<String,List<BetDetails>> groupedByCurrency = betDetailsList.stream().collect(Collectors
                .groupingBy(BetDetails::getCurrency));
        List<TotalLiabilityCurrencyReport> totalLiabilityCurrencyReportList = new ArrayList<>();
        groupedByCurrency.entrySet().forEach(entry -> {
            TotalLiabilityCurrencyReport totalLiabilityCurrencyReport = TotalLiabilityCurrencyReport.builder()
                    .Currency(entry.getKey())
                    .build();
            float totalStake=0L;
            float totalLiability=0L;
            int numOfBets=0;
            for(BetDetails data:entry.getValue()){
                totalStake = totalStake + data.getStake();
                totalLiability = totalLiability + (data.getStake()*data.getPrice());
                numOfBets ++;
            }
            totalLiabilityCurrencyReport.setTotalStakes(totalStake);
            totalLiabilityCurrencyReport.setTotalLiability(totalLiability);
            totalLiabilityCurrencyReport.setNumOfBets(numOfBets);
            totalLiabilityCurrencyReportList.add(totalLiabilityCurrencyReport);
        });
        return totalLiabilityCurrencyReportList;

    }

    @Override
    public List<BetDetails> getBetDetailsFromCSV(String filePath) {
        List<BetDetails> betDetailsList = new ArrayList<>();
        try {
            File file = ResourceUtils.getFile("classpath:"+filePath);
            Reader reader = new FileReader(file);
            CsvToBean<BetDetails> csvToBean = new CsvToBeanBuilder<BetDetails>(reader)
                    .withType(BetDetails.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            betDetailsList = csvToBean.parse();
            betDetailsList.forEach(e->{
                System.out.println("-------"+e.toString());
            });
            List<SelectionLiabilityCurrencyReport> list = getSelectionLiabilityByCurrencyReport(betDetailsList);
            System.out.println("selectionName  currency numOfBets totalStakes totalLiability");
            list.forEach(e->{
                System.out.println(e.toString());
            });
        } catch (IOException ex){
            ex.printStackTrace();
        }
        return betDetailsList;
    }
}
