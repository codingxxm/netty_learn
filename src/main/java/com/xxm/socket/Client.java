package com.xxm.socket;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author CodingXXM
 * @desc netty客户端
 * @date 2019/9/5 23:35
 **/
@Slf4j
public class Client {

    public static void main(String[] args) {

    }

    public static void start() {

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(SocketChannel.class)
                    .handler(new ClientInitializer());

            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8888).sync();
            channelFuture.channel().writeAndFlush("test msg");
            channelFuture.channel().close().sync();
        } catch (InterruptedException e) {
            log.error("error: {}", e);
        } finally {
            eventLoopGroup.shutdownGracefully();
        }


    }
}
