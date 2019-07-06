package com.future.lobajo;

public class Sightseeing {

    enum Type
    {
        HISTORICAL,
        LOCAL_NEWS,
        EVENT,
        COMMUNITY,
    };

    public Type type;
    public String name;
    public String link;
    public String description;

    public static void main(String args[]) {

        System.out.println("Hello Sightseeing");
    }
}
