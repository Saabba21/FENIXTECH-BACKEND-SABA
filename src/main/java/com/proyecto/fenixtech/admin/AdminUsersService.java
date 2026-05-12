package com.proyecto.fenixtech.admin;

import com.proyecto.fenixtech.model.Users;
import com.proyecto.fenixtech.exception.ResourceNotFoundException;
import com.proyecto.fenixtech.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdminUsersService {

    @Autowired
    private UsersRepository usersRepository;

    public List<Users> searchUsers(String query) {
        if (query == null || query.isBlank()) {
            return usersRepository.findAll();
        }
        return usersRepository.findByEmailContainingOrFirstNameContaining(query, query);
    }

    public Users updateUserStatus(Integer id, Boolean active) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
        user.setIsActive(active);
        return usersRepository.save(user);
    }
}