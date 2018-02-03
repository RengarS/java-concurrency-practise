package server;

import lombok.Data;
import server.domain.Handler;
import server.domain.Request;

import java.util.HashMap;
import java.util.Map;


public class HandlerMapping {
    private static Map<Request, Handler> handlerMap = new HashMap<>();

    public static Handler getHandler(Request request) {
        return handlerMap.get(request);
    }
}
