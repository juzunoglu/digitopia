package com.example.demo.entity;

import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InvitationResponseId implements Serializable {

    private String user;

    private String invitation;
}
