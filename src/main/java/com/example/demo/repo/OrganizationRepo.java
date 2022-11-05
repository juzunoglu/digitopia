package com.example.demo.repo;

import com.example.demo.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface OrganizationRepo extends JpaRepository<Organization, String> {
    Optional<Organization> findByRegistryNumber(String registryNumber);
    List<Organization> findByNormalizedNameContainsIgnoreCaseOrYearFoundedOrCompanySize(String normalizedName, Date yearFounded, Long companySize);

    @Query("select (count(o) > 0) from Organization o where o.registryNumber = ?1")
    boolean existsByRegistryNumber(String registryNumber);
}
