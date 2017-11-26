package app.atr.mobitribe.com.app_time_recorder.model.responses;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Uzair Qureshi
 * Date:  8/28/17.
 * Description:
 */

public class Companies {
    private ArrayList<Subadmin> subadmins;

    public ArrayList<Subadmin> getSubadmins() {
        return subadmins;
    }






public class Subadmin {
    private Integer id;
    private String name;
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

}
