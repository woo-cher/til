package com.study.til.handler;

public interface TilHandler<I, O>{
  O handle(I in);
}
