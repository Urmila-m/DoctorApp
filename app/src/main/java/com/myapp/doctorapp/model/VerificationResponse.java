package com.myapp.doctorapp.model;

import java.io.Serializable;

public class VerificationResponse implements Serializable {
    int verified;

    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }

    public VerificationResponse(int verified) {
        this.verified = verified;
    }
}
