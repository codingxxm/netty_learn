package com.xxm.socket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * @author CodingXXM
 * @desc 服务端向客户端发送的消息在这处理
 * @date 2019/9/6 0:20
 **/
@Slf4j
public class ClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.info("remote url:{}", ctx.channel().remoteAddress());
        log.info("client get message:{}", msg);
        ctx.writeAndFlush("msg from client:" + LocalDateTime.now());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("msg from client init");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("error:{}", cause);
        ctx.close();
    }
}
