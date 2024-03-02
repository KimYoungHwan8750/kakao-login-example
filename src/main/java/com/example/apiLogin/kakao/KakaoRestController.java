package com.example.apiLogin.kakao;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@RestController
public class KakaoRestController {
    @GetMapping("/kakao/oauth")
    public void kakao(@RequestParam("code") String code){
        WebClient webClient = WebClient.create("https://kauth.kakao.com/oauth/token");
        @SuppressWarnings("unchecked")
        Map<String, String> result = webClient.post()
                .uri(uri -> uri
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", 앱키)
                        .queryParam("redirect_uri", 리다이렉트 주소)
                        .queryParam("code", code).build())
                .header("Content-type","application/x-www-form-urlencoded;charset=utf-8")
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        System.out.println(result);

        String userInfo = WebClient.create("https://kapi.kakao.com/v2/user/me")
                .post()
                .header("Authorization","Bearer "+ result.get("access_token"))
                .header("Content-Type","application/json")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println(userInfo);
    }
}
