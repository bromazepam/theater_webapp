package com.theater.app.service;

import com.theater.app.domain.UserPayment;

public interface UserPaymentService {
    UserPayment findById(Long id);

    void removeById(Long id);
}
