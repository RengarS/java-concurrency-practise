package server.domain;

import io.netty.handler.codec.http.HttpMethod;
import lombok.Data;

@Data
public class Request {
    private HttpMethod method;
    private String uri;

    public Request(HttpMethod method, String uri) {
        this.method = method;
        this.uri = uri;
    }
}
