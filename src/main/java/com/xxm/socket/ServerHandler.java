package com.xxm.socket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * @author CodingXXM
 * @desc 自定义Handler处理请求，使用String泛型
 * @date 2019/9/5 23:25
 **/
@Slf4j
public class ServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        log.info("remote url: {} , message: {}", ctx.channel().remoteAddress(), msg);
        ctx.channel().writeAndFlush("message form server: " + UUID.randomUUID());

    }

    //异常处理方法
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("error:{}", cause);
        //当产生异常，关闭连接
        ctx.close();
    }
}
