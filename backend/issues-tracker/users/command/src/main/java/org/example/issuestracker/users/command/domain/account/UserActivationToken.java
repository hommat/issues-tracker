package org.example.issuestracker.users.command.domain.account;

import java.util.Objects;
import java.util.UUID;

public record UserActivationToken(UUID value) {
    public static UserActivationToken generate() {
        return new UserActivationToken(UUID.randomUUID());
    }

    public static UserActivationToken fromString(String value) {
        return new UserActivationToken(UUID.fromString(value));
    }

    public UserActivationToken {
        Objects.requireNonNull(value);
    }
}
