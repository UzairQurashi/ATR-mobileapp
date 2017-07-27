package app.atr.mobitribe.com.app_time_recorder.model;

import java.util.HashMap;

public class Login {

    private String username;
    private String password;
    private String client_id;
    private String client_secret;
    private String grant_type;

    public Login(String mEmail, String mPassword) {


        client_id = "1";
        client_secret = "secret";
        grant_type = "password";
        this.username = mEmail;
        this.password = mPassword;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public HashMap<String,String> getMapObject()
    {
        HashMap<String, String> map = new HashMap<>();
        map.put("username",username);
        map.put("client_id",getClient_id());
        map.put("client_secret",getClient_secret());
        map.put("password",password);
        map.put("grant_type",getGrant_type());
        return map;
    }
}