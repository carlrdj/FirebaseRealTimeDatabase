package com.rdj.carl.instagramfirebase.model;

/**
 * Created by SEVEN on 8/1/2017.
 */

public class Artist {
    private String id_artist;
    private String name;
    private String genere;

    public Artist() {
    }

    public Artist(String id_artist, String name, String genere) {
        this.id_artist = id_artist;
        this.name = name;
        this.genere = genere;
    }

    public String getId_artist() {
        return id_artist;
    }

    public void setId_artist(String id_artist) {
        this.id_artist = id_artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }
}
