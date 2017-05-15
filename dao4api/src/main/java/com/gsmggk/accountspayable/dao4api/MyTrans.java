/**
 * 
 */
package com.gsmggk.accountspayable.dao4api;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(METHOD)
@Retention(RUNTIME)
/**
 * Annotation  MyTrans need for add @Transaction to service method.
 * @author Gena
 *
 */
public @interface MyTrans  {

}
