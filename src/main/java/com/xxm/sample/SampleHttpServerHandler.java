package com.xxm.sample;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * @author CodingXXM
 * @desc 处理器，读取客户端发来的请求，并且向客户端响应
 * @date 2019/9/3 22:26
 **/
@Slf4j
public class SampleHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        log.info("msg class: {}", msg.getClass());

        log.info("remote addr: {}", ctx.channel().remoteAddress());

        if (msg instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest) msg;
            log.info("request method: {}", httpRequest.method().name());

            URI uri = new URI(httpRequest.uri());
            if("/favicon.ico".equals(uri.getPath())) {
                log.info("request favicon.ico");
                return;
            }

            //构造信息向客户端响应
            ByteBuf content = Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            //设置响应头信息
            response.headers()
                    .set(HttpHeaderNames.CONTENT_TYPE, "text/plain")
                    .set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            //响应，如果调用write()，那么数据只会被放入缓冲区，不会响应给客户端
            ctx.writeAndFlush(response);

            //服务端在响应后，主动关闭channel
            //实际中，应先判断请求协议版本http1.0或者http1.1，然后根据超时时间等判断依据进行处理
            ctx.channel().close();
        }

    }

    /*
     * 重写ChannelInBoundHandlerAdapter的一些方法，观察事件方法执行时机
     *
     * 当浏览器发出http请求后，netty收到请求并响应后，浏览器并不会主动关闭连接，可以手动实现，服务端主动关闭连接
     *
     **/

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("channel active");
        super.channelActive(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("chanbel registered");
        super.channelRegistered(ctx);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("handler added");
        super.handlerAdded(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("channel inactive");
        super.channelInactive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.info("channel unregistered");
        super.channelUnregistered(ctx);
    }
}
