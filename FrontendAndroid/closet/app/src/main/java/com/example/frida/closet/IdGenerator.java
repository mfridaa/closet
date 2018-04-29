package com.example.frida.closet;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.iid.InstanceID;

import java.sql.Timestamp;
import java.util.UUID;

public class IdGenerator{

    private String iid;

    public IdGenerator(MapsActivity map){
        this.iid = InstanceID.getInstance(map.getBaseContext()).getId();
    }

    public String getId(){
        return this.iid;
    }

    public String getUniquePsuedoID() {
        //String iid = InstanceID.getInstance(this.getBaseContext()).getId();

        String m_szDevIDShort = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + (Build.CPU_ABI.length() % 10) + (Build.DEVICE.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String time = Long.toString(timestamp.getTime());
        String serial = "serial"; // some value
        try {
            serial = android.os.Build.class.getField("SERIAL").get(null).toString();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return new UUID((m_szDevIDShort+time).hashCode(), serial.hashCode()).toString();
    }
}

