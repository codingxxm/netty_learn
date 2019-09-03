package com.xxm.sample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author CodingXXM
 * @desc 最简服务端
 * @date 2019/9/3 22:11
 **/
@Slf4j
public class SampleServer {

    public static void main(String[] args) {
        SampleServer sampleServer = new SampleServer();
        try {
            sampleServer.startServer();
        } catch (InterruptedException e) {
            log.error("error:", e);
        }
    }

    public void startServer() throws InterruptedException {

        //定义事件循环组，netty推荐2个线程组
        //bossGroup接收连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //workerGroup对连接进行真正的处理，业务操作
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //server端启动辅助类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //加载线程组
            serverBootstrap.group(bossGroup, workerGroup)
                    //使用nio管道
                    .channel(NioServerSocketChannel.class)
                    //子处理器，请求处理器，在初始化器中进行具体添加需要的channel handler
                    .childHandler(new SampleServerInitializer());

            //绑定端口
            ChannelFuture channelFuture = serverBootstrap.bind(8888).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            //优雅关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}
