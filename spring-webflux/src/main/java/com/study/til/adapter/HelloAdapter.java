package com.study.til.adapter;

import com.study.til.handler.TilHandler;
import com.study.til.adapter.req.HelloReq;


public interface HelloAdapter<O> extends TilHandler<HelloReq, O> {

}
