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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public class HttpServletRequest {
    private FullHttpRequest fullHttpRequest;
    private ChannelHandlerContext channelHandlerContext;

    public HttpServletRequest(FullHttpRequest fullHttpRequest, ChannelHandlerContext context) {
        this.channelHandlerContext = context;
        this.fullHttpRequest = fullHttpRequest;
    }

    public Cookie[] getCookies() {
        Set<Cookie> cookies = ServerCookieDecoder.LAX.decode(this.fullHttpRequest.headers().get(HttpHeaders.Names.COOKIE));
        return (Cookie[]) cookies.toArray();
    }

    public long getDataLength() {
        return Long.valueOf(fullHttpRequest.headers().get(HttpHeaders.Names.CONTENT_LENGTH));
    }

    public String getHeader() {
        return fullHttpRequest.headers().toString();
    }

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

    public String getSessionID() {
        return this.channelHandlerContext.name();
    }

    public NettySession getSession() {
        return NettySessionMannager.getSession(channelHandlerContext);
    }

    public InputStream getInputStream(){
    }


}
