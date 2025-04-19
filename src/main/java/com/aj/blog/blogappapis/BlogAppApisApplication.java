package com.aj.blog.blogappapis;

import java.security.Key;
import java.util.Base64;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.aj.AJLogger;
import com.aj.blog.blogappapis.entities.Role;
import com.aj.blog.blogappapis.repositories.RoleRepository;
import com.aj.blog.blogappapis.utils.AppConstants;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner{
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
		//AJLogger.log("BlogApp", "HELLO");
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		/*
		 * System.out.println("Bcrypted password for rahul: "+passwordEncoder.encode(
		 * "rahul@123"));
		 * System.out.println("Bcrypted password for ram: "+passwordEncoder.encode(
		 * "ram@123"));
		 * 
		 * @SuppressWarnings("deprecation") Key key =
		 * Keys.secretKeyFor(SignatureAlgorithm.HS512); String base64Key =
		 * Base64.getEncoder().encodeToString(key.getEncoded());
		 * System.out.println("Base64 Key: " + base64Key);
		 */
		
		try {
			Role role=new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ADMIN_USER");
			
			Role role1=new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("NORMAL_USER");
			
			List<Role> roles = List.of(role,role1);
			List<Role> result = roleRepo.saveAll(roles);
			result.forEach(r->{
				AJLogger.log("BlogApp", "New Roles:"+r);
			});
		} catch (Exception e) {
			AJLogger.log("BlogApp", "Exception: "+e);
		}
	}
}
