package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Point {
    private double xVal;
    private double yVal;
    private double rVal;
    private long timezone;
}
