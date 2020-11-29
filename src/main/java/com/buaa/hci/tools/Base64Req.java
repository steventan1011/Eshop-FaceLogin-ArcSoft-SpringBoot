package com.buaa.hci.tools;

import lombok.Data;

import java.io.Serializable;

@Data
public class Base64Req implements Serializable {
    private String base64;
}
