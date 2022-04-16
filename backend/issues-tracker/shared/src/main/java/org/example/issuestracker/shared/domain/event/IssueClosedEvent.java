package org.example.issuestracker.shared.domain.event;

import lombok.NoArgsConstructor;
import org.example.cqrs.event.BaseEvent;
import org.example.cqrs.event.EventBuilder;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
public class IssueClosedEvent extends BaseEvent {
    public static IssueClosedEventBuilder builder() {
        return new IssueClosedEventBuilder();
    }

    private IssueClosedEvent(String issueId) {
        super(issueId);
    }

    public static class IssueClosedEventBuilder extends EventBuilder<IssueClosedEventBuilder, IssueClosedEvent> {
        @NotNull
        private UUID issueId;

        public IssueClosedEventBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        @Override
        protected IssueClosedEvent create() {
            return new IssueClosedEvent(
                    issueId.toString()
            );
        }
    }
}