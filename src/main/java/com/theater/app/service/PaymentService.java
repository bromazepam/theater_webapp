package com.theater.app.service;

import com.theater.app.domain.Payment;
import com.theater.app.domain.UserPayment;

public interface PaymentService {
    Payment setByUserPayment(UserPayment userPayment, Payment payment);
}
