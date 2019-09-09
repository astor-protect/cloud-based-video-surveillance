package com.astorprotect.cloudbasedvideosurveillance.DAO;

import com.astorprotect.cloudbasedvideosurveillance.Model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<UserRole,Long> {
    UserRole findByAccountType(String accountType);
}
