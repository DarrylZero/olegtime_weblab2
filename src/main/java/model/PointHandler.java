package model;

import exceptions.InvalidParameterException;

import java.time.ZonedDateTime;
import java.util.Date;

import static java.lang.Math.pow;

public class PointHandler {

    public Hit getHitInfo(Point point) {
        Date startExecution = new Date();
        validatePoint(point);
        return Hit.builder()
                .xVal(point.getXVal())
                .yVal(point.getYVal())
                .rVal(point.getRVal())
                .currentTime(ZonedDateTime.now().minusMinutes(point.getTimezone()).toLocalDateTime())
                .isHit(isHit(point))
                .executionTime(new Date().getTime() - startExecution.getTime())
                .build();
    }

    private void validatePoint(Point point) {
        byte MAX_Y = 5;
        byte MIN_Y = -5;

        if (!(point.getYVal() > MIN_Y && point.getYVal() < MAX_Y)) {
            throw new InvalidParameterException("Значение Y не попадает в нужный интервал!");
        }

        if (point.getRVal() <= 0) {
            throw new InvalidParameterException("Значение R не может быть неположительным!");
        }
    }

    private boolean isHit(Point point) {
        return isRectangleHit(point) || isCircleHit(point) || isTriangleHit(point);
    }

    private boolean isRectangleHit(Point point) {
        return point.getXVal() <= 0 && point.getXVal() >= -point.getRVal()
                && point.getYVal() <= 0 && point.getYVal() >= -point.getRVal();
    }

    private boolean isCircleHit(Point point) {
        return point.getXVal() >= 0 && point.getYVal() >= 0
                && (pow(point.getXVal(), 2) + pow(point.getYVal(), 2)) <= pow(point.getRVal() / 2, 2);
    }

    private boolean isTriangleHit(Point point) {
        return point.getXVal() >= 0 && point.getYVal() <= 0
                && (2 * point.getXVal() - point.getRVal()) <= 2 * point.getYVal();
    }

}
