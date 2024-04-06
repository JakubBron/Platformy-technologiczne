package org.example;

import java.io.Serializable;

// daje gettery, settery, konstruktor, equals, hashCode, toString
public record Message(int number, String content) implements Serializable {
}
/*
public class Message implements Serializable {
    private int number;
    private String content;

    Message(int number, String content) {
        this.number = number;
        this.content = content;
    }

    public int getNumber() {
        return number;
    }

    public String getContent() {
        return content;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
 */