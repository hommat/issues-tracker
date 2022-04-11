package org.example.issuestracker.issues.command.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandHandler;
import org.example.cqrs.event.EventSourcingHandler;
import org.example.issuestracker.issues.command.application.command.ChangeIssueContentCommand;
import org.example.issuestracker.issues.command.domain.issue.IssueContent;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueClosedException;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueContentSetException;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueNotFoundException;
import org.example.issuestracker.issues.command.domain.issue.Issue;

@RequiredArgsConstructor
public class ChangeIssueContentCommandHandler implements CommandHandler<ChangeIssueContentCommand> {
    private final EventSourcingHandler<Issue> eventSourcingHandler;

    /**
     * @throws IssueClosedException see {@link Issue#changeContent(IssueContent)}
     * @throws IssueContentSetException see {@link Issue#changeContent(IssueContent)}
     * @throws IssueNotFoundException if issue with given id does not exist
     */
    @Override
    public void handle(ChangeIssueContentCommand command) {
        var issue = eventSourcingHandler
                .getById(command.getIssueId())
                .orElseThrow(() -> new IssueNotFoundException(command.getIssueId()));

        issue.changeContent(command.getIssueContent());

        eventSourcingHandler.save(issue);
    }
}
