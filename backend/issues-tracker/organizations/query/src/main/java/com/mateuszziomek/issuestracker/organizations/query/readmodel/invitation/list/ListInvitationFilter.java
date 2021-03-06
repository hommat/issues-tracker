package com.mateuszziomek.issuestracker.organizations.query.readmodel.invitation.list;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ListInvitationFilter {
    private final UUID memberId;
    private final UUID organizationId;
}
