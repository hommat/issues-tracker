package org.example.issuestracker.users.query.readmodel.listuser;

import org.example.issuestracker.shared.domain.valueobject.UserStatus;
import org.example.issuestracker.shared.readmodel.ListUser;

import java.util.List;

public interface ListUserFilterQueryBuilder {
    ListUserFilterQueryBuilder emailEquals(String email);
    ListUserFilterQueryBuilder statusEquals(UserStatus status);
    List<ListUser> execute();
}
