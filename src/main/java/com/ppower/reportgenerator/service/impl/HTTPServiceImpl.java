package com.ppower.reportgenerator.service.impl;

import com.ppower.reportgenerator.boundary.BetDetailData;
import com.ppower.reportgenerator.service.HTTPService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class HTTPServiceImpl implements HTTPService {
    @Override
    public BetDetailData getBetDetailsOverHttp(String url) {
        BetDetailData betDetailData = null;
        RestTemplate restTemplate = new RestTemplate();
        try{
            HttpEntity<String> httpEntity = new HttpEntity<>(getPalmHttpHeader());
            ResponseEntity<BetDetailData> response =
                    restTemplate.exchange(url, HttpMethod.GET, httpEntity, BetDetailData.class);
            if (response.getBody() != null) {
                betDetailData = response.getBody();
            } else {
                String exceptionMessage = String
                        .format("Problem while reading response from service: %s .", url);
                throw new RuntimeException(exceptionMessage);
            }

        } catch (RestClientException e){
            String exceptionMessage = String
                    .format("Problem while accessing service: %s .", url);
            throw new RuntimeException(exceptionMessage,e.getCause());
        }
        return betDetailData;
    }

    private HttpHeaders getPalmHttpHeader() {
        HttpHeaders headers = new HttpHeaders();
        //set AUTHORIZATION if needed
        //headers.add(HttpHeaders.AUTHORIZATION, authToken);
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        return headers;
    }
}
