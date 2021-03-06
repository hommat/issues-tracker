package com.mateuszziomek.issuestracker.issues.command.application.command;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.command.CommandBuilder;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueContent;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueId;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueName;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.IssueType;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class OpenIssueCommand {
    private final IssueId issueId;
    private final IssueType issueType;
    private final IssueContent issueContent;
    private final IssueName issueName;
    private final IssueOrganizationDetails organizationDetails;

    public static OpenIssueCommandBuilder builder() {
        return new OpenIssueCommandBuilder();
    }

    public static class OpenIssueCommandBuilder extends CommandBuilder<OpenIssueCommandBuilder, OpenIssueCommand> {
        public static final String ISSUE_TYPE_FIELD_NAME = "issueType";
        public static final String ISSUE_CONTENT_FIELD_NAME = "issueContent";
        public static final String ISSUE_NAME_FIELD_NAME = "issueName";

        @NotNull
        private UUID issueId;

        @NotNull
        private IssueType issueType;

        @NotBlank
        private String issueContent;

        @NotBlank
        private String issueName;

        @NotNull
        private IssueOrganizationDetails organizationDetails;

        public OpenIssueCommandBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        public OpenIssueCommandBuilder issueType(IssueType issueType) {
            this.issueType = issueType;
            return this;
        }

        public OpenIssueCommandBuilder issueContent(String issueContent) {
            this.issueContent = issueContent;
            return this;
        }

        public OpenIssueCommandBuilder issueName(String issueName) {
            this.issueName = issueName;
            return this;
        }

        public OpenIssueCommandBuilder organizationDetails(IssueOrganizationDetails organizationDetails) {
            this.organizationDetails = organizationDetails;
            return this;
        }

        @Override
        protected OpenIssueCommand create() {
            return new OpenIssueCommand(
                    new IssueId(issueId),
                    issueType,
                    new IssueContent(issueContent),
                    new IssueName(issueName),
                    organizationDetails
            );
        }
    }
}
