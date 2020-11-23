package com.theater.app.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Base64;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Play {

    @Id
    private String id;
    private String title;
    private String author;
    private String director;
    private String category;
    private boolean active=true;
    private String description;
    private byte[] playImage;


    public boolean isActive() {
        return active;
    }

    public String getPlayImage() {
        return convertBinImageToString(playImage);
    }

    private static String convertBinImageToString(byte[] binImage) {
        if(binImage!=null && binImage.length>0) {
            return Base64.getEncoder().encodeToString(binImage);
        }
        else
            return "";
    }

}
