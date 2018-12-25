package com.qst.service;

import com.qst.dao.UserDao;
import com.qst.dbutils.DBUtil;
import com.qst.dbutils.JDBCUtil;
import com.qst.user.User;
import com.qst.utils.Md5Utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    UserDao userDao = new UserDao();
    JDBCUtil db = new JDBCUtil();
//根据用户名查询用户
    public User findUserByName(String username){
        JDBCUtil db = new JDBCUtil();
        User user = null;
        try {
            db.getConnection();
            String sql = "select * from users where userame=?";
            Object[] param = new Object[]{username};
            ResultSet resultSet = db.executeQuery(sql,param);
            if (resultSet.next()){
                user = new User(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getInt(5),resultSet.getInt(6),resultSet.getString(7));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close();
        }
        return user;
    }
//保存用户信息
    public boolean saveUser(User user) {
//        定义一个布尔返回值，初始值为false
        boolean r= false;
        try{
            JDBCUtil.getConnection();
            String sql = "insert into users(username,password,email,age,sex,address) VALUES(?,?,?,?,?,?)";
            Object[] param = new Object[]{user.getUsername(),Md5Utils.md5(user.getPassword()),user.getEmail(),user.getAge(),user.getSex(),user.getAddress()};
            if (db.executeUpdate(sql,param)>0){
                r = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtil.close();
        }
        return r;
    }
}
