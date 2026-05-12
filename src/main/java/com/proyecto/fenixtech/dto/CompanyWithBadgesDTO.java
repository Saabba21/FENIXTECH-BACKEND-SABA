package com.proyecto.fenixtech.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyWithBadgesDTO {
    private Integer companyId;
    private String companyName;
    private String cif;
    private Boolean isActive;
    private List<BadgeInfoDTO> badges;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BadgeInfoDTO {
        private Integer badgeId;
        private String badgeName;
        private String iconUrl;
        private LocalDateTime awardedAt;
    }
}