package com.example.demo.entity;


import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
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
@Table(name = "organization", indexes = {
        @Index(name = "idx_normalized_name_year_size", columnList = "normalized_name, year_founded, company_size"),
        @Index(name = "idx_registry_number", columnList = "registry_number")
}, uniqueConstraints = {
        @UniqueConstraint(columnNames = "contact_email", name = "constraint_unique_email"),
        @UniqueConstraint(columnNames = "registry_number", name = "constraint_unique_registry_number")
})
public class Organization extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "normalized_name")
    private String normalizedName;

    @Column(name = "registry_number")
    private String registryNumber;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "phone")
    private String phone;

    @Column(name = "year_founded")
    private Date yearFounded;

    @Column(name = "company_size")
    private Long companySize;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST
    })
    @JoinTable(
            name = "user_organization",
            joinColumns = @JoinColumn(name = "organization_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Builder.Default
    @ToString.Exclude
    private Set<User> userSet = new HashSet<>();

    public void addUser(User user) {
        this.userSet.add(user);
        user.getOrganizationSet().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Organization that = (Organization) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
