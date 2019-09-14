package com.xxm.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.nio.channels.SocketChannel;

/**
 * @desc: chatServerHandler
 * @author: codingxxm
 * @date: 2019-09-13 12:07
 **/
@Slf4j
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    //存放channel
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel channel = channelHandlerContext.channel();
        channelGroup.forEach(ch -> {
            if(channel != ch) {
                ch.writeAndFlush(channel.remoteAddress() + "send message : " + s);
            }else {
                //or channel
                ch.writeAndFlush("[MySelf] " + s + "\n");
            }
        });
    }

    //表示服务端已经与客户端建立好连接-并不携带消息
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //实现广播
        //先发送广播，给其他已经保存的channel发送消息
        channelGroup.writeAndFlush("[SERVER] - " + channel.remoteAddress() + " join\n");
        //再将自己的channel存入group
        channelGroup.add(channel);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("error", cause);
        ctx.close();
    }

    //表示连接已经断掉
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[SERVER] - " + channel.remoteAddress() + " leave\n");

        //当连接断掉
        //netty会自动的调用channelGroup的remove方法，把无效的channel移除，所以此处不需要手动调用remove
    }

    //表示连接处于活动状态
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        log.info(channel.remoteAddress() + " online");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        log.info(channel.remoteAddress() + " offline");
    }
}
