package org.retailer.demo.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PointsByMonth {
    @Schema(name = "Customer name", example = "John Doe")
    private String customerName;
    @Schema(name = "List of Awarded points")
    private List<AwardingPoints> awardedPoints = new ArrayList<>();

    public Long getTotalPoints() {
        return this.awardedPoints.stream().reduce(0L, (total, points) -> total += points.getPoints(), Long::sum);
    }
}
