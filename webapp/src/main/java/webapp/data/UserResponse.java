package webapp.data;

public class UserResponse {
    private final Outcome result;

    public UserResponse(Outcome result) {
        this.result = result;
    }

    public Outcome getResult() {
        return result;
    }
}
