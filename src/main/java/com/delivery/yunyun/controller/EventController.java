package com.delivery.yunyun.controller;

import com.delivery.yunyun.domain.Customer;
import com.delivery.yunyun.domain.Owner;
import com.delivery.yunyun.dto.request.event.EventRequest;
import com.delivery.yunyun.dto.request.event.JoinEventRequest;
import com.delivery.yunyun.dto.response.event.EventResponse;
import com.delivery.yunyun.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping("/add")
    public ResponseEntity<?> addEvent(@AuthenticationPrincipal Owner owner, @RequestBody EventRequest request){
        eventService.addEvent(owner, request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/{eventId}")
    public ResponseEntity<?> updateEvent(@AuthenticationPrincipal Owner owner, @PathVariable Long eventId, EventRequest request){
        eventService.updateEvent(owner,eventId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long eventId){
        eventService.deleteEvent(eventId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/owner")
    public ResponseEntity<List<EventResponse>> getOwnerEvent(@AuthenticationPrincipal Owner owner){
        List<EventResponse> eventResponses= eventService.getEvent(owner);
        return ResponseEntity.ok(eventResponses);
    }

    @GetMapping("/customer")
    public ResponseEntity<List<EventResponse>> getCustomerEvent(){
        List<EventResponse> eventResponses= eventService.getCustomerEvent();
        return ResponseEntity.ok(eventResponses);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponse> getEventDatail(@PathVariable Long eventId){
        EventResponse eventResponse = eventService.getEventDetail(eventId);
        return ResponseEntity.ok(eventResponse);
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinEvent(@AuthenticationPrincipal Customer customer, @RequestBody JoinEventRequest request){
        eventService.joinEvent(customer,request);
        return ResponseEntity.ok().build();
    }



}
