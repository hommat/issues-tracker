package com.mateuszziomek.issuestracker.issues.command.ui.http.rest.v1;

import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationServiceUnavailableException;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.exception.CommentContentSetException;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.exception.CommentHiddenException;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.exception.CommentNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.exception.CommentWithIdExistsException;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.exception.*;
import com.mateuszziomek.issuestracker.issues.command.domain.vote.exception.VoteAlreadyExistsException;
import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationMemberNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationProjectNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.exception.*;
import com.mateuszziomek.rest.v1.RestErrorResponse;
import com.mateuszziomek.rest.v1.RestValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class IssueRestControllerAdvice {
    @ExceptionHandler(RestValidationException.class)
    public ResponseEntity<RestErrorResponse> handle(RestValidationException ex) {
        var errorResponse = new RestErrorResponse("Validation failed", ex.getErrors());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(IssueNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handle(IssueNotFoundException ex) {
        var errorResponse = new RestErrorResponse("Issue not found");

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(IssueClosedException.class)
    public ResponseEntity<RestErrorResponse> handle(IssueClosedException ex) {
        var errorResponse = new RestErrorResponse("Issue is closed");

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(IssueTypeSetException.class)
    public ResponseEntity<RestErrorResponse> handle(IssueTypeSetException ex) {
        var errorResponse = new RestErrorResponse("Issue type is already set");

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(IssueContentSetException.class)
    public ResponseEntity<RestErrorResponse> handle(IssueContentSetException ex) {
        var errorResponse = new RestErrorResponse("Issue content is already set");

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(IssueNameSetException.class)
    public ResponseEntity<RestErrorResponse> handle(IssueNameSetException ex) {
        var errorResponse = new RestErrorResponse("Issue name is already set");

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(VoteAlreadyExistsException.class)
    public ResponseEntity<RestErrorResponse> handle(VoteAlreadyExistsException ex) {
        var errorResponse = new RestErrorResponse("Vote already exists");

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(CommentWithIdExistsException.class)
    public ResponseEntity<RestErrorResponse> handle(CommentWithIdExistsException ex) {
        var errorResponse = new RestErrorResponse("Comment with id already exist");

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handle(CommentNotFoundException ex) {
        var errorResponse = new RestErrorResponse("Comment not found");

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(CommentHiddenException.class)
    public ResponseEntity<RestErrorResponse> handle(CommentHiddenException ex) {
        var errorResponse = new RestErrorResponse("Comment is hidden");

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(CommentContentSetException.class)
    public ResponseEntity<RestErrorResponse> handle(CommentContentSetException ex) {
        var errorResponse = new RestErrorResponse("Comment content is already set");

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(OrganizationNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handle(OrganizationNotFoundException ex) {
        var errorResponse = new RestErrorResponse("Organization not found");

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(OrganizationProjectNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handle(OrganizationProjectNotFoundException ex) {
        var errorResponse = new RestErrorResponse("Project not found");

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(OrganizationMemberNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handle(OrganizationMemberNotFoundException ex) {
        var errorResponse = new RestErrorResponse("User is not member of organization");

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(errorResponse);
    }

    @ExceptionHandler(OrganizationServiceUnavailableException.class)
    public ResponseEntity<RestErrorResponse> handle(OrganizationServiceUnavailableException ex) {
        var errorResponse = new RestErrorResponse("Service unavailable");

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(errorResponse);
    }
}
