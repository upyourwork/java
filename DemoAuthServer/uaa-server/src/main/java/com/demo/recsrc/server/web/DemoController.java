package com.demo.recsrc.server.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class DemoController {

    public DemoController(WebClient webClient) {
        this.webClient = webClient;
    }

    private WebClient webClient;
    @GetMapping("/demo")
    public String[] getArticles() {
        return new String[]{"Dummy Text-1", "Dummy Text-2", "Dummy Text-3"};
    }

    @GetMapping("/token")
    public String getToken() {
        return this.webClient
                .post()
                .uri("/oauth2/token")
                .body(BodyInserters.fromFormData("grant_type", "client_credentials")
                        .with("client_id", "demo-client").with("client_secret", "secret"))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}