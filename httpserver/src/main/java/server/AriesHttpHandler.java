package server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.CookieDecoder;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import io.netty.util.CharsetUtil;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class AriesHttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    /**
     * 接收到客户端请求
     *
     * @param channelHandlerContext
     * @param fullHttpRequest
     * @throws Exception
     */
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
        String uri = fullHttpRequest.uri();
        System.out.println("URI:" + uri);
        System.out.println("METHOD:" + fullHttpRequest.method().name());

        if (uri.equals("/favicon.ico")) {
            channelHandlerContext.write(returnImg()).addListener(ChannelFutureListener.CLOSE);
            System.out.println("did");
            return;
        }
        if (fullHttpRequest.method() == HttpMethod.GET) {
            Map<String, String> paramMap = ParamResolveUtil.getParameterMap(fullHttpRequest);

            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                System.out.println(entry.getKey() + "     " + entry.getValue());
            }
        }
        //获取cookie

        // Cookie[] cookies = (Cookie[]) ServerCookieDecoder.LAX.decode(fullHttpRequest.headers().get(HttpHeaders.Names.COOKIE)).toArray();

        ByteBuf httpContentByteBuf = fullHttpRequest.content();
        String httpContent = httpContentByteBuf.toString(CharsetUtil.UTF_8);

        System.out.println("Content:" + httpContent);

        FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        ByteBuf byteBuf = Unpooled.copiedBuffer("HTTP-OK", CharsetUtil.UTF_8);
        httpResponse.content().writeBytes(byteBuf);
        channelHandlerContext.writeAndFlush(httpResponse).addListener(ChannelFutureListener.CLOSE);
    }


    /**
     * 产生异常
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //super.exceptionCaught(ctx, cause);
        //打印异常
        cause.printStackTrace();
        //返回500
        HttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.INTERNAL_SERVER_ERROR);
        ctx.writeAndFlush(httpResponse).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 读操作完成
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //super.channelReadComplete(ctx);
        System.out.println(ctx.isRemoved());
        ctx.flush();
    }

    /**
     * 返回一个页面
     *
     * @return
     */
    private static HttpResponse returnImg() {
        File file = new File("/Users/wulingyunyingzhongxin/Desktop/logo.png");
        byte[] bytes = new byte[17000];

        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            bufferedInputStream.read(bytes);
            ByteBuf byteBuf = Unpooled.copiedBuffer(bytes);
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
            response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "image/jpeg;charset=UTF-8");
            response.content().writeBytes(byteBuf);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
