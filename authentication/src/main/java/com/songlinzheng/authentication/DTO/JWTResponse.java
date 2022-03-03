package com.songlinzheng.authentication.DTO;

import lombok.Getter;
import java.io.Serializable;

@Getter
public class JWTResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwtToken;
    private final String recoveryToken;
    public JWTResponse(String jwtToken
            , String recoveryToken
    ) {
        this.jwtToken = jwtToken;
        this.recoveryToken = recoveryToken;
    }
}
