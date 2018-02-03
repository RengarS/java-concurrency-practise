package server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.util.CharsetUtil;
import server.domain.Handler;
import server.domain.Request;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 参数解析器
 * create by aries 2018-1-31
 */
public class ParamResolveUtil {
    /**
     * 参数解析器
     *
     * @param context
     * @param request
     * @return
     */
    public static Object paramResolve(ChannelHandlerContext context, FullHttpRequest request) {
        Handler handler = HandlerMapping.getHandler(new Request(request.method(), request.uri()));
        if (handler != null) {
            Method actionMethod = handler.getMethod();
            Parameter[] parameters = actionMethod.getParameters();
            LinkedList<Object> paramList = new LinkedList<>();
            if (request.method() == HttpMethod.GET) {
                Map<String, String> paramMap = getParameterMap(request);
                Arrays.stream(parameters).forEach(parameter -> {
                    paramList.add(paramMap.get(parameter.getName()));
                });
                try {
                    Object result = actionMethod.invoke(new Object(), paramList.toArray());
                    return request;
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else if (HttpMethod.POST == request.method()) {
                //request.content()
                ByteBuf byteBuf = request.content();
                byte[] bytes = new byte[byteBuf.readableBytes()];
                byteBuf.readBytes(bytes);
                String cotent = new String(bytes, CharsetUtil.UTF_8);
                //todo  将content转为Object
            }
        }

        return null;
    }

    /**
     * 获取get请求的参数列表
     *
     * @param fullHttpRequest
     * @return
     */
    public static Map<String, String> getParameterMap(FullHttpRequest fullHttpRequest) {
        String uri = fullHttpRequest.uri();

        int index = uri.lastIndexOf("?");
        String parameterStr = uri.substring(index + 1);
        String[] parameters = parameterStr.split("&");
        Map<String, String> parameterMap = new HashMap<>(parameters.length, 1.0F);
        for (String param : parameters) {
            int index2 = param.lastIndexOf("=");
            parameterMap.put(param.substring(0, index2), param.substring(index2 + 1));
        }
        return parameterMap;
    }
}
