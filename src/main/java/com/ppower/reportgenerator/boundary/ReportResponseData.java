package com.ppower.reportgenerator.boundary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ppower.reportgenerator.domain.GenericReport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponseData {
    @JsonProperty("reportItem")
    private List<? extends GenericReport> reportDataList;

    @JsonProperty("outputFilePathCSV")
    private String outputCSVFilePath;
}
