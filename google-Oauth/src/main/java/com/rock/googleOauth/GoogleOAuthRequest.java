package com.rock.googleOauth;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class GoogleOAuthRequest {
    private String redirectUri;
    private String clientId;
    private String clientSecret;
    private String code;
    private String responseType;
    private String scope;
    private String accessType;
    private String grantType;
    private String state;
    private String includeGrantedScopes;
    private String loginHint;
    private String prompt;
	public static Object builder() {
		// TODO Auto-generated method stub
		return null;
	}
}