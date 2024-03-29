package com.astorprotect.cloudbasedvideosurveillance;

import com.astorprotect.cloudbasedvideosurveillance.Model.AstorUser;
import com.astorprotect.cloudbasedvideosurveillance.Model.UserRole;
import com.astorprotect.cloudbasedvideosurveillance.Service.ServiceAstorUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CloudBasedVideoSurveillanceApplication implements CommandLineRunner {
	@Bean
	public BCryptPasswordEncoder getBcp(){return new BCryptPasswordEncoder();}
	@Autowired
	private ServiceAstorUser serviceAstorUser;
	
	@RequestMapping("/")
    public String home() {
        return "Hello Astor Protect !";
    }

	public static void main(String[] args) {
		SpringApplication.run(CloudBasedVideoSurveillanceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		AstorUser user1 = serviceAstorUser.saveUser(new AstorUser("igor","igor","igor","igor","igor"));
		AstorUser user2 = serviceAstorUser.saveUser(new AstorUser("igor2","igor","igor@gmail.com","igor","igor"));
		AstorUser user3 = serviceAstorUser.saveUser(new AstorUser("igor3","igor3","igor@gmail.com","igor","igor"));

		/* creation des roles */

        serviceAstorUser.saveRole(new UserRole("SUPER ADMIN"));
        serviceAstorUser.saveRole(new UserRole("ADMIN"));
        serviceAstorUser.saveRole(new UserRole("CLIENT BC"));

		serviceAstorUser.saveRole(new UserRole("CLIENT BB"));
		serviceAstorUser.saveRole(new UserRole("REVENDEUR"));
		serviceAstorUser.saveRole(new UserRole("STATION DE SURVEILLANCE"));

		serviceAstorUser.saveRole(new UserRole("SERVICE TECHNIQUE"));
		serviceAstorUser.saveRole(new UserRole("INTERVENTION"));
		serviceAstorUser.saveRole(new UserRole("INSTALLATEUR"));
		serviceAstorUser.saveRole(new UserRole("USER"));


		System.out.println("text igor  le boss");
        serviceAstorUser.addRoleToUser("igor","USER");
        serviceAstorUser.addRoleToUser("igor2","SUPER ADMIN");
        serviceAstorUser.addRoleToUser("igor3","ADMIN");




    }
}
