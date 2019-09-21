package com.astorprotect.cloudbasedvideosurveillance.Service;

import com.astorprotect.cloudbasedvideosurveillance.Model.AstorUser;
import com.astorprotect.cloudbasedvideosurveillance.Model.UserRole;

import java.util.List;

public interface ServiceAstorUser {
    AstorUser findById(Long id);
    AstorUser findByUsername(String username);
    AstorUser findByPhone(String phone);
    AstorUser findByEmail(String email);
    List<AstorUser> findAllUsers();

    List<AstorUser> getAllUsers();
    AstorUser save(AstorUser user);

    /* inscription - connexion */
    AstorUser saveUser(AstorUser user);
    UserRole saveRole(UserRole role);
    void addRoleToUser(String username, String accountType);
    AstorUser findUserByUsernameOrEmailOrPhone(String name);
    boolean delectAdmin(Long id_admin);
}
