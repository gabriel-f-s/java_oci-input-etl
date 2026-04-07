package com.gabriel_f_s.oci.input.repository;

import com.gabriel_f_s.oci.input.entity.DomainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface DomainEntitiesRepository<T extends DomainEntity> extends JpaRepository<T, Long> {
}
