package com.nam4o.myweb.domain.member.repository;

import com.nam4o.myweb.domain.member.entity.Authorities;
import com.nam4o.myweb.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthoritiesRepository extends JpaRepository<Authorities, Long> {

}
