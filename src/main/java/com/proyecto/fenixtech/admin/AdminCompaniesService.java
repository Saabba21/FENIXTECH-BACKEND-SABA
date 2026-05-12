package com.proyecto.fenixtech.admin;

import com.proyecto.fenixtech.dto.CompanyWithBadgesDTO;
import com.proyecto.fenixtech.model.Companies;
import com.proyecto.fenixtech.repository.CompaniesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminCompaniesService {

    @Autowired
    private CompaniesRepository companiesRepository;

    @Transactional(readOnly = true)
    public List<CompanyWithBadgesDTO> findAllCompaniesWithBadges() {
        return companiesRepository.findAll().stream().map(company -> {
            CompanyWithBadgesDTO dto = new CompanyWithBadgesDTO();
            dto.setCompanyId(company.getCompanyId());
            dto.setCompanyName(company.getCompanyName());
            dto.setCif(company.getCif());
            dto.setIsActive(company.getIsActive());
            
            dto.setBadges(company.getCompanyBadges().stream().map(cb -> {
                CompanyWithBadgesDTO.BadgeInfoDTO badgeDto = new CompanyWithBadgesDTO.BadgeInfoDTO();
                badgeDto.setBadgeId(cb.getBadge().getBadgeId());
                badgeDto.setBadgeName(cb.getBadge().getBadgeName());
                badgeDto.setIconUrl(cb.getBadge().getIconUrl());
                badgeDto.setAwardedAt(cb.getAwardedAt());
                return badgeDto;
            }).collect(Collectors.toList()));
            
            return dto;
        }).collect(Collectors.toList());
    }
}