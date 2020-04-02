package com.ppower.reportgenerator.service.impl;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.ppower.reportgenerator.domain.BetDetails;
import com.ppower.reportgenerator.domain.SelectionLiabilityCurrencyReport;
import com.ppower.reportgenerator.service.ReportService;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
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

    @Override
    public List<BetDetails> getBetDetailsFromCSV(String filePath) {
        try {
            File file = ResourceUtils.getFile("classpath:"+filePath);
            Reader reader = new FileReader(file);
            CsvToBean<BetDetails> csvToBean = new CsvToBeanBuilder<BetDetails>(reader)
                    .withType(BetDetails.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            Iterator<BetDetails> iterator = csvToBean.iterator();
            while(iterator.hasNext()){
                BetDetails betDetails = iterator.next();
                System.out.println(betDetails.getBetId());
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
        return null;
    }
}
