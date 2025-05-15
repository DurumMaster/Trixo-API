package trixo.api.trixo_api.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.cloud.Timestamp;
import trixo.api.trixo_api.controllers.TimestampDeserializer;
import trixo.api.trixo_api.controllers.TimestampSerializer;
import trixo.api.trixo_api.entities.User;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class PostResponse {
    private String id;
    private String caption;
    private List<String> images;
    
    @JsonDeserialize(using = TimestampDeserializer.class)
    @JsonSerialize(using = TimestampSerializer.class)
    private Timestamp created_at;
    
    private Integer comments_count;
    private List<String> tags;
    private User user;
    private String status;
    
    @JsonProperty("is_liked")
    private boolean isLiked;
    
    @JsonProperty("likes_count")
    private int likesCount;

    // Constructor
    public PostResponse(String id, 
                       String caption, 
                       List<String> images,
                       Timestamp created_at,
                       Integer comments_count,
                       List<String> tags,
                       User user,
                       String status,
                       boolean isLiked, 
                       int likesCount) {
        this.id = id;
        this.caption = caption;
        this.images = images;
        this.created_at = created_at;
        this.comments_count = comments_count;
        this.tags = tags;
        this.user = user;
        this.status = status;
        this.isLiked = isLiked;
        this.likesCount = likesCount;
    }

    // Getters
    public String getId() { return id; }
    public String getCaption() { return caption; }
    public List<String> getImages() { return images; }
    public Timestamp getCreated_at() { return created_at; }
    public Integer getComments_count() { return comments_count; }
    public List<String> getTags() { return tags; }
    public User getUser() { return user; }
    public String getStatus() { return status; }
    public boolean isLiked() { return isLiked; }
    public int getLikesCount() { return likesCount; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setCaption(String caption) { this.caption = caption; }
    public void setImages(List<String> images) { this.images = images; }
    public void setCreated_at(Timestamp created_at) { this.created_at = created_at; }
    public void setComments_count(Integer comments_count) { this.comments_count = comments_count; }
    public void setTags(List<String> tags) { this.tags = tags; }
    public void setUser(User user) { this.user = user; }
    public void setStatus(String status) { this.status = status; }
    public void setLiked(boolean liked) { isLiked = liked; }
    public void setLikesCount(int likesCount) { this.likesCount = likesCount; }
}