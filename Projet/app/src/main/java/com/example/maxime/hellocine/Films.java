package com.example.maxime.hellocine;

/**
 * Created by ludoviccarlu on 26/03/2018.
 */

public class Films {

    private int id;
    private String note;
    private String release_date;
    private String imgUrl;
    private String desc;
    private String titre;

    public Films(int id, String titre, String imgUrl, String desc, String note, String release_date) {
        this.id = id;
        this.note = note;
        this.release_date = release_date;
        this.imgUrl = imgUrl;
        this.desc = desc;
        this.titre = titre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getReleaseDate() {
        return release_date;
    }

    public void setReleaseDate(String releaseDate) {
        this.release_date = releaseDate;
    }

    public String getImgUrl() { return imgUrl; }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
}
