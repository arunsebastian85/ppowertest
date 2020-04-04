package com.ppower.reportgenerator.service;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.ppower.reportgenerator.domain.BetDetails;
import com.ppower.reportgenerator.domain.GenericReport;

import java.io.IOException;
import java.util.List;

public interface CSVManipulatorService {
    List<BetDetails> readBetDetailsFromCSV(String filePath);
    String exportToCSV(List<? extends GenericReport> reportDataList, String outputFile,String type)
            throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException;
}
