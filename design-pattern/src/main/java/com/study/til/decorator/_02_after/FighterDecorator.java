package com.study.til.decorator._02_after;

import com.study.til.decorator.Fighter;

public abstract class FighterDecorator implements Fighter {
  private Fighter decoratedFighter;

  public FighterDecorator(Fighter decoratedFighter) {
    this.decoratedFighter = decoratedFighter;
  }

  @Override
  public void attack() {
    decoratedFighter.attack();
  }
}
