package org.example.issuestracker.issues.command.domain.issue.exception;

import org.example.issuestracker.issues.command.domain.comment.CommentId;

public class CommentNotFoundException extends RuntimeException {
    private CommentId commentId;

    public CommentNotFoundException(CommentId commentId) {
        this.commentId = commentId;
    }

    public CommentId getCommentId() {
        return commentId;
    }
}
