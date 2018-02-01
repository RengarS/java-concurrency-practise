package server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;


import java.util.HashMap;

/**
 * session管理器
 * create by aries 2018-1-31
 */
public class NettySessionMannager {

    public static HashMap<Channel, NettySession> sessionContainer = new HashMap<>();

    public static void createSession(ChannelHandlerContext context) {
        if (sessionContainer.get(context.channel()) == null) {
            sessionContainer.put(context.channel(), new NettySession(context));
        }
    }

    /**
     * 获取session
     *
     * @param context
     * @return
     */
    public static NettySession getSession(ChannelHandlerContext context) {
        return sessionContainer.get(context.channel());
    }

    /**
     * 销毁session
     * 定时执行
     */
    public static void removeSession() {
        for (Channel channel : sessionContainer.keySet()) {
            if (!channel.isOpen()) {
                sessionContainer.remove(channel);
            }
        }
    }
}
