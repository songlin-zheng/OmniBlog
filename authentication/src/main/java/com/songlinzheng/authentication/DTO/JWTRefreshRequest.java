package com.songlinzheng.authentication.DTO;

import lombok.Data;

@Data
public class JWTRefreshRequest {
    private static final long serialVersionUID = 5926468583005153427L;
    private String recoveryToken;
    public JWTRefreshRequest() {}
}
