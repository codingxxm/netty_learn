package com.xxm.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @desc: chatClient
 * @author: codingxxm
 * @date: 2019-09-13 12:43
 **/
@Slf4j
public class ChatClient {

    public static void main(String[] args) {
        ChatClient chatClient = new ChatClient();
        chatClient.start();
    }

    public void start() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(bossGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChatClientInitializer());

            //获取和服务器的连接
            Channel channel = bootstrap.connect("127.0.0.1", 8888).sync().channel();

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            //死循环读取console输入信息，发送给服务端
            for (;;){
                channel.writeAndFlush(reader.readLine() + "\r\n");
            }

        } catch (InterruptedException e) {
            log.error("error", e);
        } catch (IOException e) {
            log.error("error", e);
        } finally {
            bossGroup.shutdownGracefully();
        }

    }
}
