package com.ppower.reportgenerator;

import com.ppower.reportgenerator.domain.BetDetails;
import com.ppower.reportgenerator.domain.SelectionLiabilityCurrencyReport;
import com.ppower.reportgenerator.service.ReportService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class ReportgeneratorApplicationTests {

	@Autowired
	ReportService reportService;

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
				.stake(1l)
				.price(9l)
				.currency("euro")
				.build();

		BetDetails betDetails1 = BetDetails.builder()
				.betId("2")
				.betTimestamp("1234567")
				.selectionId("1")
				.selectionName("puthussery")
				.stake(2l)
				.price(5l)
				.currency("dollar")
				.build();

		List<BetDetails> betDetailsList = new ArrayList<>(Arrays.asList(betDetails,betDetails1));

		List<SelectionLiabilityCurrencyReport> selectionLiabilityCurrencyReports = reportService
				.getSelectionLiabilityByCurrencyReport(betDetailsList).stream()
				.filter(f->f.getSelectionName().equalsIgnoreCase("puthussery")).collect(Collectors.toList());
		Assertions.assertEquals(2,selectionLiabilityCurrencyReports.size());
		Assertions.assertEquals(9,selectionLiabilityCurrencyReports.stream()
				//.filter(f->f.getSelectionName().equalsIgnoreCase("puthussery"))
				.filter(e->e.getCurrency().equalsIgnoreCase("euro"))
				.collect(Collectors.toList()).get(0).getTotalLiability());
		Assertions.assertEquals(1,selectionLiabilityCurrencyReports.stream()
				//.filter(f->f.getSelectionName().equalsIgnoreCase("puthussery"))
				.filter(e->e.getCurrency().equalsIgnoreCase("euro"))
				.collect(Collectors.toList()).get(0).getTotalStakes());
		Assertions.assertEquals(10,selectionLiabilityCurrencyReports.stream()
				//.filter(f->f.getSelectionName().equalsIgnoreCase("puthussery"))
				.filter(e->e.getCurrency().equalsIgnoreCase("dollar"))
				.collect(Collectors.toList()).get(0).getTotalLiability());
		Assertions.assertEquals(2,selectionLiabilityCurrencyReports.stream()
				//.filter(f->f.getSelectionName().equalsIgnoreCase("puthussery"))
				.filter(e->e.getCurrency().equalsIgnoreCase("dollar"))
				.collect(Collectors.toList()).get(0).getTotalStakes());
	}

	@Test
	void testCsvReader(){
		String file = "bet_details.csv";
		reportService.getBetDetailsFromCSV(file);
	}

}
