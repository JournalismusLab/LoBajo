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

    public Sightseeing( Type type, String name, String link, String description )
    {
        this.type = type;
        this.name = name;
        this.link = link;
        this.description = description;
    }

    public static void main(String args[]) {

        System.out.println("Hello Sightseeing");
    }
}
