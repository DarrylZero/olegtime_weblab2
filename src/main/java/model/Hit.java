package model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Hit {
    private final double xVal;
    private final double yVal;
    private final double rVal;
    private final LocalDateTime currentTime;
    private final long executionTime;
    private final boolean isHit;
}
