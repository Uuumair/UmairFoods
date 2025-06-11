package com.umair.service;
import com.umair.model.*;

public interface UserService {

public User findUserByJwtToken(String jwt) throws Exception;
public User findUserByEmail(String jwt) throws Exception;

}
