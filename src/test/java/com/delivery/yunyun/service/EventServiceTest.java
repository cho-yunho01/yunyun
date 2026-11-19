package com.delivery.yunyun.service;

import com.delivery.yunyun.domain.Customer;
import com.delivery.yunyun.domain.Event;
import com.delivery.yunyun.dto.request.event.JoinEventRequest;
import com.delivery.yunyun.repository.CustomerCouponRepository;
import com.delivery.yunyun.repository.CustomerRepository;
import com.delivery.yunyun.repository.EventRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class EventServiceTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerCouponRepository customerCouponRepository;

    @Autowired
    private RedisTemplate<String, Long> redisTemplate;

    private Long testEventId;
    private final int MAX_COUNT = 30; // 선착순 정원

    @BeforeEach
    void setUp() {
        Event event = Event.builder()
                .name("선착순 30명 할인 쿠폰 이벤트")
                .description("동시성 테스트용")
                .couponId(1L)
                .maxCount(MAX_COUNT)
                .startAt(LocalDateTime.now().minusDays(1))
                .endAt(LocalDateTime.now().plusDays(1))
                .userId(1L)
                .build();
        Event savedEvent = eventRepository.save(event);
        testEventId = savedEvent.getEventId();

        redisTemplate.delete("event:" + testEventId);
    }

    @AfterEach
    void tearDown() {
        customerCouponRepository.deleteAll();
        if (testEventId != null) {
            eventRepository.deleteById(testEventId);
            redisTemplate.delete("event:" + testEventId);
        }
    }

    @Test
    @DisplayName("100명이 동시에 30명 선착순 이벤트를 신청할 때 정확히 30명만 발급되어야 한다")
    void joinEvent_concurrency_test() throws InterruptedException {
        // Given
        int totalRequests = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch doneLatch = new CountDownLatch(totalRequests);

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        JoinEventRequest request = new JoinEventRequest(1L, testEventId);

        // When
        for (int i = 1; i <= totalRequests; i++) {
            long customerId = i;
            Customer customer = Customer.builder()
                    .customerId(customerId)
                    .build();

            executorService.submit(() -> {
                try {
                    eventService.joinEvent(customer, request);
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    System.out.println("❌ 실패 원인: " + e.getMessage()); // <-- 이 줄 추가!
                    failCount.incrementAndGet();
                } finally {
                    doneLatch.countDown();
                }
            });
        }

        doneLatch.await();
        executorService.shutdown();

        // Then
        Long actualIssuedCount = customerCouponRepository.countByEventEventId(testEventId);

        System.out.println("=========================================");
        System.out.println("총 시도 인원: " + totalRequests);
        System.out.println("성공 응답 수: " + successCount.get());
        System.out.println("실패/인원초과 응답 수: " + failCount.get());
        System.out.println("실제 DB에 발급된 쿠폰 수: " + actualIssuedCount);
        System.out.println("선착순 제한 인원(정원): " + MAX_COUNT);
        System.out.println("=========================================");

        assertThat(actualIssuedCount).isEqualTo(MAX_COUNT);
    }
}