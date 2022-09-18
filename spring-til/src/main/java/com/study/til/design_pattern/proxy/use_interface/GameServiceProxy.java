package com.study.til.design_pattern.proxy.use_interface;

public class GameServiceProxy implements GameService {

    private GameService gameService;

    public GameServiceProxy(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public void startGame() {
        long startTime = System.currentTimeMillis();

        // 초기화 지연 (lazy-init)
        if (this.gameService == null) {
            this.gameService = new DefaultGameService();
        }

        this.gameService.startGame();
        System.out.println(System.currentTimeMillis() - startTime);
    }
}
