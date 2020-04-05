package com.ppower.reportgenerator;

import com.ppower.reportgenerator.domain.BetDetails;
import com.ppower.reportgenerator.domain.SelectionLiabilityCurrencyReport;
import com.ppower.reportgenerator.service.ReportGeneratorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class ReportgeneratorApplicationTests {

	@Autowired
	ReportGeneratorService reportGeneratorService;

	@Test
	void contextLoads() {
	}


	@Test
	void testGetSelectionLiabilityByCurrencyReport(){
		BetDetails betDetails = BetDetails.builder()
				.betId("1")
				.betTimestamp("1234567")
				.selectionId("1")
				.selectionName("puthussery")
				.stake(1F)
				.price(9F)
				.currency("eur")
				.build();

		BetDetails betDetails1 = BetDetails.builder()
				.betId("2")
				.betTimestamp("1234567")
				.selectionId("1")
				.selectionName("puthussery")
				.stake(2F)
				.price(5F)
				.currency("gbp")
				.build();

		List<BetDetails> betDetailsList = new ArrayList<>(Arrays.asList(betDetails,betDetails1));

		List<SelectionLiabilityCurrencyReport> selectionLiabilityCurrencyReports = reportGeneratorService
				.getSelectionLiabilityByCurrencyReport(betDetailsList).stream()
				.filter(f->f.getSelectionName().equalsIgnoreCase("puthussery")).collect(Collectors.toList());
		Assertions.assertEquals(2,selectionLiabilityCurrencyReports.size());
		Assertions.assertEquals("€9.00",selectionLiabilityCurrencyReports.stream()
				//.filter(f->f.getSelectionName().equalsIgnoreCase("puthussery"))
				.filter(e->e.getCurrency().equalsIgnoreCase("eur"))
				.collect(Collectors.toList()).get(0).getTotalLiability());
		Assertions.assertEquals("€1.00",selectionLiabilityCurrencyReports.stream()
				//.filter(f->f.getSelectionName().equalsIgnoreCase("puthussery"))
				.filter(e->e.getCurrency().equalsIgnoreCase("eur"))
				.collect(Collectors.toList()).get(0).getTotalStakes());
		Assertions.assertEquals("£10.00",selectionLiabilityCurrencyReports.stream()
				//.filter(f->f.getSelectionName().equalsIgnoreCase("puthussery"))
				.filter(e->e.getCurrency().equalsIgnoreCase("gbp"))
				.collect(Collectors.toList()).get(0).getTotalLiability());
		Assertions.assertEquals("£2.00",selectionLiabilityCurrencyReports.stream()
				//.filter(f->f.getSelectionName().equalsIgnoreCase("puthussery"))
				.filter(e->e.getCurrency().equalsIgnoreCase("gbp"))
				.collect(Collectors.toList()).get(0).getTotalStakes());
	}

	@Test
	void testCsvReader(){
		String file = "bet_details.csv";
		String outputFile = "bet_details_out_2.csv";
		reportGeneratorService.generateReport("SLCReport","csv","CSV",file,outputFile);
	}

}
