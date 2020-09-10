package com.theater.app.service;

import com.theater.app.domain.UserPayment;

public interface UserPaymentService {
    UserPayment findById(String id);

    void removeById(String id);
}
