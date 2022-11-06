package com.example.demo.kafka.events;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class EMailEvent {

    private String email;
    private String invitationMessage;
}
