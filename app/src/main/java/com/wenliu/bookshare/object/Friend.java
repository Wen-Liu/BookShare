package com.wenliu.bookshare.object;

public class Friend {

    private String id;
    public String name;
    public String email;
    public String imageUrl;
    public String status;

    public Friend() {
        id = "";
        name = "";
        email = "";
        imageUrl = "";
        status = "";
    }

    public Friend(User user) {
        id = user.getId();
        name = user.getName();
        email = user.getEmail();
        imageUrl = user.getImage();
        status = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
