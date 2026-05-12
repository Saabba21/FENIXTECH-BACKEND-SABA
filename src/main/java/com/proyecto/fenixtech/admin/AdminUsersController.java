package com.proyecto.fenixtech.admin;

import com.proyecto.fenixtech.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUsersController {

    @Autowired
    private AdminUsersService adminUsersService;

    @GetMapping("/search")
    public ResponseEntity<List<Users>> searchUsers(@RequestParam(required = false) String query) {
        return ResponseEntity.ok(adminUsersService.searchUsers(query));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Users> toggleUserStatus(@PathVariable Integer id, @RequestBody Boolean active) {
        return ResponseEntity.ok(adminUsersService.updateUserStatus(id, active));
    }
}