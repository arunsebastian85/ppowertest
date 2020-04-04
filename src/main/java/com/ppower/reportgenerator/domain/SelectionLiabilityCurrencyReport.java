package com.ppower.reportgenerator.domain;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.ppower.reportgenerator.config.PropertyConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

public class SelectionLiabilityCurrencyReport extends GenericReport{

    @CsvBindByName(column = "Selection", required = true)
    //@CsvBindByPosition(position=1)
    private String selectionName;

    public String getSelectionName() {
        return selectionName;
    }

    public void setSelectionName(String selectionName) {
        this.selectionName = selectionName;
    }

    @Override
    public String toString(){
        return selectionName +" "+super.toString();
    }


}
