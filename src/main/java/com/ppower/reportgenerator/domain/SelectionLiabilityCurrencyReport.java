package com.ppower.reportgenerator.domain;

import com.opencsv.bean.CsvBindByName;

public class SelectionLiabilityCurrencyReport extends GenericReport{

    @CsvBindByName(column = "Selection", required = true)
    private String selectionName;

    public String getSelectionName() {
        return selectionName.trim();
    }

    public void setSelectionName(String selectionName) {
        this.selectionName = selectionName;
    }

    @Override
    public String toString(){
        return selectionName +" | "+super.toString();
    }


}
