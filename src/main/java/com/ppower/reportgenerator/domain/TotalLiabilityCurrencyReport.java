package com.ppower.reportgenerator.domain;

import lombok.*;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class TotalLiabilityCurrencyReport {
    private String Currency;
    private int numOfBets;
    private float totalStakes;
    private float totalLiability;
}
