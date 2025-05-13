package com.aj.blog.blogappapis.payloads;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private int userId;

    @NotEmpty(message = "Please enter name")
    @Size(min = 4, message = "Username must be minimum 4 characters !!")
    private String name;

    @Email(message = "E-mail address is not valid !!")
    @NotEmpty(message = "email is required.")
    private String email;

    @NotEmpty(message = "Password is required.")
    @Size(min = 6, message = "Password must be minimum 3 characters !!")
    private String password;

    @NotEmpty
    private String about;
    
    private Set<RoleDto> roles=new HashSet<RoleDto>();
    
    
    
    @JsonProperty
    public void setPassword(String password) {
    	this.password=password;
    }
}
