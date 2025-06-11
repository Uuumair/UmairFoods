package com.umair.service;

import com.stripe.exception.StripeException;
import com.umair.model.Order;
import com.umair.response.PaymentResponse;

public interface PaymentService {
    public PaymentResponse createPaymentLink(Order order) throws StripeException;


}
