package com.example.demo.auth;

import com.example.demo.security.ApplicationUserRole;
import com.google.common.collect.Lists;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDao{
    private PasswordEncoder passwordEncoder;

    public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers().stream().filter(
                user->user.getUsername().equals(username)
        ).findFirst();
    }
    private List<ApplicationUser> getApplicationUsers(){
        List<ApplicationUser> applicationUsers= Lists.newArrayList(
                new ApplicationUser(
                        ApplicationUserRole.STUDENT.getAuthorities(),
                        passwordEncoder.encode("password"),
                        "student",
                        true,
                        true,
                        true,
                        true

                ),
                new ApplicationUser(
                        ApplicationUserRole.ADMIN.getAuthorities(),
                        passwordEncoder.encode("password"),
                        "admin",
                        true,
                        true,
                        true,
                        true

                ),
                new ApplicationUser(
                        ApplicationUserRole.ADMINTRAINEE.getAuthorities(),
                        passwordEncoder.encode("password"),
                        "adminTrainee",
                        true,
                        true,
                        true,
                        true

                )
        );
        return applicationUsers;
    }
}
