package com.ppower.reportgenerator.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

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

    @Autowired
    private Environment env;

    @Override
    public String toString(){
        String currencyIndi = env.getProperty(currency);
        return selectionName +" "+currency+" "+numOfBets+" "+currencyIndi+totalStakes+" "+currencyIndi+totalLiability;
    }
}
