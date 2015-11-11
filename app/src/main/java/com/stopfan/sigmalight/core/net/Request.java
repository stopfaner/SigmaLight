package com.stopfan.sigmalight.core.net;

/**
 * @author Denys Lytvinyuk
 * Made with love
 */
public class Request<T> {

    final String action;

    final T data;

    public Request(String action, T data) {
        this.action = action;
        this.data = data;
    }

    public String getAction() {
        return this.action;
    }

    public T getData() {
        return this.data;
    }

}
