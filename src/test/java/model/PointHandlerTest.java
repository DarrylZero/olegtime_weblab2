package model;

import exceptions.InvalidParameterException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class PointHandlerTest {
    private final PointHandler pointHandler = new PointHandler();

    @Test
    void getHitInfoWithValidData() {
        Hit hit = pointHandler.getHitInfo(pointData(1, 2, 3, -60));

        assertEquals(hit.getXVal(), 1);
        assertEquals(hit.getYVal(), 2);
        assertEquals(hit.getRVal(), 3);
        assertFalse(hit.isHit());
    }

    @Test
    void getHitInfoWithIncorrectY() {
        var point = pointData(1, 6, 3, -60);

        assertThrows(InvalidParameterException.class,
                () -> pointHandler.getHitInfo(point),
                "Значение Y не попадает в нужный интервал!");
    }

    @Test
    void getHitInfoWithIncorrectR() {
        var point = pointData(1, 3, -3, -60);

        assertThrows(InvalidParameterException.class,
                () -> pointHandler.getHitInfo(point),
                "Значение R не может быть неположительным!");
    }

    @Test
    void getHitInfoWithHitToRectangle() {
        assertTrue(pointHandler.getHitInfo(pointData(-1, 1, 3, -60)).isHit());
    }

   @Test
    void getHitInfoWithHitToCircle() {
       assertTrue(pointHandler.getHitInfo(pointData(1, 1, 3, -60)).isHit());
    }

    @Test
    void getHitInfoWithHitToTriangle() {
        assertTrue(pointHandler.getHitInfo(pointData(1, -1, 2, -60)).isHit());
    }

    @Test
    void getHitInfoWithHitToCenter() {
        assertTrue(pointHandler.getHitInfo(pointData(0, 0, 3, -60)).isHit());
    }

    @Test
    void getHitInfoWithMiss() {
        Hit hit = pointHandler.getHitInfo(pointData(-1, -1, 3, -60));

        assertFalse(hit.isHit());
    }

    private PointData pointData(double xVal, double yVal, double rVal, long timezone) {
        return new PointData(new Point(xVal, yVal), rVal, timezone);
    }
}