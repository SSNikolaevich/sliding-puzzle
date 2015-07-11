package com.github.ssnikolaevich.slidingpuzzle;

public class LevelStateManager {
    private final static int DEFAULT_UNLOCKED_LEVELS_LIMIT = 3;

    private boolean[] levelState;
    private int unlockedLevelsLimit;

    public LevelStateManager(int levelsCount) {
        levelState = new boolean[levelsCount];
        for (int i = 0; i < levelsCount; ++i)
            levelState[i] = false;
        unlockedLevelsLimit = DEFAULT_UNLOCKED_LEVELS_LIMIT;
    }

    public int getLevelsCount() {
        return levelState.length;
    }

    public int getUnlockedLevelsLimit() {
        return unlockedLevelsLimit;
    }

    public  void setUnlockedLevelsLimit(int value) {
        unlockedLevelsLimit = value;
    }

    public void setSolved(int level, boolean value) {
        levelState[level] = value;
    }

    public boolean isSolved(int level) {
        return levelState[level];
    }

    public boolean isLocked(int level) {
        if (isSolved(level))
            return false;
        int c = 0;
        for (int i = 0; i < level; ++i) {
            if (!isSolved(i)) {
                ++c;
            }
        }
        return c >= unlockedLevelsLimit;
    }
}
