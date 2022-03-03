package com.songlinzheng.authentication.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class JWTRequest implements Serializable {
    private static final long serialVersionUID = 5926468583005150707L;
    private String email;
    private String password;

    public JWTRequest() {}

    public JWTRequest(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
    }


}
