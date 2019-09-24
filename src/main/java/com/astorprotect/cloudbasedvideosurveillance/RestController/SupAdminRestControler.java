package com.astorprotect.cloudbasedvideosurveillance.RestController;

import com.astorprotect.cloudbasedvideosurveillance.Model.APIForms.ChangeAccountTypeForm;
import com.astorprotect.cloudbasedvideosurveillance.Model.AstorUser;
import com.astorprotect.cloudbasedvideosurveillance.Model.RegisterForm;
import com.astorprotect.cloudbasedvideosurveillance.Model.UserRole;
import com.astorprotect.cloudbasedvideosurveillance.Service.EmailService;
import com.astorprotect.cloudbasedvideosurveillance.Service.ServiceAstorUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

import static java.lang.Boolean.TRUE;

@RequestMapping("/supadmin")
@RestController
@CrossOrigin("*")
public class SupAdminRestControler {
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
        if (registerForm.getRole()==null) throw new RuntimeException("This USER Not have Role");

        AstorUser user = new AstorUser();
        user.setPassword(registerForm.getPassword());
        user.setUsername(registerForm.getUsername());

        user.setEmail(registerForm.getEmail());
        user.setFirstName(registerForm.getFirstName());
        user.setLastName(registerForm.getLastName());
        user.setPhone(registerForm.getPhone());
        user.setAddress(registerForm.getAddress());

        serviceAstorUser.saveUser(user);
        serviceAstorUser.addRoleToUser(registerForm.getUsername(), registerForm.getRole());
        emailService.sendSimpleMessage(user.getEmail(), REGISTRATION_SUBJECT,"Thank for coming IN");
        return user;
    }
    /* A2 changer le type de compte d'un Utilisateur */
    @PostMapping(value = "/changeAccountType")
    public AstorUser changeAccountType(@RequestBody ChangeAccountTypeForm changeAccountTypeForm){
        AstorUser user = serviceAstorUser.findByUsername(changeAccountTypeForm.getUsername());
        serviceAstorUser.deleteAllRolesToUser(user);
        serviceAstorUser.addRoleToUser(user.getUsername(),changeAccountTypeForm.getAccountType());
        return user;

    }
    /*
    * A2 lister tous les comptes enregistrés sur la plateforme */
    @GetMapping("getAllUsers")
    public List<AstorUser> findAllUsers(){
        return serviceAstorUser.getAllUsers();
    }
    /* chercher les users selon le type de compte */
   @GetMapping("findByRole/{accountType}")
    public List<AstorUser> findUsersByRole(@PathVariable String accountType){
        return serviceAstorUser.findAllByRole(accountType);
    }

    @GetMapping("/findByAccount/{accountType}")
    public List<AstorUser> findUsersByAccountType(@PathVariable String accountType){
        return serviceAstorUser.findAllByAccountType(accountType);
    }
    /* Test envoi  simple d'email */
    @RequestMapping(value = "envoi",method = RequestMethod.GET)
    public Boolean createMail(){
        emailService.sendSimpleMessage("jokayam95@gmail.com","TEST EMAIL ASTOR", "Ceci  est un exercice.");
        return TRUE;
    }
/* A2 supprimer un Utilisateur  activer desactiver*/
    @DeleteMapping("/delect_user/{id_user}")
    boolean delectUser(@PathVariable Long id_user){
        return serviceAstorUser.delectAdmin(id_user);
    }
    @PutMapping("/activeUser/{id_user}")
    boolean activateUser(@PathVariable Long id_user){
        AstorUser user = serviceAstorUser.findById(id_user);
        return serviceAstorUser.activateUser(user);
    }
/**/
    @GetMapping("roles")
    public List<UserRole> AllRoles(){
        return serviceAstorUser.findallRoles();
    }


}
