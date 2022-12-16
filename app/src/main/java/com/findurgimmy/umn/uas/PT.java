package com.findurgimmy.umn.uas;

import java.io.Serializable;
import java.util.ArrayList;

public class PT implements Serializable {
    private String nama, gym, noTelp, ptpic;
    private int rating, price, umur;
    private ArrayList<String> jadwal;
    private ArrayList<String> keahlian;

    public PT(String nama, String gym, int umur, String noTelp, int rating, int price, String ptpic, ArrayList<String> keahlian, ArrayList<String> jadwal) {
        this.nama = nama;
        this.umur = umur;
        this.noTelp = noTelp;
        this.rating = rating;
        this.price = price;
        this.ptpic = ptpic;
        this.gym = gym;
        this.keahlian = keahlian;
        this.jadwal = jadwal;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getGym() {
        return gym;
    }

    public void setGym(String gym) {
        this.gym = gym;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public ArrayList<String> getKeahlian() {
        return keahlian;
    }

    public void setKeahlian(ArrayList<String> keahlian) {
        this.keahlian = keahlian;
    }

    public String getPtpic() {
        return ptpic;
    }

    public void setPtpic(String ptpic) {
        this.ptpic = ptpic;
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

    public int getUmur() {
        return umur;
    }

    public void setUmur(int umur) {
        this.umur = umur;
    }

    public ArrayList<String> getJadwal() {
        return jadwal;
    }

    public void setJadwal(ArrayList<String> jadwal) {
        this.jadwal = jadwal;
    }
}
