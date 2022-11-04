package com.example.demo.entity;

import com.example.demo.entity.enums.User_Status;
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

    @ManyToMany(mappedBy = "userSet", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    @Schema(hidden = true)
    private Set<Organization> organizationSet = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "user_invitation",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "invitation_id", referencedColumnName = "id")
    )
    @ToString.Exclude
    @Schema(hidden = true)
    private Invitation invitation;

    public void addOrganization(Organization organization) {
        this.organizationSet.add(organization);
        organization.getUserSet().add(this);
    }

    public void removeOrganization(Organization organization) {
        this.organizationSet.remove(organization);
        organization.getUserSet().remove(this);
    }

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
