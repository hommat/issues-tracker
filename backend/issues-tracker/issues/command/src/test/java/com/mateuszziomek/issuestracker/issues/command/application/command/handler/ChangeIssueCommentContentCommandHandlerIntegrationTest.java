package com.mateuszziomek.issuestracker.issues.command.application.command.handler;

import com.mateuszziomek.cqrs.event.producer.EventProducer;
import com.mateuszziomek.cqrs.event.store.EventStoreRepository;
import com.mateuszziomek.issuestracker.issues.command.application.command.handler.helpers.IssueCommandHandlerIntegrationTest;
import com.mateuszziomek.issuestracker.issues.command.application.service.organization.OrganizationService;
import com.mateuszziomek.issuestracker.issues.command.projection.OrganizationRepository;
import com.mateuszziomek.issuestracker.shared.domain.event.IssueCommentContentChangedEvent;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.mateuszziomek.issuestracker.issues.command.application.command.handler.helpers.IssueCommandData.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ChangeIssueCommentContentCommandHandlerIntegrationTest extends IssueCommandHandlerIntegrationTest {
    @Test
    void changingIssueCommentContentSavesEventToDbAndSendsMessageToBroker() {
        // Arrange
        var eventProducer = mock(EventProducer.class);
        var eventStoreRepository = mock(EventStoreRepository.class);
        var organizationRepository = createOrganizationRepositoryMock();

        when(eventStoreRepository.findByAggregateId(ISSUE_ID))
                .thenReturn(List.of(ISSUE_OPENED_EVENT_MODEL, ISSUE_COMMENTED_EVENT_MODEL));

        var sut = createHandler(
                eventProducer,
                eventStoreRepository,
                organizationRepository
        );

        // Act
        sut.handle(CHANGE_ISSUE_COMMENT_CONTENT_COMMAND);

        // Assert
        verify(eventProducer, times(1)).produce(
                eq("IssueCommentContentChangedEvent"),
                argThat(event -> hasIssueCommentContentChangedEventCorrectedData((IssueCommentContentChangedEvent) event))
        );

        verify(eventStoreRepository, times(1)).save(
                argThat(eventModel -> (
                        eventModel.eventType().equals("IssueCommentContentChangedEvent")
                        && eventModel.aggregateId().getValue().equals(ISSUE_UUID))
                        && hasIssueCommentContentChangedEventCorrectedData((IssueCommentContentChangedEvent) eventModel.eventData())
                )
        );
    }

    private ChangeIssueCommentContentCommandHandler createHandler(
            EventProducer eventProducer,
            EventStoreRepository eventStoreRepository,
            OrganizationRepository organizationRestClient
    ) {
        var eventStore = createEventStore(eventStoreRepository, eventProducer);
        var eventSourcingHandler = createSourcingHandler(eventStore);
        var organizationService = new OrganizationService(organizationRestClient);

        return new ChangeIssueCommentContentCommandHandler(eventSourcingHandler, organizationService);
    }

    private boolean hasIssueCommentContentChangedEventCorrectedData(IssueCommentContentChangedEvent event) {
        var command = CHANGE_ISSUE_COMMENT_CONTENT_COMMAND;
        var organizationDetails = command.getOrganizationDetails();

        return (
                event.getId().equals(command.getIssueId().getValue())
                && event.getCommentId().equals(command.getCommentId().getValue())
                && event.getCommentContent().equals(command.getCommentContent().text())
                && event.getMemberId().equals(organizationDetails.memberId().getValue())
                && event.getOrganizationId().equals(organizationDetails.organizationId().getValue())
                && event.getProjectId().equals(organizationDetails.projectId().getValue())
        );
    }
}