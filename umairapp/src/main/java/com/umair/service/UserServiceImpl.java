package com.umair.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.umair.config.JwtProvider;
import com.umair.model.USER_ROLE;
import com.umair.model.User;
import com.umair.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
   private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;
 @Override
public User findUserByJwtToken(String jwt) throws Exception {
    String email = jwtProvider.getEmailFromJwtToken(jwt);
    User user = findUserByEmail(email);

    // Check if the user is an admin
    if (!user.getRole().equals(USER_ROLE.ROLE_RESTAURANT_OWNER)) {
        throw new Exception("User is not authorized for this operation");
    }

    return user;
}

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user=userRepository.findByEmail(email);
        if (user==null) {
            throw new Exception("user not found");
        }
        return user;
    }
    

}
