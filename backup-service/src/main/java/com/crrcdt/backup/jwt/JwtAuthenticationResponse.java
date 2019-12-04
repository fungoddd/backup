package com.crrcdt.backup.jwt;

import java.io.Serializable;

/**
 * <p>
 * JwtAuthenticationResponse
 * </p>
 *
 * @author lyh
 * @date 2019年11月1日13:06:10
 */
public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    private String token;

    private String message;

    private boolean success = false;

    public JwtAuthenticationResponse(String token) {
        this.token = token;
        this.success = true;
    }

    public JwtAuthenticationResponse(boolean success, String message) {
        this.message = message;
        this.success = success;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public String getToken() {
        return this.token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
