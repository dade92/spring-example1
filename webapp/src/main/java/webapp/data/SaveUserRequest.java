package webapp.data;

import java.util.Objects;

public class SaveUserRequest {
    public UserRequest user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaveUserRequest that = (SaveUserRequest) o;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }
}
