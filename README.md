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
```

<img width="1142" height="667" alt="image" src="https://github.com/user-attachments/assets/fddaf478-2232-40e6-815c-e93c324e2712" />


- 🟢 resilience4j_circuitbreaker_not_permitted_calls_total
  - 서킷 OPEN 이후 차단된 요청 수 누적   
- 🟡 resilience4j_circuitbreaker_state{state="open"}
  - 0 : CLOSED(정상), 1 : OPEN(차단)  
- 🔵 resilience4j_circuitbreaker_failure_rate
  - 실패 비율(OPEN 직전에 임계치까지 도달) 

### 시각화 포인트
1. 초기에는 CLOSED 상태에서 성공/실패가 섞여 발생

2. 실패율과 지연 비율이 임계치를 넘으면서

3. 특정 시점에 Circuit Breaker가 OPEN으로 전환

4. 이후 요청은 실제 서비스 호출 없이 차단(not_permitted)

5. Grafana 그래프에서 OPEN 상태와 차단 요청 급증이 동시에 관찰됨


<br>
<br>
<img width="1062" height="475" alt="image" src="https://github.com/user-attachments/assets/e709cf01-e273-43fe-8036-ba857b4fe462" />

