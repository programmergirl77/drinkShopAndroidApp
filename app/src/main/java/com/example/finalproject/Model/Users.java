package com.example.finalproject.Model;

import com.google.gson.annotations.SerializedName;

public class Users {

        @SerializedName("userName")
        private String username;

        @SerializedName("email")
        private String email;

        @SerializedName("lastName")
        private String lastname;

        @SerializedName("firstName")
        private String firstname;

        @SerializedName("id")
        private int id;

        @SerializedName("message")
        private String message;

    @SerializedName("avatarUrl")
    private String avatarUrl;


    @SerializedName("error")
        private boolean error;

        public Users(String username, String email, String lastname, String firstname) {
            this.username = username;
            this.email = email;
            this.lastname = lastname;
            this.firstname = firstname;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public boolean isError() {
            return error;
        }

        public void setError(boolean error) {
            this.error = error;
        }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}

