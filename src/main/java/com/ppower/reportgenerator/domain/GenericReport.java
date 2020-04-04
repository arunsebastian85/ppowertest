package com.ppower.reportgenerator.domain;

import com.opencsv.bean.CsvBindByName;

public class GenericReport {

    @CsvBindByName(column = "Currency", required = true)
    private String currency;
    @CsvBindByName(column = "Num Of Bets", required = true)
    private int numOfBets;
    @CsvBindByName(column = "Total Stakes", required = true)
    private Float totalStakes;
    @CsvBindByName(column = "Total Liability", required = true)
    private Float totalLiability;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) { this.currency = currency; }

    public void setNumOfBets(int numOfBets) {
        this.numOfBets = numOfBets;
    }

    public Float getTotalStakes() {
        return totalStakes;
    }

    public void setTotalStakes(Float totalStakes) {
        this.totalStakes = totalStakes;
    }

    public Float getTotalLiability() {
        return totalLiability;
    }

    public void setTotalLiability(Float totalLiability) {
        this.totalLiability = totalLiability;
    }

    @Override
    public String toString(){
        return currency+" "+numOfBets+" "+wrapCurrency(currency,totalStakes)+" "+wrapCurrency(currency,totalLiability);
    }

    private String wrapCurrency(String currencyVal, Float value){
        String currency = currencyVal.trim().equalsIgnoreCase("EUR") ? "€" : "£";
        return currency+String.format("%.2f", value);
    }

}
