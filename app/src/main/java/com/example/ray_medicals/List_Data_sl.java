package com.example.ray_medicals;

public class List_Data_sl {
    private String namef;
    private String addressf;
    private String pinf;


    public List_Data_sl(String namef, String addressf, String pinf) {
        this.namef = namef;
        this.addressf=addressf;
        this.pinf=pinf;


    }

    public String getNamef() {
        return namef;
    }
    public String getAddressf() {
        return addressf;
    }
    public String getPinf(){
        return pinf;
    }

}