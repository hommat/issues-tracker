package org.example.issuestracker.users.command.domain.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.issuestracker.users.command.domain.user.UserId;

@RequiredArgsConstructor
@Getter
public class UserNotFoundException extends RuntimeException {
    private final transient UserId userId;
}