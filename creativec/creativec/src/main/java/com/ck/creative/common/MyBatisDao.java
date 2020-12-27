package com.ck.creative.common;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
 
import org.springframework.stereotype.Component;

@Retention(RetentionPolicy.RUNTIME)
@Component
@Target(ElementType.TYPE)
@Documented
public @interface MyBatisDao {

	/**
	 * 成员以无参数的方法的形式被声明
	 */
   String value() default"";
}
