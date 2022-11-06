package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.login.AccountNotFoundException;
import java.util.HashMap;
import java.util.Map;

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

    public int getAccountId (int userId){
        int accountId;

        ResponseEntity<Account> response = restTemplate.exchange(baseUrl + "/account/user/" + userId, HttpMethod.GET, makeAuthEntity(), Account.class);
        if (response.hasBody()) {
            accountId = response.getBody().getAccountId();
        } else {
            accountId = 0;
        }






        return accountId;
    }



    public Map<Integer, String> listUsers(){
        Map<Integer, String> map = new HashMap<>();
        ResponseEntity<Map> response = restTemplate.exchange(baseUrl + "/tenmo_user/list", HttpMethod.GET, makeAuthEntity(), Map.class);
        if (response.hasBody()){
            map = response.getBody();
        }
        return map;


    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
