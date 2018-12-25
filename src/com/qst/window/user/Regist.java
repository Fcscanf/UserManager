package com.qst.window.user;

import com.qst.dbutils.JDBCUtil;
import com.qst.service.UserService;
import com.qst.user.User;
import com.qst.utils.Logsave;
import com.qst.utils.Md5Utils;

import javax.swing.*;
import java.awt.*;

import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.border.EmptyBorder;

public class Regist extends JFrame {
    private JPanel contentPane;
    private JTextField userNameText;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JPasswordField passwordField;
    private JPasswordField passwordField_1;
    private JPasswordField passwordField_2;
    private JLabel lblNewLabel;
    private JRadioButton jbMale,jbFemale;
    private ButtonGroup buttonGroup;
    private User user;
    private UserService userService = new UserService();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Regist frame = new Regist();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     *
     */
    public Regist() throws IOException {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 539, 562);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("\u7528\u6237\u540D\uFF1A");
        lblNewLabel.setBounds(85, 55, 54, 15);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("\u5BC6\u7801\uFF1A");
        lblNewLabel_1.setBounds(85, 278, 54, 15);
        contentPane.add(lblNewLabel_1);

        userNameText = new JTextField();
        userNameText.setBounds(203, 52, 149, 21);
        contentPane.add(userNameText);
        userNameText.setColumns(10);

        JButton button = new JButton("登录");
        button.setBounds(70, 418, 93, 23);
        contentPane.add(button);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
//                在注册页面添加一个页面跳转方法，跳至登录界面
                try {
                    Login frame = new Login();
                    frame.setVisible(true);
                    dispose();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        JButton btnNewButton = new JButton("注册");
        btnNewButton.setBounds(275, 418, 93, 23);
        contentPane.add(btnNewButton);
        btnNewButton.addActionListener(new RegisterListener());
        /*button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if(isReg()>0) {
                    System.out.println("注册成功！");
                }else {
                    System.out.println("注册失败！");
                }
            }
        });*/


        lblNewLabel = new JLabel("");
        lblNewLabel.setBounds(152, 128, 153, 15);
        contentPane.add(lblNewLabel);

        JLabel label = new JLabel("性别：");
        label.setBounds(85, 99, 54, 15);
        contentPane.add(label);

        JLabel label_1 = new JLabel("年龄：");
        label_1.setBounds(85, 145, 54, 15);
        contentPane.add(label_1);

        JLabel label_2 = new JLabel("地址：");
        label_2.setBounds(85, 232, 54, 15);
        contentPane.add(label_2);

        JLabel label_3 = new JLabel("邮箱：");
        label_3.setBounds(85, 189, 54, 15);
        contentPane.add(label_3);

        textField = new JTextField();
        textField.setBounds(203, 142, 149, 21);
        contentPane.add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setBounds(202, 186, 150, 21);
        contentPane.add(textField_1);
        textField_1.setColumns(10);

        textField_2 = new JTextField();
        textField_2.setBounds(203, 229, 149, 21);
        contentPane.add(textField_2);
        textField_2.setColumns(10);

        jbMale = new JRadioButton("男",true);
        jbMale.setBounds(199, 95, 103, 23);
        contentPane.add(jbMale);

        JRadioButton jbFemale = new JRadioButton("女");
        jbFemale.setBounds(298, 95, 103, 23);
        contentPane.add(jbFemale);
        buttonGroup = new ButtonGroup();
        buttonGroup.add(jbMale);
        buttonGroup.add(jbFemale);

        JLabel label_4 = new JLabel("请再次输入密码：");
        label_4.setBounds(85, 325, 54, 15);
        contentPane.add(label_4);


        passwordField_1 = new JPasswordField();
        passwordField_1.setBounds(203, 275, 149, 21);
        contentPane.add(passwordField_1);

        passwordField_2 = new JPasswordField();
        passwordField_2.setBounds(203, 322, 149, 21);
        contentPane.add(passwordField_2);

        final Logsave logSvr = new Logsave();
        final File tmpLogFile = new File("logrecord.txt");
        if(!tmpLogFile.exists()) {
            tmpLogFile.createNewFile();
        }
        //启动一个线程每5000秒钟向日志文件写一次数据
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        exec.scheduleWithFixedDelay(new Runnable(){
            public void run() {
                try {
                    logSvr.logMsg(tmpLogFile,userNameText.getText()+"用户进行注册操作!\r");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, 5000, TimeUnit.SECONDS);

        JLabel image = new JLabel("");
        image.setBounds(10,10,54,15);
        ImageIcon ii = new ImageIcon("D:\\fcphotosynthetic\\Screenshots\\QQ截图20170711091748.png");
        image.setIcon(ii);
        ii.setImage(ii.getImage().getScaledInstance(1000,1000, Image.SCALE_DEFAULT));
        image.setBounds(0,0,500,700);
        contentPane.add(image);
    }

    /**
     *监听类，负责处理确认按钮的错误提示
     */
    private class RegisterListener implements ActionListener{
//重写actionPerformed()方法，事件处理方法
        @Override
        public void actionPerformed(ActionEvent e) {
//            获取用户输入的数据
            String username = userNameText.getText().trim();
//            将男女的性别转换为0和1
            int sex = Integer.parseInt(jbMale.isSelected()?"0":"1");
            String password = new String(passwordField_1.getPassword());
            String repassword = new String(passwordField_2.getPassword());
            int age = textField.getColumns();
            String email = textField_1.getText().trim();
            String address = textField_2.getText().trim();
//            判断两次密码是否一样
            if(password.equals(repassword)){
                user = new User(username,password,email,age,sex,address);
//                保存数据
                if (userService.saveUser(user)){
//                    输出提示信息
                    System.out.print("注册成功！");
                }else {
                    System.out.print("注册失败！");
                }
            }else {
                System.out.print("两次输入的密码不一致！");
            }
        }
    }
    /**
     * 注册用户
     *
     * @return true 成功 false 失败
     */
    public int isReg() {
        int i = 0;
        if (isCunZai()) {
            lblNewLabel.setText("用户名已存在！！！");
        } else {
            String username = userNameText.getText();
            String password = passwordField_1.getText();
            String email = textField_1.getText();
            int age = textField.getColumns();
            int sex = Integer.parseInt(jbFemale.isSelected()?"0":"1");
            String address = textField_2.getText();
            // 链接数据库
            Connection con = JDBCUtil.getConnection();
            PreparedStatement psm = null;
            ResultSet rs = null;
            int uid = 1;
            try {
                String sql1 = "INSERT INTO user(username, password ,email,age,sex,address) VALUES (?,?,?,?,?,?)";
                psm = con.prepareStatement(sql1);
                psm.setString(1, username);
                psm.setString(2, password);
                psm.setString(3,email);
                psm.setInt(4,age);
                psm.setInt(5,sex);
                psm.setString(6,address);
                i = psm.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                JDBCUtil.close();
            }
        }

        return i;
    }

    public boolean isCunZai() {
        boolean flag = false;
        String un = userNameText.getText();
        // 链接数据库
        Connection con = JDBCUtil.getConnection();
        PreparedStatement psm = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            psm = con.prepareStatement(sql);
            psm.setString(1, un);
            rs = psm.executeQuery();
            while (rs.next()) {
                flag = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close();
        }
        return flag;
    }
}
