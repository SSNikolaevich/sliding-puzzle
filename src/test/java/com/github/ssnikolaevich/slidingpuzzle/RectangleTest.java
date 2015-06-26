package com.github.ssnikolaevich.slidingpuzzle;

import static org.junit.Assert.*;
import org.junit.Test;

public class RectangleTest {
    @Test
    public void testIntersectsWith() {
        Rectangle r = new Rectangle(1, 2, 3, 4);
        assertFalse(r.intersectsWith(new Rectangle(5, 6, 7, 8)));
        assertFalse(r.intersectsWith(new Rectangle(1, 6, 3, 8)));
        assertFalse(r.intersectsWith(new Rectangle(5, 2, 7, 4)));
        assertTrue(r.intersectsWith(new Rectangle(1, 4, 3, 6)));
        assertTrue(r.intersectsWith(new Rectangle(3, 2, 5, 4)));
        assertTrue(r.intersectsWith(new Rectangle(2, 3, 4, 5)));
        assertTrue(r.intersectsWith(new Rectangle(2, 1, 4, 3)));
    }
}
