package model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Hit {
    private double xVal;
    private double yVal;
    private double rVal;
    private LocalDateTime currentTime;
    private long executionTime;
    private boolean isHit;
}
