package com.ra.model.service.user;


import com.ra.model.dao.user.UserDAO;
import com.ra.model.entity.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDAO userDAO;
    @Override
    public boolean register(User user) {
        // mã hóa mật khẩu
        user.setPassword(BCrypt.hashpw(user.getPassword(),BCrypt.gensalt(12)));
        return userDAO.create(user);
    }

    @Override
    public User login(User user) {
        // Tìm về bản ghi theo email
        User userLogin = userDAO.findUserByEmail(user.getEmail());
        if(userLogin != null){
            // check passowrd
            if(BCrypt.checkpw(user.getPassword(), userLogin.getPassword())){
                return userLogin;
            }
        }
        return null;
    }
}
