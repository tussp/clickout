package com.clickout.clickout;

import java.util.HashMap;
import java.util.Map;

public class ScoreManager {
    private final static Map<String, Score> localScore = new HashMap<>();
    public static void updateScore(String key, Score value) {
        localScore.put(key, value);
    }

    public static Score getScore(String key) {
        Score value = localScore.get(key);
        if (value == null) {
            value = new Score();
        }
        return value;
    }
}
