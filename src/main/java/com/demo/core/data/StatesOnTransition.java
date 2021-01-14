package com.demo.core.data;

import org.springframework.statemachine.annotation.OnTransition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@OnTransition
/**
 * The custom annotation is necessary as the out-of-box @OnTransition is not type safe for the enums that
 * we use to express states.
 */
public @interface StatesOnTransition {
    States[] source() default {};

    States[] target() default {};
}