package com.restaurant_service.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class MenuResponseDTO implements Serializable
{
    private Long id;
    private Long restaurantId;
    private String menuName;
    private Boolean active;
}

/*
In Java, Serializable is a marker interface used to convert an object into a byte stream so it can be:

Stored in a file
Sent over a network
Cached in Redis
Saved in session
Transferred between microservices

The interface belongs to:

import java.io.Serializable;

Serializable is a marker interface.It only tells JVM "the object is allowed to be serialized".
Marker Interface is an interface that contains no methods.

Primitive types and String are already serializable internally.
Custom classes must explicitly implement Serializable.
* */