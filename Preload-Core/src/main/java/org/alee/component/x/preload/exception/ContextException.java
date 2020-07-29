package org.alee.component.x.preload.exception;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/7/21
 * @description: xxxx
 *
 *********************************************************/
public class ContextException extends RuntimeException {

    public ContextException() {
        super("Please pass in a valid Context!");
    }
}
