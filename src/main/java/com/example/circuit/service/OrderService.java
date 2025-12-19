package com.example.circuit.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Slf4j
@Service
public class OrderService {

    private final PaymentService paymentService;

    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @CircuitBreaker(name = "paymentCB", fallbackMethod = "fallback")
    public String order() {
        log.info("pay request");
        return paymentService.payApi();
    }

    public String fallback(Throwable t) {
        log.info("fallback now");
        return "결제 서비스 장애 발생, 현재 주문 실패(fallback), 잠시 후 다시 시도해주세요.";
        //cache return
        //message show
        //another seervice
        //장애 시 사용자 경험을 결정하는 부분
    }
}
