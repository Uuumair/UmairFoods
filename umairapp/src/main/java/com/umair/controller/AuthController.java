package com.umair.controller;

import org.springframework.web.bind.annotation.RestController;

import com.umair.config.JwtProvider;
import com.umair.model.Cart;
import com.umair.model.USER_ROLE;
import com.umair.model.User;
import com.umair.repository.CartRepository;
import com.umair.repository.UserRepository;
import com.umair.request.LoginRequest;
import com.umair.response.AuthResponse;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.umair.service.CustomUserDetailsService;




@RestController
@RequestMapping("/auth")



public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
   private CustomUserDetailsService customUserDetailsService;

   @Autowired
   private CartRepository cartRepository;
   


   @PostMapping("/signup")
public ResponseEntity<AuthResponse>createUserHandler(@RequestBody User user) throws Exception{

        User isEmailExists=userRepository.findByEmail(user.getEmail());
        if (isEmailExists != null) {
            throw new Exception("email is used ");
        }
        User createdUser =new User();
        createdUser.setEmail(user.getEmail());
        createdUser.setFullname(user.getFullname());
        createdUser.setRole(user.getRole());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser =userRepository.save(createdUser);

        Cart cart =new Cart();
        cart.setCustomer(savedUser);

        cartRepository.save(cart);

        UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt =jwtProvider.generateToken(authentication);
        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(jwt);

        authResponse.setMessage("successregistered");
        authResponse.setRole(savedUser.getRole());
    return new ResponseEntity<>(authResponse,HttpStatus.CREATED);
}


@PostMapping("/login")
public ResponseEntity<AuthResponse>signIn(@RequestBody LoginRequest req){
String username=req.getEmail();
String password=req.getPassword();
Authentication authentication=authenticate(username,password);

Collection<? extends GrantedAuthority>authoroties=(authentication).getAuthorities();
String role =authoroties.isEmpty()?null:authoroties.iterator().next().getAuthority();

String jwt =jwtProvider.generateToken(authentication);
        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(jwt);

        authResponse.setMessage("successlogin");

authResponse.setRole(USER_ROLE.valueOf(role));
        
    return new ResponseEntity<>(authResponse,HttpStatus.OK);
}






private Authentication authenticate(String username, String password) {
    UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
    if (userDetails == null) {
        throw new BadCredentialsException("Invalid credentials");
    }

    if (!passwordEncoder.matches(password, userDetails.getPassword())) {
        throw new BadCredentialsException("Invalid password");
    }

    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
}
}

