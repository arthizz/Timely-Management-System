package com.art.timelymanagementsystem.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenRequest {

    @NotBlank(message = "Please provide your refresh token")
    private String refreshToken;

}
