package com.ppower.reportgenerator.service.impl;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.ppower.reportgenerator.boundary.BetDetailData;
import com.ppower.reportgenerator.boundary.ReportInputObject;
import com.ppower.reportgenerator.boundary.ReportResponseData;
import com.ppower.reportgenerator.domain.BetDetails;
import com.ppower.reportgenerator.domain.GenericReport;
import com.ppower.reportgenerator.domain.SelectionLiabilityCurrencyReport;
import com.ppower.reportgenerator.domain.TotalLiabilityCurrencyReport;
import com.ppower.reportgenerator.service.CSVManipulatorService;
import com.ppower.reportgenerator.service.HTTPService;
import com.ppower.reportgenerator.service.ReportComparatorService;
import com.ppower.reportgenerator.service.ReportGeneratorService;
import com.ppower.reportgenerator.utils.ReportComparatorFactory;
import com.ppower.reportgenerator.utils.ReportUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportGeneratorServiceImpl implements ReportGeneratorService {

    @Autowired
    CSVManipulatorService csvManipulatorService;

    @Autowired
    ReportUtils reportUtils;

    @Autowired
    HTTPService httpService;

    @Override
    public ReportResponseData generateReport(ReportInputObject reportInputObject){
        ReportResponseData reportResponseData = null;

        try {

            //Validate Input parameters
            reportUtils.sanitizeInput(reportInputObject);

            //read input file
            BetDetailData betDetailData = reportInputObject.getInputFormat().equalsIgnoreCase("CSV")
                    ? csvManipulatorService.readBetDetailsFromCSV(reportInputObject.getInputFile())
                    : httpService.getBetDetailsOverHttp(reportInputObject.getInputFile());

            if(!Objects.isNull(betDetailData)) {

                List<BetDetails> betDetailsList = betDetailData.getBetDetailsList();

                //generate report
                List < ? extends GenericReport > reportList = reportInputObject.getReportType().equalsIgnoreCase("SLCReport")
                        ? getSelectionLiabilityByCurrencyReport(betDetailsList) : getTotalLiabilityByCurrencyReport(betDetailsList);

                //Export report to the selected Output Format
                String outputLocation = "Output printed in CONSOLE";
                if(reportInputObject.getOutputFormat().equalsIgnoreCase("CSV")){
                    outputLocation = csvManipulatorService.exportToCSV(reportList, reportInputObject.getOutputFile(),
                            reportInputObject.getReportType());
                } else {
                    //Print Report in Console
                    System.out.println(reportUtils.getReportHeader(reportInputObject.getReportType()));
                    reportList.forEach(e -> System.out.println(e.toString()));
                }

                //Build API response
                reportResponseData = ReportResponseData.builder()
                        .reportDataList(reportList)
                        .outputCSVFilePath(outputLocation)
                        .build();

            }

        } catch (IOException e) {
           throw new RuntimeException(e.getMessage(),e.getCause());
        } catch (CsvDataTypeMismatchException e) {
            throw new RuntimeException(e.getMessage(),e.getCause());
        } catch (CsvRequiredFieldEmptyException e) {
            throw new RuntimeException(e.getMessage(),e.getCause());
        }
        return reportResponseData;
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
                Float totalStake=0F;
                Float totalLiability=0F;
                int numOfBets = 0;
                for(BetDetails data:keySet.getValue()){
                   totalStake = totalStake + data.getStake();
                   totalLiability = totalLiability + (data.getStake()*data.getPrice());
                   numOfBets ++;
                }
                selectionLiabilityCurrencyReport.setTotalStakes(reportUtils.formatAmount(totalStake));
                selectionLiabilityCurrencyReport.setTotalLiability(reportUtils.formatAmount(totalLiability));
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
            Float totalStake=0F;
            Float totalLiability=0F;
            int numOfBets=0;
            for(BetDetails data:entry.getValue()){
                totalStake = totalStake + data.getStake();
                totalLiability = totalLiability + (data.getStake()*data.getPrice());
                numOfBets ++;
            }
            totalLiabilityCurrencyReport.setTotalStakes(reportUtils.formatAmount(totalStake));
            totalLiabilityCurrencyReport.setTotalLiability(reportUtils.formatAmount(totalLiability));
            totalLiabilityCurrencyReport.setNumOfBets(numOfBets);
            totalLiabilityCurrencyReportList.add(totalLiabilityCurrencyReport);
        });
        Collections.sort(totalLiabilityCurrencyReportList,getComparator("TLCR"));
        return totalLiabilityCurrencyReportList;

    }

    private ReportComparatorService getComparator(String type){
        return ReportComparatorFactory.getInstance(type);
    }
}
