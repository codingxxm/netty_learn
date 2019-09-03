package com.xxm.sample;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @author CodingXXM
 * @desc 处理器，读取客户端发来的请求，并且向客户端响应
 * @date 2019/9/3 22:26
 **/
public class SampleHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        if (msg instanceof HttpRequest) {
            //构造信息向客户端响应
            ByteBuf content = Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            //设置响应头信息
            response.headers()
                    .set(HttpHeaderNames.CONTENT_TYPE, "text/plain")
                    .set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            //响应，如果调用write()，那么数据只会被放入缓冲区，不会响应给客户端
            ctx.writeAndFlush(response);
        }

    }
}
