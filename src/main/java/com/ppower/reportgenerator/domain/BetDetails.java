package com.ppower.reportgenerator.domain;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class BetDetails {
    private String betId;
    private String betTimestamp;
    private String selectionId;
    private String selectionName;
    private float stake;
    private float price;
    private String currency;
}
