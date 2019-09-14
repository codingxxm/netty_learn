package com.xxm.chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @desc: chatClientHandler
 * @author: codingxxm
 * @date: 2019-09-13 12:49
 **/
@Slf4j
public class ChatClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        log.info("msg from server: {}", s);

    }

}
