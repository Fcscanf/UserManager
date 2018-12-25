package com.qst.window.user;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.qst.dbutils.JDBCUtil;
import com.qst.utils.Logsave;
import com.qst.utils.Md5Utils;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Change extends JFrame {

	private JPanel contentPane;
	private JTextField nameText;
	private JTextField codeText;
	private JTextField sexText;
	private JTextField ageText;
	private JPasswordField passwordField;
	private JTextField emailField;
	private JTextField addressField;
	private ButtonGroup buttonGroup;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Change frame = new Change(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Change(JTable table) throws IOException {

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
					logSvr.logMsg(tmpLogFile, "进行修改操作!\r");
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}, 0, 5000, TimeUnit.SECONDS);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel newname = new JLabel("新用户名：");
		newname.setBounds(54, 13, 97, 18);
		contentPane.add(newname);
		
		nameText = new JTextField();
		nameText.setBounds(124, 9, 208, 24);
		contentPane.add(nameText);
		nameText.setColumns(10);
		
		JLabel newcode = new JLabel("新 密 码：");
		newcode.setBounds(54, 49, 97, 18);
		contentPane.add(newcode);

		JLabel resex = new JLabel("性    别：");
		resex.setBounds(52, 84, 86, 18);
		contentPane.add(resex);
		
		JLabel reage = new JLabel("年    龄：");
		reage.setBounds(52, 119, 91, 18);
		contentPane.add(reage);
		
		ageText = new JTextField();
		ageText.setBounds(124, 114, 208, 24);
		contentPane.add(ageText);
		ageText.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(124, 48, 208, 21);
		contentPane.add(passwordField);

		JRadioButton jbMale = new JRadioButton("男");
		jbMale.setBounds(124, 82, 86, 23);
		contentPane.add(jbMale);

		JRadioButton jbFemale = new JRadioButton("女");
		jbFemale.setBounds(227, 82, 113, 23);
		contentPane.add(jbFemale);
		buttonGroup = new ButtonGroup();
		buttonGroup.add(jbMale);
		buttonGroup.add(jbFemale);

		JLabel label = new JLabel("邮箱：");
		label.setBounds(54, 158, 54, 15);
		contentPane.add(label);

		JLabel label_1 = new JLabel("地址：");
		label_1.setBounds(54, 195, 54, 15);
		contentPane.add(label_1);

		emailField = new JTextField();
		emailField.setBounds(124, 155, 208, 21);
		contentPane.add(emailField);
		emailField.setColumns(10);

		addressField = new JTextField();
		addressField.setBounds(124, 192, 208, 21);
		contentPane.add(addressField);
		addressField.setColumns(10);
		
		JLabel jieg = new JLabel("");
		jieg.setBounds(144, 273, 178, 18);
		contentPane.add(jieg);
       ImageIcon ii = new ImageIcon("C:/Users/23225/Desktop/11.png");
		
		JButton btnOk = new JButton("确定");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con =  JDBCUtil.getConnection();
				PreparedStatement psm = null;

				int index[] = table.getSelectedRows();

				String username = nameText.getText();
				String password = passwordField.getText();
				int sex = Integer.parseInt(jbFemale.isSelected()?"0":"1");
				int age = Integer.parseInt(ageText.getText());
				String email = emailField.getText();
				String address = addressField.getText();
				try {
					String id = table.getValueAt(index[0], 0 ).toString();
					String sql3 = "UPDATE users SET username = ?, password = ?, sex = ?, age = ?, email = ?, address = ?WHERE uid = ?";
					psm = con.prepareStatement(sql3);
					psm.setString(1, username);
					psm.setString(2, Md5Utils.md5(password));
					psm.setInt(3, sex);
					psm.setInt(4, age);
					psm.setString(5,email);
					psm.setString(6,address);
					psm.setString(7, id);
					psm.executeUpdate();
					jieg.setText("  修改成功！");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		btnOk.setBounds(53, 243, 113, 27);
		contentPane.add(btnOk);
		
		JButton nbutton = new JButton("返回");
		nbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LoginSucc fan = null;
				try {
					fan = new LoginSucc();
				} catch (IOException e) {
					e.printStackTrace();
				}
				fan.setVisible(true);
				dispose();
			}
		});
		nbutton.setBounds(219, 243, 113, 27);
		contentPane.add(nbutton);
		
        JLabel image = new JLabel("");
        image.setBounds(10,10,54,15);
        image.setIcon(ii);
        ii.setImage(ii.getImage().getScaledInstance(450,300, image.ABORT));
        image.setBounds(0, 0, 450, 300);
        contentPane.add(image);
	}
}
