package umn.ac.id.uas;

import java.io.Serializable;

public class PT implements Serializable {
    private String nama, desc, umur, noTelp, lokasi, review, jarak, type;
    private int rating, price, ptpic;

    public PT(String nama, String desc, String umur, String noTelp, String lokasi, String review, String jarak, String type, int rating, int price, int ptpic) {
        this.nama = nama;
        this.desc = desc;
        this.umur = umur;
        this.noTelp = noTelp;
        this.lokasi = lokasi;
        this.review = review;
        this.jarak = jarak;
        this.type = type;
        this.rating = rating;
        this.price = price;
        this.ptpic = ptpic;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUmur() {
        return umur;
    }

    public void setUmur(String umur) {
        this.umur = umur;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPtpic() {
        return ptpic;
    }

    public void setPtpic(int ptpic) {
        this.ptpic = ptpic;
    }
}
