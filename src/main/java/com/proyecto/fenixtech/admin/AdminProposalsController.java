package com.proyecto.fenixtech.admin;

import com.proyecto.fenixtech.model.Proposals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/proposals")
@PreAuthorize("hasRole('ADMIN')")
public class AdminProposalsController {

    @Autowired
    private AdminContentService adminService;

    @GetMapping("/all")
    public ResponseEntity<List<Proposals>> getAll() {
        return ResponseEntity.ok(adminService.findAllProposals());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStatus(@PathVariable Integer id, @RequestBody String status) {
        adminService.updateProposalStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        adminService.deleteProposal(id);
        return ResponseEntity.noContent().build();
    }
}