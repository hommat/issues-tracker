package com.mateuszziomek.issuestracker.issues.query.infrastructure.domain;

import com.mateuszziomek.issuestracker.issues.query.domain.issue.Issue;
import com.mateuszziomek.issuestracker.issues.query.domain.issue.IssueRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MongoIssueRepository extends ReactiveMongoRepository<Issue, UUID>, IssueRepository { }
