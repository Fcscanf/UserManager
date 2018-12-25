package com.qst.window.user;

import com.qst.dbutils.JDBCUtil;
import com.qst.utils.Logsave;
import com.qst.utils.Md5Utils;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

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

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField userNameText;
	private JTextField pwdText;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 创建一个Frame容器.
	 */
	public Login() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("用户名：");
		lblNewLabel.setBounds(50, 57, 54, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("密码：");
		lblNewLabel_1.setBounds(50, 101, 54, 15);
		contentPane.add(lblNewLabel_1);
		
		userNameText = new JTextField();
		userNameText.setBounds(125, 54, 89, 21);
		contentPane.add(userNameText);
		userNameText.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(125, 98, 89, 21);
		contentPane.add(passwordField);
		passwordField.setColumns(10);


		JButton btnlogin = new JButton("登录");
		btnlogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(isLogin()) {
					try {
						LoginSucc frame = new LoginSucc();
						frame.setVisible(true);
						dispose();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else {
					System.out.println("登录失败");
				}
			}
		});
		btnlogin.setBounds(107, 187, 93, 23);
		contentPane.add(btnlogin);

		JButton btnregist = new JButton("注册");
		btnregist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//                在注册页面添加一个页面跳转方法，跳至登录界面
				try {
					Regist frame = new Regist();
					frame.setVisible(true);
					dispose();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnregist.setBounds(203, 188, 93, 23);
		contentPane.add(btnregist);

		JLabel image = new JLabel("");
		image.setBounds(10,10,54,15);
		ImageIcon ii = new ImageIcon("D:\\fcofficework\\Dome\\IDEA\\UserManager\\images\\1511670027132.jpeg");
		image.setIcon(ii);
		ii.setImage(ii.getImage().getScaledInstance(450,300, Image.SCALE_DEFAULT));
		image.setBounds(0,0,450,300);
		contentPane.add(image);

		final Logsave logSvr = new Logsave();
		final File tmpLogFile = new File("logrecord.txt");
		if(!tmpLogFile.exists()) {
			tmpLogFile.createNewFile();
		}
		//启动一个线程每5秒钟向日志文件写一次数据
		ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
		exec.scheduleWithFixedDelay(new Runnable(){
			public void run() {
				try {
					logSvr.logMsg(tmpLogFile, userNameText.getText()+"用户进行登录操作!\r");
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}, 0, 2400, TimeUnit.SECONDS);
	}
	
	/**
	 * 判断用户是否登录成功
	 * @return true 成功  false 失败
	 */
	public boolean isLogin() {
		boolean loginFlag = false;
		String un = userNameText.getText();
		String pwd = Md5Utils.md5(passwordField.getText());
		// 连接数据库
		Connection con = JDBCUtil.getConnection();
		PreparedStatement psm = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
		try {
			psm = con.prepareStatement(sql);
			psm.setString(1, un);
			psm.setString(2, pwd);
			rs = psm.executeQuery();
			while(rs.next()) {
				loginFlag = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close();
		}
		return loginFlag;
	}
	
}
