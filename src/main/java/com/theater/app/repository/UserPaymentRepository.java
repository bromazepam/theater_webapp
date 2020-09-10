package com.theater.app.repository;

import com.theater.app.domain.UserPayment;
import org.springframework.data.repository.CrudRepository;

public interface UserPaymentRepository extends CrudRepository<UserPayment, String> {
}
