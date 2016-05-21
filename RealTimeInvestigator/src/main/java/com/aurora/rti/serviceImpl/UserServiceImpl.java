package com.aurora.rti.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aurora.rti.dao.UserDao;
import com.aurora.rti.model.User;
import com.aurora.rti.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao dao;

    @Transactional
    public User findById(int id) {
        return dao.findById(id);
    }
 
    @Transactional
    public User findBySso(String sso) {
        return dao.findBySSO(sso);
    }

}
