package com.theater.app.repository;

import com.theater.app.domain.User;
import com.theater.app.domain.security.PasswordResetToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.stream.Stream;

public interface PasswordResetTokenRepository extends MongoRepository<PasswordResetToken, String> {

    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(User user);

    Stream<PasswordResetToken> findAllByExpiryDateLessThan(Date now);

//    @Modifying
//    @Query("delete from PasswordResetToken t where t.expiryDate <= ?1")
//    void deleteAllExpiredSince(Date now);
}
