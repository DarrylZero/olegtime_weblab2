package model;

import exceptions.InvalidParameterException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointHandlerTest {
    private final PointHandler pointHandler = new PointHandler();

    @Test
    void getHitInfoWithValidData() {
        Point point = new Point(1, 2, 3, -60);

        Hit hit = pointHandler.getHitInfo(point);

        assertEquals(hit.getXVal(), 1);
        assertEquals(hit.getYVal(), 2);
        assertEquals(hit.getRVal(), 3);
        assertFalse(hit.isHit());
    }

    @Test
    void getHitInfoWithIncorrectY() {
        Point point = new Point(1, 6, 3, -60);

        assertThrows(InvalidParameterException.class,
                () -> pointHandler.getHitInfo(point),
                "Значение Y не попадает в нужный интервал!");
    }

    @Test
    void getHitInfoWithIncorrectR() {
        Point point = new Point(1, 3, -3, -60);

        assertThrows(InvalidParameterException.class,
                () -> pointHandler.getHitInfo(point),
                "Значение R не может быть неположительным!");
    }

    @Test
    void getHitInfoWithHitToRectangle() {
        Point point = new Point(-1, 1, 3, -60);

        Hit hit = pointHandler.getHitInfo(point);

        assertTrue(hit.isHit());
    }

    @Test
    void getHitInfoWithHitToCircle() {
        Point point = new Point(1, 1, 3, -60);

        Hit hit = pointHandler.getHitInfo(point);

        assertTrue(hit.isHit());
    }

    @Test
    void getHitInfoWithHitToTriangle() {
        Point point = new Point(1, -1, 2, -60);

        Hit hit = pointHandler.getHitInfo(point);

        assertTrue(hit.isHit());
    }

    @Test
    void getHitInfoWithHitToCenter() {
        Point point = new Point(0, 0, 3, -60);

        Hit hit = pointHandler.getHitInfo(point);

        assertTrue(hit.isHit());
    }

    @Test
    void getHitInfoWithMiss() {
        Point point = new Point(-1, -1, 3, -60);

        Hit hit = pointHandler.getHitInfo(point);

        assertFalse(hit.isHit());
    }
}