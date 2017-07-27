package app.atr.mobitribe.com.app_time_recorder.model;


public class Register {

    private String first_name;
    private String last_name;
    private String username;
    private String phone;
    private String cnic;
    private String gender;
    private String address;
    private String city;
    private String password;
    private String retype_password;
    private String security_keywords;
    private String device_model;
    private String android_version;
    private String imei_numer;
    private String cellular_network;
    private String age="20";
    private String education="bachelors";
    private String sector="Sec 10";


    public String getCellular_network() {
        return cellular_network;
    }

    public void setCellular_network(String cellular_network) {
        this.cellular_network = cellular_network;
    }

    public String getImei_numer() {
        return imei_numer;
    }

    public void setImei_numer(String imei_numer) {
        this.imei_numer = imei_numer;
    }

    public String getAndroid_version() {
        return android_version;
    }

    public void setAndroid_version(String android_version) {
        this.android_version = android_version;
    }

    public String getDevice_model() {
        return device_model;
    }

    public void setDevice_model(String device_model) {
        this.device_model = device_model;
    }


    public String getFirst_name() {
return first_name;
}

public void setFirst_name(String first_name) {
this.first_name = first_name;
}

public String getLast_name() {
return last_name;
}

public void setLast_name(String last_name) {
this.last_name = last_name;
}

public String getUsername() {
return username;
}

public void setUsername(String username) {
this.username = username;
}

public String getPhone() {
return phone;
}

public void setPhone(String phone) {
this.phone = phone;
}

public String getCnic() {
return cnic;
}

public void setCnic(String cnic) {
this.cnic = cnic;
}

public String getGender() {
return gender;
}

public void setGender(String gender) {
this.gender = gender;
}

public String getAddress() {
return address;
}

public void setAddress(String address) {
this.address = address;
}

public String getCity() {
return city;
}

public void setCity(String city) {
this.city = city;
}

public String getPassword() {
return password;
}

public void setPassword(String password) {
this.password = password;
}

public String getRetype_password() {
return retype_password;
}

public void setRetype_password(String retype_password) {
this.retype_password = retype_password;
}

public String getSecurity_keywords() {
return security_keywords;
}

public void setSecurity_keywords(String security_keywords) {
this.security_keywords = security_keywords;
}

}