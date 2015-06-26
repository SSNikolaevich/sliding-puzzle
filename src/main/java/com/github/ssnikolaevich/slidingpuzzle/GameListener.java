package com.github.ssnikolaevich.slidingpuzzle;

import java.util.EventListener;

public interface GameListener extends EventListener {
    void handle(GameEvent event);
}
