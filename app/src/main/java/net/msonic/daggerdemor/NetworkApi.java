package net.msonic.daggerdemor;

/**
 * Created by manuelzegarra on 11/17/16.
 */

public class NetworkApi {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean validateUser(String username, String password) {
        // imagine an actual network call here
        // for demo purpose return false in "real" life
        if (username == null || username.length() == 0) {
            return false;
        } else {
            return true;
        }
    }

}
