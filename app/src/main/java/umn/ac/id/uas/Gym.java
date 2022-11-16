package umn.ac.id.uas;

import java.io.Serializable;

public class Gym implements Serializable {
    private String nama, lokasi, review, jarak, tipe;
    private int rating, teenagePrice, adultPrice, gympic;

    public Gym(String nama, String lokasi, String review, String jarak, String tipe, int gympic, int rating, int teenagePrice, int adultPrice){
        this.nama = nama;
        this.lokasi = lokasi;
        this.review = review;
        this.jarak = jarak;
        this.tipe = tipe;
        this.gympic = gympic;
        this.rating = rating;
        this.teenagePrice = teenagePrice;
        this.adultPrice = adultPrice;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getJarak() {
        return jarak;
    }

    public void setJarak(String jarak) {
        this.jarak = jarak;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public int getGympic() {
        return gympic;
    }

    public void setGympic(int gympic) {
        this.gympic = gympic;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getTeenagePrice() {
        return teenagePrice;
    }

    public void setTeenagePrice(int teenagePrice) {
        this.teenagePrice = teenagePrice;
    }

    public int getAdultPrice() {
        return adultPrice;
    }

    public void setAdultPrice(int adultPrice) {
        this.adultPrice = adultPrice;
    }
}
