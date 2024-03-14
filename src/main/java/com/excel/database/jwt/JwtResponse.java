package com.excel.database.jwt;

import com.excel.database.entity.Role;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class JwtResponse{

//    private String jwtToken;

//    private String email;
//
    private Set<Role> roles;

}