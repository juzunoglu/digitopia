package com.example.demo.entity;

import com.example.demo.entity.enums.Invitation_Status;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "invitation")
public class Invitation extends BaseEntity {

    private String message;

    @Enumerated(EnumType.STRING)
    private Invitation_Status invitationStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @ToString.Exclude
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Invitation that = (Invitation) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
