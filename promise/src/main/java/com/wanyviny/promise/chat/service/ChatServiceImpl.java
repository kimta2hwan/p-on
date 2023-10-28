package com.wanyviny.promise.chat.service;

import com.wanyviny.promise.chat.dto.ChatRequest;
import com.wanyviny.promise.chat.dto.ChatResponse;
import com.wanyviny.promise.chat.dto.ChatResponse.CreateDto;
import com.wanyviny.promise.chat.entity.Chat;
import com.wanyviny.promise.room.entity.Room;
import com.wanyviny.promise.room.repository.RoomRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final RoomRepository roomRepository;

    @Override
    public CreateDto createChat(String roomId, ChatRequest.CreateDto createDto) {

        Chat chat = Chat.builder()
                .sender(createDto.sender())
                .chatType(createDto.chatType())
                .content(createDto.content())
                .createAt(LocalDateTime.now())
                .build();

        Room room = roomRepository.findById(roomId).orElseThrow();
        room.addMessage(chat);
        roomRepository.save(room);

        return ChatResponse.CreateDto
                .builder()
                .sender(chat.getSender())
                .chatType(chat.getChatType())
                .content(chat.getContent())
                .createAt(chat.getCreateAt())
                .build();
    }
}
