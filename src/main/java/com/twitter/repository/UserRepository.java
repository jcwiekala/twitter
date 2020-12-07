package com.twitter.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.twitter.domain.User;

/**
 * Reactive repository working on {@link User} documents
 */
@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
}
