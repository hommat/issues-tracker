package org.example.issuestracker.issues.command.application.command;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandBuilder;
import org.example.issuestracker.issues.command.domain.issue.IssueContent;
import org.example.issuestracker.issues.command.domain.issue.IssueCreatorId;
import org.example.issuestracker.issues.command.domain.issue.IssueId;
import org.example.issuestracker.issues.command.domain.issue.IssueName;
import org.example.issuestracker.issues.command.domain.organization.OrganizationId;
import org.example.issuestracker.issues.command.domain.project.ProjectId;
import org.example.issuestracker.shared.domain.valueobject.IssueType;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class OpenIssueCommand {
    private final IssueId issueId;
    private final OrganizationId organizationId;
    private final ProjectId projectId;
    private final IssueCreatorId issueCreatorId;
    private final IssueType issueType;
    private final IssueContent issueContent;
    private final IssueName issueName;

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
        private UUID organizationId;

        @NotNull
        private UUID projectId;

        @NotNull
        private UUID issueCreatorId;

        @NotNull
        private IssueType issueType;

        @NotBlank
        private String issueContent;

        @NotBlank
        private String issueName;

        public OpenIssueCommandBuilder issueId(UUID issueId) {
            this.issueId = issueId;
            return this;
        }

        public OpenIssueCommandBuilder organizationId(UUID organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public OpenIssueCommandBuilder projectId(UUID projectId) {
            this.projectId = projectId;
            return this;
        }

        public OpenIssueCommandBuilder issueCreatorId(UUID issueCreatorId) {
            this.issueCreatorId = issueCreatorId;
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

        @Override
        protected OpenIssueCommand create() {
            return new OpenIssueCommand(
                    new IssueId(issueId),
                    new OrganizationId(organizationId),
                    new ProjectId(projectId),
                    new IssueCreatorId(issueCreatorId),
                    issueType,
                    new IssueContent(issueContent),
                    new IssueName(issueName)
            );
        }
    }
}
