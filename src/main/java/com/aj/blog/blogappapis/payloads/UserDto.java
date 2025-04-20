package com.aj.blog.blogappapis.payloads;

import java.util.HashSet;
import java.util.Set;

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

    @NotEmpty
    @Size(min = 4, message = "Username must be minimum 4 characters !!")
    private String name;

    @Email(message = "E-mail address is not valid !!")
    private String email;

    @NotEmpty
    @Size(min = 6, max = 10, message = "Password must be minimum 3 characters and maximum 10 characters !!")
    private String password;

    @NotEmpty
    private String about;
    
    private Set<RoleDto> roles=new HashSet<RoleDto>();
}
