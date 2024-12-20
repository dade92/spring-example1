package adapters;

import domain.User;
import domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.function.Supplier;

public class RetryUserRepository implements UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(RetryUserRepository.class);

    private final UserRepository delegate;
    private final int maxRetries;
    private final long initialBackoffMillis;

    public RetryUserRepository(UserRepository delegate, int maxRetries, long initialBackoffMillis) {
        this.delegate = delegate;
        this.maxRetries = maxRetries;
        this.initialBackoffMillis = initialBackoffMillis;
    }

    @Override
    public Optional<User> fetch(long userId) {
        return retry(() -> delegate.fetch(userId), maxRetries, initialBackoffMillis);
    }

    @Override
    public Optional<Boolean> addUser(User user) {
        return delegate.addUser(user);
    }

    @Override
    public Optional<User> fetchByUsername(String username) {
        return retry(() -> delegate.fetchByUsername(username), maxRetries, initialBackoffMillis);
    }

    private <T> Optional<T> retry(Supplier<Optional<T>> action, int maxRetries, long initialBackoffMillis) {
        int attempt = 0;
        long backoff = initialBackoffMillis;

        while (attempt < maxRetries) {
            try {
                Optional<T> result = action.get();

                if (result.isPresent()) {
                    return result;
                } else {
                    logger.warn("Attempt {} returned empty. Retrying...", attempt + 1);
                }
            } catch (Exception e) {
                logger.warn("Attempt {} failed due to an exception. Retrying in {} ms...", attempt + 1, backoff, e);
            }

            attempt++;
            if (attempt >= maxRetries) {
                logger.error("Max retry attempts reached.");
                break;
            }

            try {
                Thread.sleep(backoff);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt(); // Restore the interrupt status
                logger.error("Retry interrupted: {}", ie.getMessage(), ie);
                break;
            }

            backoff *= 2;
        }

        return Optional.empty();
    }
}
