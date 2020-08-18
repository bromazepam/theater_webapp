package com.theater.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Data
@Entity
public class Play {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String director;
    private String category;
    private boolean active=true;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "play")
    private List<Repertoire> repertoires = new ArrayList<>();

    @Column(columnDefinition="text")
    private String description;

    @Lob
    private byte[] playImage;

    public static String convertBinImageToString(byte[] binImage) {
        if(binImage!=null && binImage.length>0) {
            return Base64.getEncoder().encodeToString(binImage);
        }
        else
            return "";
    }
}
