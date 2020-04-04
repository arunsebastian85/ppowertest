package com.ppower.reportgenerator.service.impl;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.ppower.reportgenerator.domain.BetDetails;
import com.ppower.reportgenerator.domain.GenericReport;
import com.ppower.reportgenerator.domain.SelectionLiabilityCurrencyReport;
import com.ppower.reportgenerator.domain.TotalLiabilityCurrencyReport;
import com.ppower.reportgenerator.service.CSVManipulatorService;
import com.ppower.reportgenerator.service.ReportComparatorService;
import com.ppower.reportgenerator.service.ReportGeneratorService;
import com.ppower.reportgenerator.utils.ReportComparatorFactory;
import com.ppower.reportgenerator.utils.ReportUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportGeneratorServiceImpl implements ReportGeneratorService {

    @Autowired
    CSVManipulatorService csvManipulatorService;

    @Autowired
    ReportUtils reportUtils;

    @Override
    public List<? extends GenericReport> generateReport(String reportType, String outputFormat, String inputFormat
            , String inputFile, String outputFile){

        try {
            //read input file
            List<BetDetails> betDetailsList = csvManipulatorService.readBetDetailsFromCSV(inputFile);

            //generate report
            List<? extends GenericReport> reportList = reportType.equalsIgnoreCase("SLCReport")
                    ? getSelectionLiabilityByCurrencyReport(betDetailsList) : getTotalLiabilityByCurrencyReport(betDetailsList);

            //Print Report in Console
            System.out.println(reportUtils.getReportHeader(reportType));
            reportList.forEach(e -> System.out.println(e.toString()));

            String outputLocation = csvManipulatorService.exportToCSV(reportList,outputFile,reportType);

            return reportList;

        } catch (IOException e) {
           throw new RuntimeException(e.getMessage());
        } catch (CsvDataTypeMismatchException e) {
            throw new RuntimeException(e.getMessage());
        } catch (CsvRequiredFieldEmptyException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public  List<SelectionLiabilityCurrencyReport> getSelectionLiabilityByCurrencyReport(List<BetDetails> betDetailsList) {

        Map<String, Map<String,List<BetDetails>>> betDetailsGrouped= betDetailsList.stream().collect(Collectors
                .groupingBy(e->e.getSelectionName(),
                Collectors.groupingBy(BetDetails::getCurrency)));
        List<SelectionLiabilityCurrencyReport> selectionLiabilityCurrencyReportList = new ArrayList<>();
        betDetailsGrouped.entrySet().forEach(entry -> {
            entry.getValue().entrySet().forEach(keySet->{
                SelectionLiabilityCurrencyReport selectionLiabilityCurrencyReport = new SelectionLiabilityCurrencyReport();
                selectionLiabilityCurrencyReport.setSelectionName(entry.getKey());
                selectionLiabilityCurrencyReport.setCurrency(keySet.getKey());
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
                selectionLiabilityCurrencyReportList.add(selectionLiabilityCurrencyReport);
            });
        });
        Collections.sort(selectionLiabilityCurrencyReportList,getComparator("SLCR"));
        return selectionLiabilityCurrencyReportList;

    }

    @Override
    public List<TotalLiabilityCurrencyReport> getTotalLiabilityByCurrencyReport(List<BetDetails> betDetailsList) {

        Map<String,List<BetDetails>> groupedByCurrency = betDetailsList.stream().collect(Collectors
                .groupingBy(BetDetails::getCurrency));
        List<TotalLiabilityCurrencyReport> totalLiabilityCurrencyReportList = new ArrayList<>();
        groupedByCurrency.entrySet().forEach(entry -> {
            TotalLiabilityCurrencyReport totalLiabilityCurrencyReport = new TotalLiabilityCurrencyReport();
            totalLiabilityCurrencyReport.setCurrency(entry.getKey());
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
        Collections.sort(totalLiabilityCurrencyReportList,getComparator("TLCR"));
        return totalLiabilityCurrencyReportList;

    }


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

    private ReportComparatorService getComparator(String type){
        return ReportComparatorFactory.getInstance(type);
    }
}
