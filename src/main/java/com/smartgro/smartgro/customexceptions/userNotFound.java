package com.smartgro.smartgro.customexceptions;

public class userNotFound extends RuntimeException{
    public userNotFound(String email) {
        super("User not found with "+email+" in database");
    }
}
