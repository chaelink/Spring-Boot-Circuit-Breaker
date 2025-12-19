# 🔌 Spring Boot Circuit Breaker 실습 (Resilience4j)

Java와 Spring Boot 환경에서 **Resilience4j Circuit Breaker**를 활용해  
외부 서비스 장애 상황에서의 **Fault Tolerance(장애 허용)** 패턴을 실습한 프로젝트입니다.

결제(Payment) API가 실패하거나 지연되는 상황을 가정하여,  
서킷 브레이커가 어떻게 동작하는지 직접 확인합니다.

---
<img width="686" height="326" alt="image" src="https://github.com/user-attachments/assets/88a53bb4-12f9-487f-9b8c-eaa67fafd36d" />

<br>
<br>



## 📌 주요 기능

- Resilience4j Circuit Breaker 적용
- 실패 시 Fallback 처리
- 설정값 기반 OPEN / HALF_OPEN / CLOSED 상태 전환
- Spring Actuator로 서킷 상태 확인
- Postman Runner로 반복 요청 테스트

---

## 🛠 기술 스택

- Java 21
- Spring Boot 3.x
- Spring Web
- Spring AOP
- Spring Actuator
- Resilience4j 2.2.0
- Gradle

---

## 📂 프로젝트 구조

com.example.circuit
┣ controller
┃ ┗ OrderController.java
┣ service
┃ ┣ OrderService.java
┃ ┗ PaymentService.java
┗ CircuitApplication.java

---

## ⚙️ Resilience4j 설정 (application.yml)

```yaml
spring:
  application:
    name: circuit

resilience4j:
  circuitbreaker:
    instances:
      paymentCB:
        slidingWindowSize: 10
        minimumNumberOfCalls: 5
        failureRateThreshold: 50
        waitDurationInOpenState: 10s
        permittedNumberOfCallsInHalfOpenState: 3
        slowCallDurationThreshold: 2s
        slowCallRateThreshold: 50

management:
  endpoints:
    web:
      exposure:
        include: health, info, circuitbreakers
