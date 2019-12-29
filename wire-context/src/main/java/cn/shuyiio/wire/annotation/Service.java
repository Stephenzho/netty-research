package cn.shuyiio.wire.annotation;


import cn.shuyiio.wire.registrar.BeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(BeanDefinitionRegistrar.class)
public @interface Service {

    String value();

}
