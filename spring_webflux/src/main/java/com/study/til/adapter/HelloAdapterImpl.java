package com.study.til.adapter;

import com.study.til.adapter.req.HelloReq;
import org.springframework.stereotype.Service;

@Service
public class HelloAdapterImpl implements HelloAdapter<String> {
  private static final String HELLO = "Hello,";

  @Override
  public String handle(HelloReq in) {
    return HELLO.concat(" " + in.getName() + "!");
  }
}
