package com.example.huaxiang.model;


import java.io.Serializable;

/**
 * Created by caiyk on 2017/3/8.
 */

public class Event<T> extends BaseInfo implements Serializable {
    public T data;
    public String message = "Event";

    public Event(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public Event(T data) {
        this.data = data;
    }
}
