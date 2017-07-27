package app.atr.mobitribe.com.app_time_recorder.model;


public class LoginResponse extends Response {

    private String access_token;
    private String token_type;
    private String error;
    private String error_description;

    public String getAccess_token() {
        return access_token;
    }


    public String getToken_type() {
        return token_type;
    }

    public String getError_description() {
        return error_description;
    }


    public String getError() {
        return error;
    }

}