package com.study.til.proxy.use_java_dynamic;

import com.study.til.proxy.use_interface.DefaultGameService;
import com.study.til.proxy.use_interface.GameService;

public class Client {
    public static void main(String[] args) {
        DynamicProxy dynamicProxy = new DynamicProxy();
        GameService gameService = dynamicProxy.getLogServiceProxy(new DefaultGameService());
        gameService.startGame();

        gameService = dynamicProxy.getTimerServiceProxy(new DefaultGameService());
        gameService.startGame();
    }
}
