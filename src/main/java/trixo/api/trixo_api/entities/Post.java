package trixo.api.trixo_api.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.cloud.Timestamp;

import trixo.api.trixo_api.controllers.TimestampDeserializer;
import trixo.api.trixo_api.controllers.TimestampSerializer;


public class Post {
    private String id;
    private String caption;
    private List<String> images = new ArrayList<String>();
    private List<String> likedBy = new ArrayList<String>();
    
    @JsonDeserialize(using = TimestampDeserializer.class)
    @JsonSerialize(using = TimestampSerializer.class)
    private Timestamp created_at;

    private Integer comments_count;
    private List<String> tags = new ArrayList<String>();
    private User user;
    private Report report;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String title) {
        this.caption = title;
    }

    public Integer getComments_count() {
        return comments_count;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<String> getImages() {
        return images;
    }

    public void setComments_count(Integer comments_count) {
        this.comments_count = comments_count;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp creationDate) {
        this.created_at = creationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(List<String> likedBy) {
        this.likedBy = likedBy;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

}