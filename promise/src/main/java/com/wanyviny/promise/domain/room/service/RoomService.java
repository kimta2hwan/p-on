package com.wanyviny.promise.domain.room.service;

import com.wanyviny.promise.domain.room.dto.RoomRequest;
import com.wanyviny.promise.domain.room.dto.RoomResponse;
import java.util.List;

public interface RoomService {

    RoomResponse.Create createRoom(Long userId, RoomRequest.Create request);
    RoomResponse.Find findRoom(Long roomId);
    List<RoomResponse.FindAll> findAllRoom(Long userId);
    RoomResponse.Join joinRoom(Long userId, Long roomId);
    List<RoomResponse.Exit> exitRoom(Long userId, Long roomId);
    void deleteRoom(Long roomId);
}