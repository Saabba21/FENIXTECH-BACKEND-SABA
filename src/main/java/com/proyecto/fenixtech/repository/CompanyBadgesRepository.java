package com.proyecto.fenixtech.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.fenixtech.model.Badges;
import com.proyecto.fenixtech.model.Companies;
import com.proyecto.fenixtech.model.CompanyBadgeId;
import com.proyecto.fenixtech.model.CompanyBadges;
import java.time.LocalDateTime;


public interface CompanyBadgesRepository extends JpaRepository<CompanyBadges, CompanyBadgeId>{
    List<CompanyBadges> findByCompany_CompanyId(Integer id);
    List<CompanyBadges> findByAwardedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    Boolean existsById_CompanyIdAndId_BadgeId(Integer companyId, Integer badgeId);
    Long countByCompany_CompanyId(Integer companyId);
    Optional<CompanyBadges> findByCompanyAndBadge(Companies company, Badges badge);
}
