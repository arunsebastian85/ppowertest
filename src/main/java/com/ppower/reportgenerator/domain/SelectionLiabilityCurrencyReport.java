package com.ppower.reportgenerator.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SelectionLiabilityCurrencyReport {
    private String selectionName;
    private String currency;
    private int numOfBets;
    private float totalStakes;
    private float totalLiability;
}
