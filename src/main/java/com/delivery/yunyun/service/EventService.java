package com.delivery.yunyun.service;

import com.delivery.yunyun.domain.Customer;
import com.delivery.yunyun.domain.CustomerCoupon;
import com.delivery.yunyun.domain.Event;
import com.delivery.yunyun.domain.Owner;
import com.delivery.yunyun.dto.request.event.EventRequest;
import com.delivery.yunyun.dto.request.event.JoinEventRequest;
import com.delivery.yunyun.dto.response.event.EventResponse;
import com.delivery.yunyun.error.CustomException;
import com.delivery.yunyun.error.ErrorCode;
import com.delivery.yunyun.repository.CustomerCouponRepository;
import com.delivery.yunyun.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final CustomerCouponRepository customerCouponRepository;
    private final RedisTemplate<String, Long> redisTemplate;
    
    public void addEvent(Owner owner, EventRequest request) {
        Event event = Event.builder()
                .name(request.name())
                .description(request.description())
                .couponId(request.couponId())
                .maxCount(request.maxCount())
                .startAt(request.startAt())
                .endAt(request.endAt())
                .userId(owner.getOwnerId())
                .build();
        
        eventRepository.save(event);
    }

    public void updateEvent(Owner owner, Long eventId, EventRequest request) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new CustomException(ErrorCode.EVENT_NOT_FOUND));

        if (request.name() != null) {
            event.setName(request.name());
        }

        if (request.description() != null) {
            event.setDescription(request.description());
        }

        if (request.couponId() != null) {
            event.setCouponId(request.couponId());
        }

        if (request.maxCount() != null) {
            event.setMaxCount(request.maxCount());
        }

        if (request.startAt() != null) {
            event.setStartAt(request.startAt());
        }

        if (request.endAt() != null) {
            event.setEndAt(request.endAt());
        }

        eventRepository.save(event);
    }

    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
    }

    public List<EventResponse> getEvent(Owner owner) {
        List<Event> events = eventRepository.findByUserId(owner.getOwnerId())
                .orElseThrow(() -> new CustomException(ErrorCode.EVENT_NOT_FOUND));

        return events.stream().map(
                event -> {
                    EventResponse eventResponse = EventResponse.builder()
                            .eventId(event.getEventId())
                            .name(event.getName())
                            .description(event.getDescription())
                            .couponId(event.getCouponId())
                            .maxCount(event.getMaxCount())
                            .startAt(event.getStartAt())
                            .endAt(event.getEndAt())
                            .build();
                    return eventResponse;
                }
        ).toList();
    }

    public List<EventResponse> getCustomerEvent() {
        List<Event> events = eventRepository.findAll();
        return events.stream().map(
                event -> {
                    EventResponse eventResponse = EventResponse.builder()
                            .eventId(event.getEventId())
                            .name(event.getName())
                            .description(event.getDescription())
                            .couponId(event.getCouponId())
                            .maxCount(event.getMaxCount())
                            .startAt(event.getStartAt())
                            .endAt(event.getEndAt())
                            .build();
                    return eventResponse;
                }
        ).toList();
    }

    public EventResponse getEventDetail(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new CustomException(ErrorCode.EVENT_NOT_FOUND));

        EventResponse eventResponse = EventResponse.builder()
                .eventId(event.getEventId())
                .name(event.getName())
                .description(event.getDescription())
                .couponId(event.getCouponId())
                .maxCount(event.getMaxCount())
                .startAt(event.getStartAt())
                .endAt(event.getEndAt())
                .build();
        return eventResponse;
    }

    public void joinEvent(Customer customer, JoinEventRequest request) {
        String keyName = "event:"+String.valueOf(request.eventId());
        Long value = redisTemplate.opsForValue().get(keyName);
        if(value == null){
            redisTemplate.opsForValue().set(keyName, 1L);
            saveEventParticipation(customer.getCustomerId(), request);
        }else{
            if(value < 10L){
                if(!validateUser(customer.getCustomerId())){
                    redisTemplate.opsForValue().increment(keyName);
                    saveEventParticipation(customer.getCustomerId(), request);
                }
                else{
                    throw new CustomException(ErrorCode.EVENT_ALREADY_JOINED);
                }
            }
            else{
                System.out.println("인원초과");
            }
        }

    }

    public void saveEventParticipation(Long customerId, JoinEventRequest request){
        Event event = eventRepository.findById(request.eventId())
                .orElseThrow(() -> new CustomException(ErrorCode.EVENT_NOT_FOUND));

        CustomerCoupon customerCoupon = CustomerCoupon.builder()
                .customerId(customerId)
                .couponId(request.couponId())
                .event(event)
                .issuedAt(LocalDateTime.now())
                .build();

        customerCouponRepository.save(customerCoupon);
    }

    public boolean validateUser(Long customerId){
        return customerCouponRepository.existsByCustomerId(customerId);
    }
}
