package com.astorprotect.cloudbasedvideosurveillance.DAO;

import com.astorprotect.cloudbasedvideosurveillance.Model.AstorUser;
import com.astorprotect.cloudbasedvideosurveillance.Model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<UserRole,Long> {
    UserRole findByAccountType(String accountType);

    List<AstorUser> findAllByAccountType(String role);
}
