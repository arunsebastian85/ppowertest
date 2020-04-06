package com.ppower.reportgenerator.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencsv.bean.CsvBindByName;
import com.ppower.reportgenerator.utils.CurrencyEnum;

public class GenericReport {

    @CsvBindByName(column = "Currency", required = true)
    private String currency;
    @CsvBindByName(column = "Num Of Bets", required = true)
    private int numOfBets;
    @CsvBindByName(column = "Total Stakes", required = true)
    private String totalStakes;
    @CsvBindByName(column = "Total Liability", required = true)
    private String totalLiability;

    public String getCurrency() {
        return currency.trim();
    }

    public void setCurrency(String currency) { this.currency = currency; }

    public void setNumOfBets(int numOfBets) {
        this.numOfBets = numOfBets;
    }

    public int getNumOfBets() {
        return numOfBets;
    }
    public String getTotalStakes() {
        return wrapCurrency(currency,totalStakes);
    }

    public void setTotalStakes(String totalStakes) {
        this.totalStakes = totalStakes;
    }

    public String getTotalLiability() {
        return wrapCurrency(currency,totalLiability);
    }

    @JsonIgnore
    public Float getTotalLiabilityFloat() {
        return new Float(totalLiability);
    }

    public void setTotalLiability(String totalLiability) {
        this.totalLiability = totalLiability;
    }

    @Override
    public String toString(){
        return currency +" | "+ numOfBets +" | "+ getTotalStakes() +" | "+ getTotalLiability();
    }

    private String wrapCurrency(String currencyVal, String value){
        String currency = CurrencyEnum.valueOf(currencyVal.toUpperCase().trim()).getValue();
        //currencyVal.trim().equalsIgnoreCase("EUR") ? "€" : "£";
        return currency+value;
    }

}
