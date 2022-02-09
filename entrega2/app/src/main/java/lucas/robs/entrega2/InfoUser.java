package lucas.robs.entrega2;

import android.net.Uri;

public class InfoUser {
    String name;
    String id;
    String email;

    public InfoUser(String name, String id, String email) {
        this.name = name;
        this.id = id;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }


    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "InfoUser{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
