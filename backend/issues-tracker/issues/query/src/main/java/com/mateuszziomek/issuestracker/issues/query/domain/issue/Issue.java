package com.mateuszziomek.issuestracker.issues.query.domain.issue;

import com.mateuszziomek.issuestracker.issues.query.domain.comment.Comment;
import com.mateuszziomek.issuestracker.issues.query.domain.member.Member;
import com.mateuszziomek.issuestracker.issues.query.domain.Vote;
import com.mateuszziomek.issuestracker.shared.domain.event.*;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.IssueStatus;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.IssueType;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.IssueUpdateType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
public class Issue {
    private UUID id;
    private UUID projectId;
    private UUID organizationId;
    private String name;
    private String content;
    private IssueStatus status;
    private IssueType type;
    private List<Vote> votes;
    private List<IssueUpdate> updates;
    private List<Comment> comments;
    private Member creator;

    public static Issue create(IssueOpenedEvent event, Member creator) {
        var issue = new Issue();
        issue.id = event.getId();
        issue.projectId = event.getProjectId();
        issue.organizationId = event.getOrganizationId();
        issue.content = event.getIssueContent();
        issue.name = event.getIssueName();
        issue.status = IssueStatus.OPENED;
        issue.type = event.getIssueType();
        issue.votes = new ArrayList<>();
        issue.updates = new ArrayList<>();
        issue.comments = new ArrayList<>();
        issue.creator = creator;
        return issue;
    }

    public void rename(IssueRenamedEvent event) {
        updates.add(new IssueUpdate(IssueUpdateType.RENAMED, event.getDate(), name, event.getIssueName()));
        name = event.getIssueName();
    }

    public void close() {
        status = IssueStatus.CLOSED;
    }

    public void changeContent(IssueContentChangedEvent event) {
        updates.add(new IssueUpdate(IssueUpdateType.CONTENT_CHANGED, event.getDate(), content, event.getIssueContent()));
        content = event.getIssueContent();
    }

    public void changeType(IssueTypeChangedEvent event) {
        updates.add(new IssueUpdate(IssueUpdateType.TYPE_CHANGED, event.getDate(), type, event.getIssueType()));
        type = event.getIssueType();
    }

    public void vote(IssueVotedEvent event) {
        var vote = new Vote(event.getMemberId(), event.getVoteType());

        votes.removeIf(v -> v.getMemberId().equals(event.getMemberId()));
        votes.add(vote);
    }

    public void comment(Comment comment) {
        comments.add(comment);
    }

    public void changeCommentContent(IssueCommentContentChangedEvent event) {
        findCommentWithId(event.getCommentId()).ifPresent(comment -> comment.changeContent(event));
    }

    public void hideComment(IssueCommentHiddenEvent event) {
        findCommentWithId(event.getCommentId()).ifPresent(Comment::hide);
    }

    public void voteComment(IssueCommentVotedEvent event) {
        var vote = new Vote(event.getMemberId(), event.getVoteType());
        findCommentWithId(event.getCommentId()).ifPresent(comment -> comment.vote(vote));
    }

    private Optional<Comment> findCommentWithId(UUID commentId) {
        return comments
                .stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findFirst();
    }
}
