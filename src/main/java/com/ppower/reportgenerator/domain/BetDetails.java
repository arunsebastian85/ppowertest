package com.ppower.reportgenerator.domain;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class BetDetails {

    @CsvBindByName
    private String betId;
    @CsvBindByName
    private String betTimestamp;
    @CsvBindByName
    private String selectionId;
    @CsvBindByName
    private String selectionName;
    @CsvBindByName
    private float stake;
    @CsvBindByName
    private float price;
    @CsvBindByName
    private String currency;

}
