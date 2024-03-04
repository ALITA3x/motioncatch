package com.alita.example.cloud_point.tcp;

import lombok.Data;

import java.io.Serializable;

@Data
public class SubDeviceData implements Serializable {


    private String userName;

    private String uid;

    private String token;

    private short second;

    private String cmd;

    //private Boolean pushStatus;
    //private String userName;
}
