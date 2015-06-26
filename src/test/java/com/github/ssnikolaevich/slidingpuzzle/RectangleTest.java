/*
 * Copyright 2015 Sergey Skuratovich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
