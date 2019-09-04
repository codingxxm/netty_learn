package com.xxm.sample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author CodingXXM
 * @desc 服务端的初始化器，用来增加一些netty提供的channel handler和自己定义的channel handler
 * @date 2019/9/3 22:21
 **/
public class SampleServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //HttpServerCodec实现对请求的解码和响应的编码
        //合二为一的实现了HttpRequestDecoder和HttpResponseEncoder的功能
        pipeline.addLast("httpServerCodec", new HttpServerCodec())
                //添加请求处理器
                .addLast("SampleHttpServerHandler", new SampleHttpServerHandler());
    }
}
