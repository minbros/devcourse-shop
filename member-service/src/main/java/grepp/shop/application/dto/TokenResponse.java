package grepp.shop.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenResponse(
        @JsonProperty("access-token")
        String accessToken,

        @JsonProperty("refresh-token")
        String refreshToken
) {
}
