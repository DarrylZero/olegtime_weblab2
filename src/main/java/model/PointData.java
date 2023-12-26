package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PointData {
    private final Point point;
    private final double rVal;
    private final long timezone;
}
