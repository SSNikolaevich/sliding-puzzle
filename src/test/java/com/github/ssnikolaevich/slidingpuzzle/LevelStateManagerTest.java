package com.github.ssnikolaevich.slidingpuzzle;

import static org.junit.Assert.*;

import org.junit.Test;

public class LevelStateManagerTest {
    @Test
    public void testGetLevelsCount() {
        LevelStateManager manager = new LevelStateManager(5);
        assertEquals(5, manager.getLevelsCount());
    }

    @Test
    public void testUnlockedLevelsLimit() {
        LevelStateManager manager = new LevelStateManager(10);
        manager.setUnlockedLevelsLimit(4);
        assertEquals(4, manager.getUnlockedLevelsLimit());
        manager.setUnlockedLevelsLimit(6);
        assertEquals(6, manager.getUnlockedLevelsLimit());
    }

    @Test
    public void testInitialState() {
        LevelStateManager manager = new LevelStateManager(5);
        for (int i = 0; i < manager.getLevelsCount(); ++i)
            assertFalse(manager.isSolved(i));
    }

    @Test
    public void testInitialUnlockedLevelsLimit() {
        LevelStateManager manager = new LevelStateManager(5);
        assertEquals(3, manager.getUnlockedLevelsLimit());
    }

    @Test
    public void testSetSolved() {
        LevelStateManager manager = new LevelStateManager(5);
        manager.setSolved(2, true);
        assertTrue(manager.isSolved(2));
        manager.setSolved(2, false);
    }

    @Test
    public void testIsLocked() {
        LevelStateManager manager = new LevelStateManager(7);
        manager.setUnlockedLevelsLimit(3);
        manager.setSolved(1, true);
        manager.setSolved(3, true);

        assertFalse(manager.isLocked(0));
        assertFalse(manager.isLocked(1));
        assertFalse(manager.isLocked(2));
        assertFalse(manager.isLocked(3));
        assertFalse(manager.isLocked(4));
        assertTrue(manager.isLocked(5));
        assertTrue(manager.isLocked(6));
    }
}
