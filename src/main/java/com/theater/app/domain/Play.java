package com.theater.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Transient
    private MultipartFile playImage;

//    @OneToMany(mappedBy = "book")
//    @JsonIgnore
//    private List<BookToCartItem> bookToCartItemList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getPlayImage() {
        return playImage;
    }

    public void setPlayImage(MultipartFile playImage) {
        this.playImage = playImage;
    }

//    public List<BookToCartItem> getBookToCartItemList() {
//        return bookToCartItemList;
//    }
//
//    public void setBookToCartItemList(List<BookToCartItem> bookToCartItemList) {
//        this.bookToCartItemList = bookToCartItemList;
//    }

    public List<Repertoire> getRepertoires() {
        return repertoires;
    }

    public void setRepertoires(List<Repertoire> repertoires) {
        this.repertoires = repertoires;
    }
}
