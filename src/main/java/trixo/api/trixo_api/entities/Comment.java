package trixo.api.trixo_api.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.cloud.Timestamp;

import trixo.api.trixo_api.controllers.TimestampDeserializer;
import trixo.api.trixo_api.controllers.TimestampSerializer;

public class Comment {
    private String comment_id;
    private String post_id;
    private String user_id;
    private String text;
    
    @JsonDeserialize(using = TimestampDeserializer.class)
    @JsonSerialize(using = TimestampSerializer.class)
    private Timestamp created_at;
    private String targetType;

    public String getComment_id() {
        return comment_id;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public String getPost_id() {
        return post_id;
    }

    public String getTargetType() {
        return targetType;
    }

    public String getText() {
        return text;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
