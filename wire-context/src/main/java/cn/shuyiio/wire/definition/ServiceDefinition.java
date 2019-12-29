package cn.shuyiio.wire.definition;

import lombok.Getter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


@Getter
public class ServiceDefinition<T> implements Definition {

    private Class<?> classes;
    private String name;

    private T obj;


    public void reg() {

    }

}