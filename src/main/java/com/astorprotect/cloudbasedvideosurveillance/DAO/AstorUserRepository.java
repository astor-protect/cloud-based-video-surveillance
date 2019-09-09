package com.astorprotect.cloudbasedvideosurveillance.DAO;

import com.astorprotect.cloudbasedvideosurveillance.Model.AstorUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AstorUserRepository extends JpaRepository<AstorUser,Long> {
    AstorUser findByIduser(Long id);
    AstorUser findByUsername(String username);
    AstorUser findByPhone(String phone);
    AstorUser findByEmail(String email);
}
