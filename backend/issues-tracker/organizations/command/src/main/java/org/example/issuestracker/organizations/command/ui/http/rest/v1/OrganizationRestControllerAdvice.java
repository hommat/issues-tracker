package org.example.issuestracker.organizations.command.ui.http.rest.v1;

import org.example.issuestracker.organizations.command.application.gateway.member.exception.MemberNotFoundException;
import org.example.issuestracker.organizations.command.domain.invitation.exception.InvitationAlreadyPresentException;
import org.example.issuestracker.organizations.command.domain.invitation.exception.InvitationNotFoundException;
import org.example.issuestracker.organizations.command.domain.member.exception.MemberAlreadyPresentException;
import org.example.issuestracker.organizations.command.domain.organization.exception.OrganizationNotFoundException;
import org.example.issuestracker.organizations.command.domain.organization.exception.OrganizationOwnerNotValidException;
import org.example.rest.v1.RestErrorResponse;
import org.example.rest.v1.RestValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrganizationRestControllerAdvice {
    @ExceptionHandler(RestValidationException.class)
    public ResponseEntity<RestErrorResponse> handle(RestValidationException ex) {
        var errorResponse = new RestErrorResponse("Validation failed", ex.getErrors());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(OrganizationNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handle(OrganizationNotFoundException ex) {
        var errorResponse = new RestErrorResponse("Organization not found");

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(OrganizationOwnerNotValidException.class)
    public ResponseEntity<RestErrorResponse> handle(OrganizationOwnerNotValidException ex) {
        var errorResponse = new RestErrorResponse("Organization owner not valid");

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(errorResponse);
    }

    @ExceptionHandler(InvitationNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handle(InvitationNotFoundException ex) {
        var errorResponse = new RestErrorResponse("Invitation not found");

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(InvitationAlreadyPresentException.class)
    public ResponseEntity<RestErrorResponse> handle(InvitationAlreadyPresentException ex) {
        var errorResponse = new RestErrorResponse("Invitation already present");

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(MemberAlreadyPresentException.class)
    public ResponseEntity<RestErrorResponse> handle(MemberAlreadyPresentException ex) {
        var errorResponse = new RestErrorResponse("Member already present");

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handle(MemberNotFoundException ex) {
        var errorResponse = new RestErrorResponse("Member not found");

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }
}