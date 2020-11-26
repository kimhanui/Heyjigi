package com.jigi.config.oauth;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Target: 이 어노테이션이 생성될 수 있는 위치를 지정(parameter로 선언된 객체에서만 사용가능)
 * @LoginUser: 이 파일을 어노테이션 클래스로 지정
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {

}