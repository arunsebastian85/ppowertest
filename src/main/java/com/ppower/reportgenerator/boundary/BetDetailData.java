package com.ppower.reportgenerator.boundary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ppower.reportgenerator.domain.BetDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BetDetailData {
    @JsonProperty("betDetails")
    private List<BetDetails> betDetailsList;
}
