package com.example.demo.repo;


import com.example.demo.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface InvitationRepo extends JpaRepository<Invitation, String> {

}
