package com.study.til.singleton._03_doublecheck_locking;

public class GameSettings {

    // use volatile keyword
    private static volatile GameSettings instance;

    private GameSettings() {}

    public static GameSettings getInstance() {
        if (instance == null) {
            // synchronized block
            synchronized (GameSettings.class) {
                if (instance == null) {
                    return new GameSettings();
                }
            }
        }

        return instance;
    }
}
