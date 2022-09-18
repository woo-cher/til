package com.study.til.singleton._04_static_inner_class;

public class GameSettings {

    private GameSettings() {}

    // thread-safe and lazy-initialization
    private static class SettingsHolder {
        private static final GameSettings INSTANCE = new GameSettings();
    }

    public static GameSettings getInstance() {
        return SettingsHolder.INSTANCE;
    }
}
