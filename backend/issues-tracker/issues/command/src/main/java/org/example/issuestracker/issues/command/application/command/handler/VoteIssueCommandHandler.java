package org.example.issuestracker.issues.command.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandHandler;
import org.example.cqrs.event.EventSourcingHandler;
import org.example.issuestracker.issues.command.application.command.VoteIssueCommand;
import org.example.issuestracker.issues.command.domain.issue.Issue;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueClosedException;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueNotFoundException;
import org.example.issuestracker.issues.command.domain.vote.Vote;
import org.example.issuestracker.issues.command.domain.vote.exception.VoteAlreadyExistsException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoteIssueCommandHandler implements CommandHandler<VoteIssueCommand> {
    private final EventSourcingHandler<Issue> eventSourcingHandler;

    /**
     * @throws IssueClosedException see {@link Issue#vote(Vote)}
     * @throws IssueNotFoundException if issue with given id does not exist
     * @throws VoteAlreadyExistsException see {@link Issue#vote(Vote)}
     */
    @Override
    public void handle(VoteIssueCommand command) {
        var issue = eventSourcingHandler
                .getById(command.getIssueId())
                .orElseThrow(() -> new IssueNotFoundException(command.getIssueId()));

        var vote = new Vote(command.getVoterId(), command.getVoteType());
        issue.vote(vote);

        eventSourcingHandler.save(issue);
    }
}
