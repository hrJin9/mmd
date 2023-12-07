package com.todos.mmd.repository.redis;

import com.todos.mmd.auth.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    // TODO : redis 왜 저장이 안되는거지..?
}
