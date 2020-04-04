package com.ppower.reportgenerator.service.impl;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.ppower.reportgenerator.domain.BetDetails;
import com.ppower.reportgenerator.domain.GenericReport;
import com.ppower.reportgenerator.service.CSVManipulatorService;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CSVManipulatorServiceImpl implements CSVManipulatorService {

    @Override
    public List<BetDetails> readBetDetailsFromCSV(String filePath) {
        List<BetDetails> betDetailsList = new ArrayList<>();
        try {
            File file = ResourceUtils.getFile("classpath:"+filePath);
            Reader reader = new FileReader(file);
            CsvToBean<BetDetails> csvToBean = new CsvToBeanBuilder<BetDetails>(reader)
                    .withType(BetDetails.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            betDetailsList = csvToBean.parse();
        } catch (IOException ex){
            ex.printStackTrace();
        }
        return betDetailsList;
    }

    @Override
    public String exportToCSV(List<? extends GenericReport> reportDataList, String outputFile, String type)
            throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        String userDir = System.getProperty("user.dir");
        File file = new File(userDir+File.separator+outputFile);
        if (!file.exists()) {
            file.createNewFile();
        }
        System.out.println(file.getAbsolutePath());
        Writer writer = new FileWriter(file);
        StatefulBeanToCsv statefulBeanToCsv = new StatefulBeanToCsvBuilder(writer)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .build();
        statefulBeanToCsv.write(reportDataList);
        return file.getAbsolutePath();
    }

}
