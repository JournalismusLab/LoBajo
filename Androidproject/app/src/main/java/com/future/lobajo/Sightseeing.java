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

    public String toString()
    {
        return type.name() + ";" + name + ";" + link + ";" + description;
    }

    public static void main(String args[]) {

        System.out.println("Hello Sightseeing");

        {
            Sightseeing s = new Sightseeing( Type.HISTORICAL, "Schloss Borbeck", "https://de.wikipedia.org/wiki/Schloss_Borbeck", "Seit dem 14. Jahrhundert war es bevorzugte Residenz der Essener Fürstäbtissinnen und erhielt seine heutige äußere Gestalt im 18. Jahrhundert." );

            System.out.println( s.toString() );
        }

        {
            Sightseeing s = new Sightseeing( Type.EVENT, "hacking://local_media", "https://www.vor-ort.nrw/2019/05/02/hackathon-hacking-local_media/", "Um den (Lokal-)Journalismus zu verbessern, wollen wir ihn hacken: Querdenken, Networken, Prototyping bis zum Umfallen. Deshalb kommen vom 5. bis 7. Juli Menschen aus den Bereichen Journalismus, Softwareentwicklung, (UX)-Design und Business-Development im Essener Unperfekthaus zusammen." );

            System.out.println( s.toString() );
        }
    }
}
