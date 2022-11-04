package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.login.AccountNotFoundException;

public class UserService {

    private User currentUser = null;
    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
    public UserService(String url){
        this.baseUrl = url;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public int getAccountId (){
        int accountId;

        ResponseEntity<Account> response = restTemplate.exchange(baseUrl + "/account/user/" + currentUser.getId(), HttpMethod.GET, makeAuthEntity(), Account.class);
        if (response.hasBody()) {
            accountId = response.getBody().getAccountId();
        } else {
            accountId=0;
        }




        return accountId;
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
