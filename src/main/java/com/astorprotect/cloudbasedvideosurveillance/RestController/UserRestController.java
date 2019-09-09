package com.astorprotect.cloudbasedvideosurveillance.RestController;

import com.astorprotect.cloudbasedvideosurveillance.Model.AstorUser;
import com.astorprotect.cloudbasedvideosurveillance.Model.RegisterForm;
import com.astorprotect.cloudbasedvideosurveillance.Service.ServiceAstorUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class UserRestController {
    @Autowired
    private ServiceAstorUser serviceAstorUser;

    @PostMapping("/inscription")
    public AstorUser insciption(@RequestBody RegisterForm registerForm) {
        if (!registerForm.getPassword().equals(registerForm.getRepassword()))
            throw new RuntimeException("you must confirm your password");

        AstorUser astorUser = serviceAstorUser.findByUsername(registerForm.getUsername());

        if (astorUser != null) throw new RuntimeException("This USER already exists");

        AstorUser user = new AstorUser();
        user.setPassword(registerForm.getPassword());
        user.setUsername(registerForm.getUsername());

        user.setEmail(registerForm.getEmail());
        user.setFirstName(registerForm.getFirstName());
        user.setLastName(registerForm.getLastName());

        serviceAstorUser.saveUser(user);
        serviceAstorUser.addRoleToUser(registerForm.getUsername(), "USER");
        return user;
    }
@GetMapping("getAllUsers")
    public List<AstorUser> findAllUsers(){
        return serviceAstorUser.getAllUsers();
}
}
