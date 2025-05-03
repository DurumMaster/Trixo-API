package trixo.api.trixo_api.entities;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.cloud.Timestamp;

import trixo.api.trixo_api.controllers.TimestampDeserializer;
import trixo.api.trixo_api.controllers.TimestampSerializer;


public class Post {
    private Integer id;
    private String caption;
    private List<String> images;

    @JsonDeserialize(using = TimestampDeserializer.class)
    @JsonSerialize(using = TimestampSerializer.class)
    private Timestamp created_at;
    private int likes_count;
    private Integer comments_count;
    private List<String> tags;
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
    // public List<String> getImages() {
    //     return images;
    // }

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
    public int getLikes_count() {
        return likes_count;
    }
    public void setLikes_count(int netVotes) {
        this.likes_count = netVotes;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
