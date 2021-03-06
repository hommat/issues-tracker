package com.mateuszziomek.issuestracker.users.command.application.command;

import com.mateuszziomek.issuestracker.users.command.domain.user.UserEmail;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserPlainPassword;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.command.CommandBuilder;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserId;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class RegisterUserCommand {
    private final UserId userId;
    private final UserEmail userEmail;
    private final UserPlainPassword userPlainPassword;

    public static RegisterUserCommandBuilder builder() {
        return new RegisterUserCommandBuilder();
    }

    public static class RegisterUserCommandBuilder extends CommandBuilder<RegisterUserCommandBuilder, RegisterUserCommand> {
        public static final String USER_EMAIL_FIELD_NAME = "userEmail";
        public static final String USER_PLAIN_PASSWORD_FIELD_NAME = "userPlainPassword";

        @NotNull
        private UUID userId;

        @NotBlank
        @Email
        private String userEmail;

        @NotBlank
        private String userPlainPassword;

        public RegisterUserCommandBuilder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public RegisterUserCommandBuilder userEmail(String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public RegisterUserCommandBuilder userPlainPassword(String userPlainPassword) {
            this.userPlainPassword = userPlainPassword;
            return this;
        }

        @Override
        protected RegisterUserCommand create() {
            return new RegisterUserCommand(
                    new UserId(userId),
                    new UserEmail(userEmail),
                    new UserPlainPassword(userPlainPassword)
            );
        }
    }
}
