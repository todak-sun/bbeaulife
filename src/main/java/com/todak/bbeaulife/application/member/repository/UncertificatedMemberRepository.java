package com.todak.bbeaulife.application.member.repository;

import com.todak.bbeaulife.application.member.UncertificatedMember;
import org.springframework.data.repository.CrudRepository;

public interface UncertificatedMemberRepository extends CrudRepository<UncertificatedMember, String> {
}
