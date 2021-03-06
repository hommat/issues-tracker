package com.mateuszziomek.issuestracker.issues.command.application.command.handler;

import com.mateuszziomek.cqrs.event.sourcinghandler.EventSourcingHandler;
import com.mateuszziomek.issuestracker.issues.command.application.service.organization.OrganizationService;
import com.mateuszziomek.issuestracker.issues.command.application.service.organization.exception.OrganizationMemberNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.application.service.organization.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.application.service.organization.exception.OrganizationProjectNotFoundException;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.command.CommandHandler;
import com.mateuszziomek.issuestracker.issues.command.application.command.VoteIssueCommentCommand;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.CommentId;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.exception.CommentNotFoundException;
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
public class VoteIssueCommentCommandHandler implements CommandHandler<VoteIssueCommentCommand> {
    private final EventSourcingHandler<Issue> eventSourcingHandler;
    private final OrganizationService organizationService;

    /**
     * @throws CommentNotFoundException see {@link Issue#voteComment(CommentId, Vote)}
     * @throws IssueClosedException see {@link Issue#voteComment(CommentId, Vote)}
     * @throws IssueNotFoundException if issue with given id does not exist
     * @throws OrganizationMemberNotFoundException see {@link OrganizationService#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     * @throws OrganizationNotFoundException see {@link OrganizationService#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     * @throws OrganizationProjectNotFoundException see {@link OrganizationService#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     * @throws VoteAlreadyExistsException see {@link Issue#voteComment(CommentId, Vote)}
     */
    @Override
    public void handle(VoteIssueCommentCommand command) {
        organizationService.ensureOrganizationHasProjectAndMember(command.getOrganizationDetails());

        var issue = eventSourcingHandler
                .getById(command.getIssueId())
                .orElseThrow(() -> new IssueNotFoundException(command.getIssueId()));

        var vote = new Vote(VoterId.fromMemberId(command.getOrganizationDetails().memberId()), command.getVoteType());
        issue.voteComment(command.getCommentId(), vote);

        eventSourcingHandler.save(issue);
    }
}
