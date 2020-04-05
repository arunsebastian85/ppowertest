package com.ppower.reportgenerator.service;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.ppower.reportgenerator.boundary.BetDetailData;
import com.ppower.reportgenerator.domain.GenericReport;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface CSVManipulatorService {
    BetDetailData readBetDetailsFromCSV(String filePath) throws FileNotFoundException;
    String exportToCSV(List<? extends GenericReport> reportDataList, String outputFile,String type)
            throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException;
}
