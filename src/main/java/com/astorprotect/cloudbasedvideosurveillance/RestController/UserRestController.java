package com.astorprotect.cloudbasedvideosurveillance.RestController;

import com.astorprotect.cloudbasedvideosurveillance.Model.APIForms.ChangeAccountTypeForm;
import com.astorprotect.cloudbasedvideosurveillance.Model.AstorUser;
import com.astorprotect.cloudbasedvideosurveillance.Model.RegisterForm;
import com.astorprotect.cloudbasedvideosurveillance.Service.EmailService;
import com.astorprotect.cloudbasedvideosurveillance.Service.ServiceAstorUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static java.lang.Boolean.TRUE;

@CrossOrigin("*")
@RestController

public class UserRestController {
    @Autowired
    private ServiceAstorUser serviceAstorUser;
    @Autowired
    private EmailService emailService;
    public static final String REGISTRATION_SUBJECT =  "ASTOR : REGISTRATION SUCCESSFULLLY";

    @PostMapping("/inscription")
    public AstorUser insciption(@RequestBody RegisterForm registerForm) {
        if (!registerForm.getPassword().equals(registerForm.getRepassword()))
            throw new RuntimeException("you must confirm your password");

        AstorUser astorUser = serviceAstorUser.findByUsername(registerForm.getUsername());

        if (astorUser != null) throw new RuntimeException("This USER already exists");
       // if (registerForm.getRole()==null) throw new RuntimeException("This USER Not have Role");

        AstorUser user = new AstorUser();
        user.setPassword(registerForm.getPassword());
        user.setUsername(registerForm.getUsername());

        user.setEmail(registerForm.getEmail());
        user.setFirstName(registerForm.getFirstName());
        user.setLastName(registerForm.getLastName());
        user.setPhone(registerForm.getPhone());
        user.setAddress(registerForm.getAddress());


        serviceAstorUser.saveUser(user);
        serviceAstorUser.addRoleToUser(user.getUsername(), registerForm.getRole());
        emailService.sendSimpleMessage(user.getEmail(), REGISTRATION_SUBJECT,"Thank for coming in");
        return user;
    }



    /* recuperer l'user connected  requete  à associer a /login si on ne veut pas exploiter le jwt    */
    @GetMapping("/getUser")
    public AstorUser getUserConnected(Principal principal){

        return serviceAstorUser.findByUsername(principal.getName());
    }



@GetMapping("getAllUsers")
    public List<AstorUser> findAllUsers(){
        return serviceAstorUser.getAllUsers();
}

/* Test envoi  simple d'email */
    @RequestMapping(value = "envoi",method = RequestMethod.GET)
    public Boolean createMail(){
        emailService.sendSimpleMessage("jokayam95@gmail.com","TEST EMAIL ASTOR", "Ceci  est un exercice.");
        return TRUE;
    }

    /* recuperer une liste d users */
    @GetMapping("getByRole/{role}")
    public List<AstorUser> findUsersByRole(@PathVariable String role){
       return serviceAstorUser.findAllByAccountType(role);
    }

}
