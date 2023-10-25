package com.wanyviny.promise.domain.message.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {

    private String sender;
    private MessageType messageType;

    @CreatedDate
    private LocalDateTime createAt;
}
