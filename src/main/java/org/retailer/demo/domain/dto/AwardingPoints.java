package org.retailer.demo.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AwardingPoints {
    @Schema(name = "Date of the points", example = "January")
    private String date;
    @Schema(name = "points earned", example = "60")
    private long points;
}
