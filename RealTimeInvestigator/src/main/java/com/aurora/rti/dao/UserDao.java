package com.aurora.rti.dao;

import com.aurora.rti.model.User;

public interface UserDao {
    User findById(int id);
    User findBySSO(String sso);
}
