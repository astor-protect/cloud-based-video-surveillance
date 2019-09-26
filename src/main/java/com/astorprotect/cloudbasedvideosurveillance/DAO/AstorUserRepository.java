package com.astorprotect.cloudbasedvideosurveillance.DAO;

import com.astorprotect.cloudbasedvideosurveillance.Model.AstorUser;
import com.astorprotect.cloudbasedvideosurveillance.Model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface AstorUserRepository extends JpaRepository<AstorUser,Long> {
    AstorUser findByIduser(Long id);
    AstorUser findByUsername(String username);
    AstorUser findByPhone(String phone);
    AstorUser findByEmail(String email);

//List<AstorUser> findAstorUsersByRolesIsContaining(Collection<UserRole> role);
}
