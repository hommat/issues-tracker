package org.example.issuestracker.organizations.query.readmodel.detailsorganization;

import org.example.issuestracker.organizations.query.domain.Member;
import org.example.issuestracker.organizations.query.domain.Organization;
import org.example.issuestracker.organizations.query.domain.Project;
import org.example.issuestracker.shared.readmodel.DetailsOrganization;

import java.util.List;

public class DetailsOrganizationMapper {
    private DetailsOrganizationMapper() {}

    public static DetailsOrganization fromModel(Organization organization) {
        return DetailsOrganization
                .builder()
                .id(organization.getId())
                .ownerId(organization.getOwnerId())
                .members(mapMembers(organization.getMembers()))
                .projects(mapProjects(organization.getProjects()))
                .build();
    }

    private static List<DetailsOrganization.Member> mapMembers(List<Member> members) {
        return members
                .stream()
                .map(DetailsOrganizationMapper::mapMember)
                .toList();
    }

    private static DetailsOrganization.Member mapMember(Member member) {
        return DetailsOrganization.Member
                .builder()
                .id(member.getId())
                .build();
    }

    private static List<DetailsOrganization.Project> mapProjects(List<Project> projects) {
        return projects
                .stream()
                .map(DetailsOrganizationMapper::mapProject)
                .toList();
    }

    private static DetailsOrganization.Project mapProject(Project project) {
        return DetailsOrganization.Project
                .builder()
                .id(project.getId())
                .name(project.getName())
                .build();
    }
}
