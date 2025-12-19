package com.example.circuit.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PaymentService {

    private final Random random = new Random();

    public String payApi() {
        int chance = random.nextInt(10);

        if(chance < 5) {
            throw new RuntimeException("Payment Service error");
        }

        if(chance>=5 && chance<7) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return "PAYMENT SUCCESS";
    }
}
