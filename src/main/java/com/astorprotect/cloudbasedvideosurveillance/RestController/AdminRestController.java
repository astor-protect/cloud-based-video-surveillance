package com.astorprotect.cloudbasedvideosurveillance.RestController;

import com.astorprotect.cloudbasedvideosurveillance.Model.APIForms.ChangeAccountTypeForm;
import com.astorprotect.cloudbasedvideosurveillance.Model.AstorUser;
import com.astorprotect.cloudbasedvideosurveillance.Service.ServiceAstorUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/admin")
public class AdminRestController {

    @Autowired
    private ServiceAstorUser serviceAstorUser;

    //* A2 lister tous les comptes enregistrés sur la plateforme */
    @GetMapping("/getAllUsers")
    public List<AstorUser> findAllUsers(){
        List<AstorUser> userList=new ArrayList<>();
        List<AstorUser> userList1=  serviceAstorUser.getAllUsers();
        int i=0;
        while (i< userList1.size()){
            if ( !userList1.get(i).getRoles().get(0).getAccountType().equals("ADMIN")
                    && !userList1.get(i).getRoles().get(0).getAccountType().equals("SUPER ADMIN") ) {
                System.out.println("le role est "+userList1.get(i).getRoles().get(0).getAccountType());
                userList.add(userList1.get(i));
            }
                i++;
            }
        return userList;
    }

    @GetMapping("findByRole/{accountType}")
    public List<AstorUser> findUsersByRole(@PathVariable String accountType){
        if (accountType.equalsIgnoreCase("SUPER ADMIN")){
        return serviceAstorUser.findAllByRole(accountType);
        }else return null;
    }


    /* A2 changer le type de compte d'un Utilisateur */
    @PostMapping(value = "/changeAccountType")
    public AstorUser changeAccountType(@RequestBody ChangeAccountTypeForm changeAccountTypeForm){
        AstorUser user = serviceAstorUser.findByUsername(changeAccountTypeForm.getUsername());

        System.out.println("l'utilisateur est : "+user.getUsername());
       if (user.getRoles().get(0).getAccountType().equals("SUPER ADMIN")){
           serviceAstorUser.deleteAllRolesToUser(user);
        serviceAstorUser.addRoleToUser(user.getUsername(),changeAccountTypeForm.getAccountType());
       }
        return user;

    }
    @DeleteMapping("/delete_user/{id_user}")
    boolean delectUser(@PathVariable Long id_user){
        return serviceAstorUser.delectUser(id_user);
    }


    @PutMapping("/activeUser/{id_user}")
    boolean activateUser(@PathVariable Long id_user){
        AstorUser user = serviceAstorUser.findById(id_user);
        return serviceAstorUser.activateUser(user);
    }
}
