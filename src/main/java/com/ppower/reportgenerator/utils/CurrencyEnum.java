package com.ppower.reportgenerator.utils;

public enum CurrencyEnum {

    EUR("€"), GBP("£");

    private final String currencyIndicator;

    CurrencyEnum(String value) {
        this.currencyIndicator = value;
    }

    public String getValue(){
        return currencyIndicator;
    }

}
