package com.example.demo.entity;

import com.example.demo.entity.enums.Invitation_Status;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@IdClass(InvitationResponseId.class)
public class InvitationResponse {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "invitation_id", referencedColumnName = "id")
    private Invitation invitation;

    @Enumerated(EnumType.STRING)
    private Invitation_Status invitationStatus;
}
