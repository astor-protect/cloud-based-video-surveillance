package com.astorprotect.cloudbasedvideosurveillance.Service.imp;

import com.astorprotect.cloudbasedvideosurveillance.DAO.AstorUserRepository;
import com.astorprotect.cloudbasedvideosurveillance.DAO.RoleRepository;
import com.astorprotect.cloudbasedvideosurveillance.Model.AstorUser;
import com.astorprotect.cloudbasedvideosurveillance.Model.UserRole;
import com.astorprotect.cloudbasedvideosurveillance.Service.ServiceAstorUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ServiceAstorUserImpl implements ServiceAstorUser {
    @Autowired
    AstorUserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    RoleRepository roleRepository;


    @Override
    public AstorUser findById(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public AstorUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public AstorUser findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    @Override
    public AstorUser findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<AstorUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public AstorUser save(AstorUser user) {
        return userRepository.save(user);
    }

    @Override
    public AstorUser saveUser(AstorUser user) {
        String hashpwd = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(hashpwd);
        return userRepository.save(user);

    }

    @Override
    public UserRole saveRole(UserRole role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String accountType) {
        UserRole role = roleRepository.findByAccountType(accountType);
        AstorUser user = userRepository.findByUsername(username);
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public void deleteRoleToUser(String username, String accountType) {
        UserRole role = roleRepository.findByAccountType(accountType);
        AstorUser user  = userRepository.findByUsername(username);
        user.getRoles().remove(role);
        userRepository.save(user);
    }

    @Override
    public void deleteAllRolesToUser(AstorUser user) {
       // AstorUser  user = userRepository.findByUsername(username);
        user.getRoles().clear();
        userRepository.save(user);
    }

    @Override
    public AstorUser findUserByUsernameOrEmailOrPhone(String name) {
        return null;
    }

    @Override
    public boolean delectAdmin(Long id_admin) {
        userRepository.deleteById(id_admin);
        return true;
    }

    @Override
    public List<AstorUser> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<AstorUser> findAllByAccountType(String role) {
        return roleRepository.findAllByAccountType(role);
    }

    @Override
   public List<AstorUser> findAllByRole(String role) {
        List<AstorUser> userList = userRepository.findAll();
        List<AstorUser> usersByRole = new ArrayList<>();
        for (AstorUser user : userList){
            if (user.getRoles().contains(roleRepository.findByAccountType(role))){
                usersByRole.add(user);
            }
        }
     return usersByRole;
 }


    @Override
    public boolean activateUser(AstorUser user) {
        if (user.isActive() == true){
            user.setActive(false);
            userRepository.save(user);
            return user.isActive();
        }else {
            user.setActive(true);
            userRepository.save(user);
            return user.isActive();
        }
    }

    @Override
    public List<UserRole> findallRoles() {
        return roleRepository.findAll();
    }

}
