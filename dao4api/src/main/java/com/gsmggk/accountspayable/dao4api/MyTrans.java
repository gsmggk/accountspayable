/**
 * 
 */
package com.gsmggk.accountspayable.dao4api;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.transaction.annotation.Transactional;

@Target(METHOD)
@Retention(RUNTIME)
/**
 * Annotation  MyTrans need for add @Transaction to service method.
 * @author Gena
 *
 */
@Transactional
public @interface MyTrans  {

}
