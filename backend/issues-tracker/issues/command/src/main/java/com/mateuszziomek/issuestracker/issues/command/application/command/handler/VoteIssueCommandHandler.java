package com.mateuszziomek.issuestracker.issues.command.application.command.handler;

import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.command.CommandHandler;
import com.mateuszziomek.cqrs.event.sourcinghandler.EventSourcingHandler;
import com.mateuszziomek.issuestracker.issues.command.application.command.VoteIssueCommand;
import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.OrganizationGateway;
import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationMemberNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationProjectNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.Issue;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.exception.IssueClosedException;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.exception.IssueNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.domain.vote.Vote;
import com.mateuszziomek.issuestracker.issues.command.domain.vote.VoterId;
import com.mateuszziomek.issuestracker.issues.command.domain.vote.exception.VoteAlreadyExistsException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoteIssueCommandHandler implements CommandHandler<VoteIssueCommand> {
    private final EventSourcingHandler<Issue> eventSourcingHandler;
    private final OrganizationGateway organizationGateway;

    /**
     * @throws IssueClosedException see {@link Issue#vote(Vote, IssueOrganizationDetails)}
     * @throws IssueNotFoundException if issue with given id does not exist
     * @throws OrganizationMemberNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     * @throws OrganizationNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     * @throws OrganizationProjectNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     * @throws VoteAlreadyExistsException see {@link Issue#vote(Vote, IssueOrganizationDetails)}
     */
    @Override
    public void handle(VoteIssueCommand command) {
        organizationGateway.ensureOrganizationHasProjectAndMember(command.getOrganizationDetails());

        var issue = eventSourcingHandler
                .getById(command.getIssueId())
                .orElseThrow(() -> new IssueNotFoundException(command.getIssueId()));

        var vote = new Vote(VoterId.fromMemberId(command.getOrganizationDetails().memberId()), command.getVoteType());
        issue.vote(vote, command.getOrganizationDetails());

        eventSourcingHandler.save(issue);
    }
}