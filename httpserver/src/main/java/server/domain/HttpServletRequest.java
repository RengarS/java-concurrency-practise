package server.domain;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import jdk.internal.util.xml.impl.Input;
import server.NettySession;
import server.NettySessionMannager;

import java.io.*;
import java.util.Set;

public class HttpServletRequest {
    private FullHttpRequest fullHttpRequest;
    private ChannelHandlerContext channelHandlerContext;

    public HttpServletRequest(FullHttpRequest fullHttpRequest, ChannelHandlerContext context) {
        this.channelHandlerContext = context;
        this.fullHttpRequest = fullHttpRequest;
    }

    /**
     * 获取cookie
     *
     * @return
     */
    public Cookie[] getCookies() {
        Set<Cookie> cookies = ServerCookieDecoder.LAX.decode(this.fullHttpRequest.headers().get(HttpHeaders.Names.COOKIE));
        return (Cookie[]) cookies.toArray();
    }

    /**
     * 获取请求data的length
     *
     * @return
     */
    public long getDataLength() {
        return Long.valueOf(fullHttpRequest.headers().get(HttpHeaders.Names.CONTENT_LENGTH));
    }

    /**
     * 获取请求头
     *
     * @return
     */
    public String getHeader() {
        return fullHttpRequest.headers().toString();
    }

    /**
     * 获取http请求方法
     *
     * @return
     */
    public String getMethod() {
        if (fullHttpRequest.method() == HttpMethod.GET) {
            return "GET";
        } else if (fullHttpRequest.method() == HttpMethod.POST) {
            return "POST";
        }
        return "";
    }

    public String getPathInfo() {
        return fullHttpRequest.uri();
    }

    /**
     * 获取sessionId
     *
     * @return
     */
    public String getSessionID() {
        return this.channelHandlerContext.name();
    }

    /**
     * 获取session
     *
     * @return
     */
    public NettySession getSession() {
        return NettySessionMannager.getSession(channelHandlerContext);
    }

    /**
     * 获取输入流
     *
     * @return
     */
    public InputStream getInputStream() {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(this.fullHttpRequest.content().array());
        return inputStream;
    }


}
