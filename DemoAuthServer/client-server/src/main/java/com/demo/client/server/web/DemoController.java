package com.demo.client.server.web;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@RestController
public class DemoController {

    public DemoController(WebClient webClient) {
        this.webClient = webClient;
    }

    private WebClient webClient;

    @GetMapping(value = "/articles")
    public String[] getArticles(
            @RegisteredOAuth2AuthorizedClient("uaa-client-authorization-code") OAuth2AuthorizedClient authorizedClient
    ) {
        return this.webClient
                .get()
                .uri("http://127.0.0.1:8090/data")
                .attributes(oauth2AuthorizedClient(authorizedClient))
                .retrieve()
                .bodyToMono(String[].class)
                .block();
    }

    @GetMapping(value = "/demo/token")
    public String getToken(
            @RegisteredOAuth2AuthorizedClient("uaa-client-authorization-code") OAuth2AuthorizedClient authorizedClient) {
        return this.webClient
                .get()
                .uri("http://127.0.0.1:8090/token")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}