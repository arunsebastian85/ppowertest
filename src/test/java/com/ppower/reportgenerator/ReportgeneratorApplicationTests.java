package com.ppower.reportgenerator;

import com.ppower.reportgenerator.domain.BetDetails;
import com.ppower.reportgenerator.service.ReportService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
				.price(1l)
				.currency("euro")
				.build();

		BetDetails betDetails1 = BetDetails.builder()
				.betId("2")
				.betTimestamp("1234567")
				.selectionId("1")
				.selectionName("puthussery")
				.stake(2l)
				.price(3l)
				.currency("euro")
				.build();

		List<BetDetails> betDetailsList = new ArrayList<>(Arrays.asList(betDetails,betDetails1));

		Assertions.assertEquals("success",reportService.getSelectionLiabilityByCurrencyReport(betDetailsList));
	}

}
