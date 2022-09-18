package com.study.til.design_pattern.singleton._01_non_thread_safe;

public class GameSettings {

    private static GameSettings instance;

    private GameSettings() {}

    // non-thread safe
    public static GameSettings getInstance() {
        if (instance== null) {
            return new GameSettings();
        }

        return instance;
    }
}
