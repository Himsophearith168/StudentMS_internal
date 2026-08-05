package com.example.studentMS_InternalAdmin.Config;

import com.example.studentMS_InternalAdmin.Model.AdminModel;
import com.example.studentMS_InternalAdmin.Model.Role;
import com.example.studentMS_InternalAdmin.Model.UserModel;
import com.example.studentMS_InternalAdmin.Repository.AdminRepository;
import com.example.studentMS_InternalAdmin.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(UserRepository userRepository, AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        try {
            if (!userRepository.existsByUsername("admin")) {
                UserModel adminUser = UserModel.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin123"))
                        .role(Role.ADMIN)
                        .build();

                UserModel savedUser = userRepository.save(adminUser);

                AdminModel adminProfile = AdminModel.builder()
                        .user(savedUser)
                        .fullName("System Administrator")
                        .email("admin@school.com")
                        .phone("012345678")
                        .build();

                adminRepository.save(adminProfile);

                System.out.println("=================================================");
                System.out.println(">>> DEFAULT ADMIN ACCOUNT CREATED");
                System.out.println(">>> Username : admin");
                System.out.println(">>> Password : admin123");
                System.out.println(">>> Role     : ADMIN");
                System.out.println("=================================================");
            } else {
                System.out.println(">>> Admin account already exists. Skipping seed.");
            }
        } catch (Exception e) {
            System.err.println(">>> ERROR seeding admin account: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
