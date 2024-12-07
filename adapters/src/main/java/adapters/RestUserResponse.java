package adapters;

public class RestUserResponse {
    private RestUser user;

    public RestUserResponse() {
    }

    public RestUser getUser() {
        return user;
    }

    public void setUser(RestUser user) {
        this.user = user;
    }
}

record RestUser(
    long id,
    String username,
    String password
) {}