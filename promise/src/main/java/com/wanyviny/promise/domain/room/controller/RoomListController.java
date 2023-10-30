package com.wanyviny.promise.domain.room.controller;

import com.wanyviny.promise.domain.common.BasicResponse;
import com.wanyviny.promise.domain.room.dto.RoomListResponse;
import com.wanyviny.promise.domain.room.service.RoomListService;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/promise/room-list")
public class RoomListController {

    private final RoomListService roomListService;

    @PostMapping("/{userId}")
    public ResponseEntity<BasicResponse> createRoomList(@PathVariable String userId) {

        roomListService.createRoomList(userId);

        BasicResponse basicResponse = BasicResponse.builder()
                .message("약속방 리스트 생성 성공")
                .build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<BasicResponse> findRoomList(@PathVariable String userId) {

        RoomListResponse.FindDto response = roomListService.findRoomList(userId);

        BasicResponse basicResponse = BasicResponse.builder()
                .message("약속방 리스트 조회 성공")
                .count(1)
                .result(Collections.singletonList(response))
                .build();
        
        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }
}