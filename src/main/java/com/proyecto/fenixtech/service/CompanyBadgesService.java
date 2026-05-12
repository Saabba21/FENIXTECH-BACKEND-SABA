package com.proyecto.fenixtech.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.fenixtech.dto.CompanyBadgesRequestDTO;
import com.proyecto.fenixtech.exception.ResourceNotFoundException;
import com.proyecto.fenixtech.model.Badges;
import com.proyecto.fenixtech.model.Companies;
import com.proyecto.fenixtech.model.CompanyBadgeId;
import com.proyecto.fenixtech.model.CompanyBadges;
import com.proyecto.fenixtech.repository.CompaniesRepository;
import com.proyecto.fenixtech.repository.CompanyBadgesRepository;
import com.proyecto.fenixtech.repository.BadgesRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


@Service
public class CompanyBadgesService {
    @Autowired
    private CompanyBadgesRepository companyBadgesRepository;

    @Autowired
    private CompaniesRepository companiesRepository;

    @Autowired
    private BadgesRepository badgesRepository;

    @Transactional(readOnly = true)
    public List<CompanyBadges> findAll() {
        return companyBadgesRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CompanyBadges findById(Integer companyId, Integer badgeId) {
        CompanyBadgeId compositeId = new CompanyBadgeId(companyId, badgeId);

        return companyBadgesRepository.findById(compositeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Insignia no encontrada para la empresa " + companyId + " y badge " + badgeId));
    }

    @Transactional(readOnly = true)
    public List<CompanyBadges> findByCompanyId(Integer id) {
        companiesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrada con id: " + id));
        return companyBadgesRepository.findByCompany_CompanyId(id);
    }

    @Transactional(readOnly = true)
    public List<CompanyBadges> findByAwardedAtBetween(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin");
        }

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(java.time.LocalTime.MAX);
        return companyBadgesRepository.findByAwardedAtBetween(startDateTime, endDateTime);
    }

    @Transactional(readOnly = true)
    public Long count() {
        return companyBadgesRepository.count();
    }

    @Transactional(readOnly = true)
    public Long countByCompanyId(Integer companyId) {
        if (!companiesRepository.existsById(companyId)) {
            throw new ResourceNotFoundException("Empresa no encontrada con ID: " + companyId);
        }

        return companyBadgesRepository.countByCompany_CompanyId(companyId);
    }

    @Transactional // Renamed from 'post' and fixed implementation for @IdClass
    public CompanyBadges assignBadgeToCompany(Integer companyId, Integer badgeId) {
        Companies company = companiesRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrada con id: " + companyId));

        Badges badge = badgesRepository.findByBadgeIdAndIsActiveTrue(badgeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "La insignia con id: " + badgeId + " no existe o no está activa"));

        // Check if the badge is already assigned
        if (companyBadgesRepository.findByCompanyAndBadge(company, badge).isPresent()) {
            throw new IllegalArgumentException("La empresa ya tiene esta insignia asignada.");
        }

        CompanyBadges newCompanyBadge = new CompanyBadges();
        newCompanyBadge.setCompany(company); // Set the entity directly for @IdClass
        newCompanyBadge.setBadge(badge);     // Set the entity directly for @IdClass
        newCompanyBadge.setAwardedAt(LocalDateTime.now()); // Set current time

        return companyBadgesRepository.save(newCompanyBadge);
    }

    @Transactional // Added this method for revoking badges
    public void revokeBadgeFromCompany(Integer companyId, Integer badgeId) {
        Companies company = companiesRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrada con ID: " + companyId));
        Badges badge = badgesRepository.findById(badgeId)
                .orElseThrow(() -> new ResourceNotFoundException("Insignia no encontrada con ID: " + badgeId));

        CompanyBadges companyBadge = companyBadgesRepository.findByCompanyAndBadge(company, badge)
                .orElseThrow(() -> new ResourceNotFoundException("La empresa no tiene esta insignia asignada."));
        companyBadgesRepository.delete(companyBadge);
    }

    @Transactional
    public void deleteByCompanyIdAndBadgeId(Integer companyId, Integer badgeId) {
        revokeBadgeFromCompany(companyId, badgeId);
    }

    @Transactional
    public CompanyBadges post(CompanyBadgesRequestDTO dto) {
        return assignBadgeToCompany(dto.getCompanyId(), dto.getBadgeId());
    }
}
