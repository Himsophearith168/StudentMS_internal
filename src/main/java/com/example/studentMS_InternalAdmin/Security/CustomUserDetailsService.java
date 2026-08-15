package com.example.studentMS_InternalAdmin.Security;

import com.example.studentMS_InternalAdmin.Model.AdminModel;
import com.example.studentMS_InternalAdmin.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Autowired
    public CustomUserDetailsService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminModel admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Staff user not found with username: " + username));

        String role = admin.getRole() != null ? admin.getRole() : "TEACHER";
        return new User(
                admin.getUsername(),
                admin.getPasswordHash(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
        );
    }
}
