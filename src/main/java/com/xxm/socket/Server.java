package com.xxm.socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author CodingXXM
 * @desc netty服务端
 * @date 2019/9/5 22:44
 **/
@Slf4j
public class Server {

    public static void main(String[] args) {

    }

    public static void start() {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerInitializer());

            //后面加sync()，netty才会一直等待
            ChannelFuture channelFuture = serverBootstrap.bind(8888).sync();
            channelFuture.channel().close().sync();
        } catch (InterruptedException e) {
            log.error("error: {}", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }



    }
}
