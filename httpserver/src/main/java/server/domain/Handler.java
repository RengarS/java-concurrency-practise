package server.domain;

import lombok.Data;

import java.lang.reflect.Method;

@Data
public class Handler {
    private Class<?> clz;
    private Method method;
}
