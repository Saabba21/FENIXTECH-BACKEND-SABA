package com.proyecto.fenixtech.admin;

import com.proyecto.fenixtech.dto.CompanyWithBadgesDTO; // Importación necesaria
import com.proyecto.fenixtech.dto.CompanyBadgesRequestDTO;
import com.proyecto.fenixtech.model.CompanyBadges;
import com.proyecto.fenixtech.service.CompanyBadgesService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/companies")
@PreAuthorize("hasRole('ADMIN')")
public class AdminCompaniesController {

    @Autowired
    private AdminCompaniesService adminCompaniesService;
    @Autowired
    private CompanyBadgesService companyBadgesService; // Assuming you have a service for CompanyBadges

    @GetMapping("/badges")
    public ResponseEntity<List<CompanyWithBadgesDTO>> getAllCompaniesWithBadges() {
        return ResponseEntity.ok(adminCompaniesService.findAllCompaniesWithBadges());
    }

    @PostMapping("/company-badges")
    public ResponseEntity<CompanyBadges> assignBadgeToCompany(@RequestBody CompanyBadgesRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(companyBadgesService.assignBadgeToCompany(dto.getCompanyId(), dto.getBadgeId()));
    }

    @DeleteMapping("/company-badges/company/{companyId}/badge/{badgeId}")
    public ResponseEntity<Void> revokeBadgeFromCompany(@PathVariable Integer companyId, @PathVariable Integer badgeId) {
        companyBadgesService.revokeBadgeFromCompany(companyId, badgeId);
        return ResponseEntity.noContent().build();
    }
}