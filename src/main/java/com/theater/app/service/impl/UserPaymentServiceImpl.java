package com.theater.app.service.impl;

import com.theater.app.domain.UserPayment;
import com.theater.app.repository.UserPaymentRepository;
import com.theater.app.service.UserPaymentService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPaymentServiceImpl implements UserPaymentService {

    private final UserPaymentRepository userPaymentRepository;

    public UserPaymentServiceImpl(UserPaymentRepository userPaymentRepository) {
        this.userPaymentRepository = userPaymentRepository;
    }

    @Override
    public UserPayment findById(Long id) {
        Optional<UserPayment> userPayment = userPaymentRepository.findById(id);
        return userPayment.get();
    }

    @Override
    public void removeById(Long id) {
        userPaymentRepository.deleteById(id);
    }
}
