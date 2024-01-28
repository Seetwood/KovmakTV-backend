package dev.vorstu.db.repositories;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import dev.vorstu.db.entities.auth.AuthUserEntity;

import java.util.Optional;

@Repository
public interface AuthUserRepository extends PagingAndSortingRepository<AuthUserEntity, Long> {
    Optional<AuthUserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
    void deleteByUsername(String username);
}