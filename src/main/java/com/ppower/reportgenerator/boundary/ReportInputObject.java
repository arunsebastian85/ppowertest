package com.ppower.reportgenerator.boundary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportInputObject {
    private String reportType;
    private String outputFormat;
    private String inputFormat;
    private String inputFile;
    private String outputFile;
}
