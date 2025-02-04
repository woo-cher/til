package com.study.til.proxy.use_java_dynamic;

import com.study.til.proxy.use_interface.GameService;
import java.lang.reflect.Proxy;

public class DynamicProxy {

  public GameService getLogServiceProxy(GameService target) {
    return (GameService)
        Proxy.newProxyInstance(
            this.getClass().getClassLoader(),
            new Class[] {GameService.class},
            (proxy, method, args) -> {
              System.out.println("메소드 실행 전 로그");
              method.invoke(target, args);
              System.out.println("메소드 실행 후 로그");
              return null;
            });
  }

  public GameService getTimerServiceProxy(GameService target) {
    return (GameService)
        Proxy.newProxyInstance(
            this.getClass().getClassLoader(),
            new Class[] {GameService.class},
            (proxy, method, args) -> {
              long before = System.currentTimeMillis();
              method.invoke(target, args);
              System.out.println("걸린 시간 : " + (before - System.currentTimeMillis()));
              return null;
            });
  }
}
