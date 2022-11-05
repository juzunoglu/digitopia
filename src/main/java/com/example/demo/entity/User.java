package com.example.demo.entity;

import com.example.demo.entity.enums.User_Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "users", indexes = {
        @Index(name = "idx_user_id", columnList = "id"),
        @Index(name = "idx_email", columnList = "email"),
        @Index(name = "idx_normalized_name", columnList = "normalized_name")
}, uniqueConstraints = {
        @UniqueConstraint(columnNames = "email", name = "constraint_unique_email")
})
public class User extends BaseEntity {

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private User_Status status;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "normalized_name")
    private String normalizedName;

    @ManyToMany(mappedBy = "userSet", fetch = FetchType.LAZY, cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST
    })
    @Builder.Default
    @ToString.Exclude
    @Schema(hidden = true)
    @JsonIgnore
    private Set<Organization> organizationSet = new HashSet<>();

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "invitation_id", unique = true)
//    @ToString.Exclude
//    @Schema(hidden = true)
//    private Invitation invitation;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
