package com.rock.googleOauth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@PropertySource("classpath:config/config.properties")
public class loginController<JSONObject> {

	@Value("${google.login.url}")
	private String googleLoginUrl;

	@Value("${google.client.id}")
	private String googleClientId;

	@Value("${google.redirect.url}")
	private String googleRedirectUrl;

	@Value("${google.auth.url}")
	private String googleAuthUrl;

	@Value("${google.secret}")
	private String googleClientSecret;

	// 구글 로그인창 호출
	@RequestMapping(value = "/getGoogleAuthUrl")
	public @ResponseBody String getGoogleAuthUrl(HttpServletRequest request) throws Exception {

		String reqUrl = googleLoginUrl + "/o/oauth2/v2/auth?client_id=" + googleClientId + "&redirect_uri="
				+ googleRedirectUrl + "&response_type=code&scope=email%20profile%20openid&access_type=offline";

		return reqUrl;
	}

	// 구글 연동정보 조회
	@RequestMapping(value = "/oauth_google")
	public String oauth_google(HttpServletRequest request, @RequestParam(value = "code") String authCode,
			HttpServletResponse response) throws Exception {

		// restTemplate 호출
		RestTemplate restTemplate = new RestTemplate();

		GoogleOAuthRequest googleOAuthRequestParam = GoogleOAuthRequest.builder().clientId(googleClientId)
				.clientSecret(googleClientSecret).code(authCode).redirectUri(googleRedirectUrl + "/login/oauth_google")
				.grantType("authorization_code").build();

		ResponseEntity<JSONObject> apiResponse = restTemplate.postForEntity(googleAuthUrl + "/token",
				googleOAuthRequestParam, JSONObject.class);
		JSONObject responseBody = apiResponse.getBody();

		// id_token은 jwt 형식
		String jwtToken = responseBody.getString("id_token");
		String requestUrl = UriComponentsBuilder.fromHttpUrl(googleAuthUrl + "/tokeninfo")
				.queryParam("id_token", jwtToken).toUriString();

		JSONObject resultJson = restTemplate.getForObject(requestUrl, JSONObject.class);

		// 구글 정보조회 성공
		if (resultJson != null) {

			// 회원 고유 식별자
			String googleUniqueNo = resultJson.getString("sub");

			/**
			 * 
			 * TO DO : 리턴받은 googleUniqueNo 해당하는 회원정보 조회 후 로그인 처리 후 메인으로 이동
			 * 
			 */

			// 구글 정보조회 실패
		} else {
			throw new ErrorMessage("구글 정보조회에 실패했습니다.");
		}

	}
}
