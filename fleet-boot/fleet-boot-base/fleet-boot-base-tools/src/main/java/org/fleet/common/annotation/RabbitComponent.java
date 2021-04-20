package org.fleet.common.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @Author:fleet-team
 * @Date:2021-04-15 10:43
 * @Description: 消息队列初始化注解
 **/
@Documented
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface RabbitComponent {
    @AliasFor(
            annotation = Component.class
    )
    String value();
}
