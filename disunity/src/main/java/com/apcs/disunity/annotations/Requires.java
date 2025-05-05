package com.apcs.disunity.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.apcs.disunity.nodes.Node;

/**
 * Specifies the children types that a node must have
 * 
 * @author Qinzhao Li
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Requires {
    /** The children types that the node must have */
    Class<? extends Node>[] nodes();
}
