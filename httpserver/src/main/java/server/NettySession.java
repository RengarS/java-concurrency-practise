package server;

import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * session
 * create by aries 2018-1-31
 */
@Getter
public class NettySession {
    private String id;   // ?  此处应该会使用channel name，不用新建id
    private Map<String, String> attributes = new HashMap<>();

    public NettySession(ChannelHandlerContext channel) {
        this.id = channel.name();
    }

    public void setAttribute(String key, String value) {
        this.attributes.put(key, value);
    }

    public String getAttribute(String key) {
        return this.attributes.get(key);
    }

    public void removeAttribute(String key) {
        this.attributes.remove(key);
    }
}
