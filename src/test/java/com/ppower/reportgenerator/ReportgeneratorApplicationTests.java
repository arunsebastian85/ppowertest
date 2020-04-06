package com.ppower.reportgenerator;

import com.ppower.reportgenerator.boundary.BetDetailData;
import com.ppower.reportgenerator.boundary.ReportInputObject;
import com.ppower.reportgenerator.boundary.ReportResponseData;
import com.ppower.reportgenerator.domain.BetDetails;
import com.ppower.reportgenerator.domain.ReportResponse;
import com.ppower.reportgenerator.domain.SelectionLiabilityCurrencyReport;
import com.ppower.reportgenerator.service.HTTPService;
import com.ppower.reportgenerator.service.ReportGeneratorService;
import com.ppower.reportgenerator.service.impl.CSVManipulatorServiceImpl;
import com.ppower.reportgenerator.service.impl.HTTPServiceImpl;
import com.ppower.reportgenerator.service.impl.ReportGeneratorServiceImpl;
import com.ppower.reportgenerator.utils.ReportUtils;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes ={HTTPServiceTestConfig.class, ReportGeneratorServiceImpl.class})
@SpringBootApplication()
class ReportgeneratorApplicationTests {

	@Autowired
	ReportGeneratorService reportGeneratorService;

	@Autowired
	HTTPService httpService;

	@Test
	void contextLoads() {
	}


	@Test
	void testGetSelectionLiabilityByCurrencyReport(){
		BetDetails betDetails = BetDetails.builder()
				.betId("1")
				.betTimestamp("1234567")
				.selectionId("1")
				.selectionName("Simon")
				.stake(1F)
				.price(9F)
				.currency("eur")
				.build();

		BetDetails betDetails1 = BetDetails.builder()
				.betId("2")
				.betTimestamp("1234567")
				.selectionId("1")
				.selectionName("Simon")
				.stake(2F)
				.price(5F)
				.currency("gbp")
				.build();

		List<BetDetails> betDetailsList = new ArrayList<>(Arrays.asList(betDetails,betDetails1));

		List<SelectionLiabilityCurrencyReport> selectionLiabilityCurrencyReports = reportGeneratorService
				.getSelectionLiabilityByCurrencyReport(betDetailsList).stream()
				.filter(f->f.getSelectionName().equalsIgnoreCase("simon")).collect(Collectors.toList());
		Assertions.assertEquals(2,selectionLiabilityCurrencyReports.size());
		Assertions.assertEquals("€9.00",selectionLiabilityCurrencyReports.stream()
				.filter(e->e.getCurrency().equalsIgnoreCase("eur"))
				.collect(Collectors.toList()).get(0).getTotalLiability());
		Assertions.assertEquals("€1.00",selectionLiabilityCurrencyReports.stream()
				.filter(e->e.getCurrency().equalsIgnoreCase("eur"))
				.collect(Collectors.toList()).get(0).getTotalStakes());
		Assertions.assertEquals("£10.00",selectionLiabilityCurrencyReports.stream()
				.filter(e->e.getCurrency().equalsIgnoreCase("gbp"))
				.collect(Collectors.toList()).get(0).getTotalLiability());
		Assertions.assertEquals("£2.00",selectionLiabilityCurrencyReports.stream()
				.filter(e->e.getCurrency().equalsIgnoreCase("gbp"))
				.collect(Collectors.toList()).get(0).getTotalStakes());
	}

	@Test
	public void testCsvReaderSLCReportCSV(){
		String file = "bet_details.csv";
		String outputFile = "bet_details_out_slcreport.csv";
		ReportInputObject reportInputObject = ReportInputObject.builder()
				.inputFile(file)
				.inputFormat("csv")
				.outputFile(outputFile)
				.outputFormat("csv")
				.reportType("SLCReport")
				.build();
		ReportResponseData reportResponseData = reportGeneratorService.generateReport(reportInputObject);
		assertNotNull(reportResponseData.getReportDataList());
		assertNotEquals("Output printed in CONSOLE",reportResponseData.getOutputCSVFilePath());
	}

	@Test
	public void testCsvReaderSLCReportConsole(){
		String file = "bet_details.csv";
		String outputFile = "bet_details_out_slcreport.csv";
		ReportInputObject reportInputObject = ReportInputObject.builder()
				.inputFile(file)
				.inputFormat("csv")
				.outputFile(outputFile)
				.outputFormat("console")
				.reportType("SLCReport")
				.build();
		ReportResponseData reportResponseData = reportGeneratorService.generateReport(reportInputObject);
		assertNotNull(reportResponseData.getReportDataList());
		assertEquals("Output printed in CONSOLE", reportResponseData.getOutputCSVFilePath());
	}

	@Test
	public void testCsvReaderTLCReport(){
		String file = "bet_details.csv";
		String outputFile = "bet_details_out_tlcreport.csv";
		ReportInputObject reportInputObject = ReportInputObject.builder()
				.inputFile(file)
				.inputFormat("csv")
				.outputFile(outputFile)
				.outputFormat("csv")
				.reportType("TLCReport")
				.build();
		ReportResponseData reportResponseData = reportGeneratorService.generateReport(reportInputObject);
	}

	@Ignore
	public void testCsvReaderTLCReporHTTP(){
		String outputFile = "bet_details_out_tlcreport.csv";
		ReportInputObject reportInputObject = ReportInputObject.builder()
				//.inputFile(file.toUpperCase())
				.inputFormat("json")
				.outputFile(outputFile)
				.outputFormat("csv")
				.reportType("TLCReport")
				.build();
		BetDetails betDetails = BetDetails.builder()
				.betId("Bet-10")
				.betTimestamp("1234455")
				.currency("GBP")
				.price(4.5F)
				.stake(9.0F)
				.selectionId("1")
				.selectionName("My Fair Lady")
				.build();
		BetDetailData betDetailData = BetDetailData.builder()
				.betDetailsList(Arrays.asList(betDetails))
				.build();
		Mockito.when(httpService.getBetDetailsOverHttp(Mockito.anyString())).thenReturn(betDetailData);
		ReportResponseData reportResponseData = reportGeneratorService.generateReport(reportInputObject);
		assertNotNull(reportResponseData);
	}

}
