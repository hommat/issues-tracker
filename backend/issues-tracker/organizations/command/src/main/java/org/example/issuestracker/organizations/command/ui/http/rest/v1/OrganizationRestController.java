package org.example.issuestracker.organizations.command.ui.http.rest.v1;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.dispatcher.CommandDispatcher;
import org.example.issuestracker.organizations.command.application.command.CreateOrganizationProjectCommand;
import org.example.issuestracker.organizations.command.application.command.InviteOrganizationMemberCommand;
import org.example.issuestracker.organizations.command.application.command.JoinOrganizationMemberCommand;
import org.example.issuestracker.organizations.command.application.command.handler.CreateOrganizationProjectCommandHandler;
import org.example.issuestracker.organizations.command.application.command.handler.InviteOrganizationMemberCommandHandler;
import org.example.issuestracker.organizations.command.application.command.handler.JoinOrganizationMemberCommandHandler;
import org.example.issuestracker.organizations.command.application.gateway.member.exception.MemberNotFoundException;
import org.example.issuestracker.organizations.command.domain.invitation.exception.InvitationAlreadyPresentException;
import org.example.issuestracker.organizations.command.domain.invitation.exception.InvitationNotFoundException;
import org.example.issuestracker.organizations.command.domain.member.exception.MemberAlreadyPresentException;
import org.example.issuestracker.organizations.command.domain.organization.exception.OrganizationNotFoundException;
import org.example.issuestracker.organizations.command.domain.organization.exception.OrganizationOwnerNotValidException;
import org.example.issuestracker.organizations.command.ui.http.rest.v1.dto.CreateOrganizationDto;
import org.example.issuestracker.organizations.command.ui.http.rest.v1.dto.CreateOrganizationProjectDto;
import org.example.issuestracker.organizations.command.ui.http.rest.v1.dto.InviteOrganizationMemberDto;
import org.example.issuestracker.organizations.command.ui.http.rest.v1.mapper.CreateOrganizationDtoMapper;
import org.example.issuestracker.organizations.command.ui.http.rest.v1.mapper.CreateOrganizationProjectDtoMapper;
import org.example.issuestracker.organizations.command.ui.http.rest.v1.mapper.InviteOrganizationMemberDtoMapper;
import org.example.rest.v1.RestValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/organization-management")
@RequiredArgsConstructor
public class OrganizationRestController {
    private final CommandDispatcher commandDispatcher;

    /**
     * @throws RestValidationException see {@link CreateOrganizationDtoMapper#toCommand(UUID, UUID, CreateOrganizationDto)}
     */
    @PostMapping("/organizations")
    public ResponseEntity<UUID> createOrganization(@RequestBody CreateOrganizationDto createOrganizationDto) {
        var organizationId = UUID.randomUUID();
        // @TODO pass user id from header
        var registerUserCommand = CreateOrganizationDtoMapper.toCommand(
                organizationId,
                UUID.randomUUID(),
                createOrganizationDto
        );

        commandDispatcher.dispatch(registerUserCommand);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(organizationId);
    }

    /**
     * @throws InvitationAlreadyPresentException see {@link InviteOrganizationMemberCommandHandler#handle(InviteOrganizationMemberCommand)}
     * @throws MemberAlreadyPresentException see {@link InviteOrganizationMemberCommandHandler#handle(InviteOrganizationMemberCommand)}
     * @throws MemberNotFoundException see {@link InviteOrganizationMemberCommandHandler#handle(InviteOrganizationMemberCommand)}
     * @throws OrganizationNotFoundException see {@link InviteOrganizationMemberCommandHandler#handle(InviteOrganizationMemberCommand)}
     * @throws OrganizationOwnerNotValidException see {@link InviteOrganizationMemberCommandHandler#handle(InviteOrganizationMemberCommand)}
     * @throws RestValidationException see {@link InviteOrganizationMemberDtoMapper#toCommand(UUID, UUID, InviteOrganizationMemberDto)}
     */
    @PostMapping("/organizations/{organizationId}/invitations")
    public ResponseEntity inviteOrganizationMember(
            @PathVariable UUID organizationId,
            @RequestBody InviteOrganizationMemberDto inviteOrganizationMemberDto
    ) {
        // @TODO pass user id from header
        var inviteOrganizationMemberCommand = InviteOrganizationMemberDtoMapper.toCommand(
                organizationId,
                UUID.randomUUID(),
                inviteOrganizationMemberDto
        );

        commandDispatcher.dispatch(inviteOrganizationMemberCommand);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    /**
     * @throws InvitationNotFoundException see {@link JoinOrganizationMemberCommandHandler#handle(JoinOrganizationMemberCommand)}
     * @throws OrganizationNotFoundException see {@link JoinOrganizationMemberCommandHandler#handle(JoinOrganizationMemberCommand)}
     */
    @PostMapping("/organizations/{organizationId}/members")
    public ResponseEntity joinOrganizationMember(@PathVariable UUID organizationId) {
        // @TODO pass user id from header
        var inviteOrganizationMemberCommand = JoinOrganizationMemberCommand
                .builder()
                .organizationId(organizationId)
                .memberId(UUID.randomUUID())
                .build();

        commandDispatcher.dispatch(inviteOrganizationMemberCommand);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * @throws OrganizationNotFoundException see {@link CreateOrganizationProjectCommandHandler#handle(CreateOrganizationProjectCommand)}
     * @throws OrganizationOwnerNotValidException see {@link CreateOrganizationProjectCommandHandler#handle(CreateOrganizationProjectCommand)}
     * @throws RestValidationException see {@link CreateOrganizationProjectDtoMapper#toCommand(UUID, UUID, UUID, CreateOrganizationProjectDto)}
     */
    @PostMapping("/organizations/{organizationId}/projects}")
    public ResponseEntity<UUID> createOrganizationProject(
            @PathVariable UUID organizationId,
            @RequestBody CreateOrganizationProjectDto createOrganizationProjectDto
    ) {
        var projectId = UUID.randomUUID();
        // @TODO pass user id from header
        var registerUserCommand = CreateOrganizationProjectDtoMapper.toCommand(
                organizationId,
                projectId,
                UUID.randomUUID(),
                createOrganizationProjectDto
        );

        commandDispatcher.dispatch(registerUserCommand);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(projectId);
    }
}
