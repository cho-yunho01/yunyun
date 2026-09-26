package com.delivery.yunyun.service;

import com.delivery.yunyun.domain.Event;
import com.delivery.yunyun.domain.Owner;
import com.delivery.yunyun.dto.request.event.EventRequest;
import com.delivery.yunyun.dto.response.event.EventResponse;
import com.delivery.yunyun.error.CustomException;
import com.delivery.yunyun.error.ErrorCode;
import com.delivery.yunyun.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    
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
}
