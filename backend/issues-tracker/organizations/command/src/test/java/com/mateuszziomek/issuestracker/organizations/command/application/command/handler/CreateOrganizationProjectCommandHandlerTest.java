package com.mateuszziomek.issuestracker.organizations.command.application.command.handler;

import com.mateuszziomek.cqrs.event.sourcinghandler.EventSourcingHandler;
import com.mateuszziomek.issuestracker.organizations.command.domain.organization.exception.OrganizationNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.mateuszziomek.issuestracker.organizations.command.application.command.handler.helpers.OrganizationCommandData.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CreateOrganizationProjectCommandHandlerTest {
    @Test
    void eventSourcingHandlerIsNotCalledWhenOrganizationDoesNotExist() {
        // Arrange
        var eventSourcingHandler = mock(EventSourcingHandler.class);
        when(eventSourcingHandler.getById(any())).thenReturn(Optional.empty());
        var sut = new CreateOrganizationProjectCommandHandler(eventSourcingHandler);

        // Assert
        assertThatExceptionOfType(OrganizationNotFoundException.class)
                .isThrownBy(() -> sut.handle(CREATE_ORGANIZATION_PROJECT_COMMAND));
        verify(eventSourcingHandler, times(0)).save(any());
    }
}
