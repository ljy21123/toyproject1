package com.example.toyproject1.repository;

import com.example.toyproject1.entity.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MemberRepository extends CrudRepository<Member, Long> {
    @Override
    List<Member> findAll();
}
