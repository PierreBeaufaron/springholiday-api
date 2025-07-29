package com.hb.cda.springholiday.business;

import com.hb.cda.springholiday.entity.User;

public interface AccountBusiness {
    User register(User user);
    void activateUser(String token);
    void resetPassword(String email);
    void deleteAccount(User user);
}
