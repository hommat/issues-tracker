package com.mateuszziomek.issuestracker.notifications.domain.user;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface UserRepository extends PagingAndSortingRepository<User, UUID> { }
