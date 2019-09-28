package com.astorprotect.cloudbasedvideosurveillance.Service;

import com.astorprotect.cloudbasedvideosurveillance.Model.AstorUser;
import com.astorprotect.cloudbasedvideosurveillance.Model.UserRole;

import java.util.Collection;
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
    boolean delectUser(Long id_admin);

    List<AstorUser> findAllByAccountType(String role);
    List<AstorUser> findAllByRole(String role);

    /*retirer un role a un user*/
    void deleteRoleToUser(String username,String accountType);

    /* retirer tous les roles à un user */
    void deleteAllRolesToUser(AstorUser user);

    boolean activateUser(AstorUser user);

    List<UserRole> findallRoles();
}

