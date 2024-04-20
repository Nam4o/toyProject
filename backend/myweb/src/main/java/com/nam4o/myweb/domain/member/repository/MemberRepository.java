package com.nam4o.myweb.domain.member.repository;

import com.nam4o.myweb.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
