package com.mateuszziomek.issuestracker.users.query.application.event.handler;

import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.event.EventHandler;
import com.mateuszziomek.issuestracker.shared.domain.event.UserActivatedEvent;
import com.mateuszziomek.issuestracker.users.query.domain.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserActivatedEventHandler implements EventHandler<UserActivatedEvent> {
    private final UserRepository userRepository;

    @Override
    public void handle(UserActivatedEvent event) {
        userRepository
                .findById(event.getId())
                .ifPresent(user -> {
                    user.activate();
                    userRepository.save(user);
                });
    }
}
