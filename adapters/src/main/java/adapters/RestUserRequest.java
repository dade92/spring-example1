package adapters;

public record RestUserRequest(
    String username,
    String password
) {
}