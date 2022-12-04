package umn.ac.id.uas;

import java.io.Serializable;

public class Gym implements Serializable {
    private String nama, lokasi, tipe, deskripsi;
    private int rating, teenagePrice, adultPrice, gympic, review;
    private double latitude, longitude;


    public Gym(String nama, String lokasi, String deskripsi, int review, String tipe, int rating, int teenagePrice, int adultPrice, double latitude, double longitude){
        this.nama = nama;
        this.lokasi = lokasi;
        this.deskripsi = deskripsi;
        this.review = review;
        this.tipe = tipe;
        this.rating = rating;
        this.teenagePrice = teenagePrice;
        this.adultPrice = adultPrice;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
