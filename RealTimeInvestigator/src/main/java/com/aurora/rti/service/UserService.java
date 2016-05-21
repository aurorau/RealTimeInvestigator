package com.aurora.rti.service;

import com.aurora.rti.model.User;

public interface UserService {
    User findById(int id);
    User findBySso(String sso);
}
