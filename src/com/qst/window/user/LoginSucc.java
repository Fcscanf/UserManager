package com.qst.window.user;

import com.qst.dbutils.JDBCUtil;
import com.qst.user.User;
import com.qst.utils.Logsave;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LoginSucc extends JFrame {
    private JPanel contentPane;
    private DefaultTableModel model;
    private JScrollPane jScrollPane;
    private User user;
    private int currentPage = 1;
    private MyTable table;

    /*测试方法*/
    public static void main(String[] args) throws IOException {
        new LoginSucc();
    }

    public LoginSucc() throws IOException {
        super("User");

        JLabel label = new JLabel("用户列表");

        JButton find = new JButton("查询");
        find.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showData();
            }
        });

        JButton delete = new JButton("删除");
        delete.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteData();
            }
        });

        JButton change = new JButton("修改");
        change.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    change();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        JButton flush = new JButton("刷新");
        flush.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showData();
            }
        });

        JPanel panel = new JPanel();
        model = new DefaultTableModel();
        showData();
        table = new MyTable(model);
        panel.add(table);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jScrollPane = new JScrollPane(table);
        this.add(jScrollPane, BorderLayout.CENTER);
        panel.add(find);
        panel.add(delete);
        panel.add(change);
        panel.add(flush);
        this.add(panel,BorderLayout.SOUTH);

        JButton button = new JButton("上一页");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                currentPage = currentPage - 1;
                showData();
                table.hideCol(1);
            }
        });
        panel.add(button);

        JButton button_1 = new JButton("下一页");
        button_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                currentPage = currentPage + 1;
                showData();
                table.hideCol(1);
            }
        });
        panel.add(button_1);

        this.show();
        this.setSize(500,400);
        this.setLocation(200,100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

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
                    logSvr.logMsg(tmpLogFile, "管理员进行登录操作!\r");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, 50, TimeUnit.SECONDS);
    }
    /*
    * 读取数据users表，将数据显示到表格*/
    /**
     * 按分页显示数据
     */
    public void showData() {
        int total = getAllUser().size();
        int totalPage = total % 5 > 0 ? (total / 5 + 1) : total / 5;
        if (currentPage > totalPage) {
            currentPage = totalPage;
        }

        if (currentPage < 1) {
            currentPage = 1;
        }

        java.util.List<User> list = new ArrayList<>();
        Connection con = JDBCUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM users limit ?,5";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, (currentPage - 1) * 5);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUid(rs.getInt(1));
                user.setUsername(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setEmail(rs.getString(4));
                user.setAge(rs.getInt(5));
                user.setSex(rs.getInt(6));
                user.setAddress(rs.getString(7));
                list.add(user);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            JDBCUtil.close(con, null, ps, rs);
        }

        String[][] o = new String[list.size()][5];
        for (int i = 0; i < list.size(); i++) {
            User u = list.get(i);
            o[i][0] = (i + 1 + (currentPage - 1) * 5) + "";
            o[i][1] = u.getUid() + "";
            o[i][2] = u.getUsername();
            o[i][3] = u.getPassword();
            o[i][4] = u.getAge() + "";
            o[i][4] = u.getSex() + "";
            o[i][4] = u.getEmail() + "";
            o[i][4] = u.getAddress() + "";
        }

        String[] ts = new String[] { "序号", "ID", "用户名", "密码", "年龄","性别","Email","地址"};
        Vector<String> title = new Vector<String>();
        for (String t : ts) {
            title.add(t);
        }

        Vector<Vector<Object>> vl = new Vector<Vector<Object>>();
        for (int i = 0; i < list.size(); i++) {
            Vector<Object> v = new Vector<Object>();
            User u = list.get(i);
            v.add(i + 1 + (currentPage - 1) * 5);
            v.add(u.getUid());
            v.add(u.getUsername());
            v.add(u.getPassword());
            v.add(u.getAge());
            v.add(u.getSex());
            v.add(u.getEmail());
            v.add(u.getAddress());
            vl.add(v);
        }

        if (list.size() > 0) {
            model.setDataVector(vl, title);
        } else {
            model.setDataVector(null, title);
        }

    }

    /**
     * 查询所有的数据
     * @return
     */
    public List<User> getAllUser() {
        List<User> list = new ArrayList<User>();

        Connection con = JDBCUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM users";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setUid(rs.getInt(1));
                user.setUsername(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setEmail(rs.getString(4));
                user.setAge(rs.getInt(5));
                user.setSex(rs.getInt(6));
                user.setAddress(rs.getString(7));
                list.add(user);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            JDBCUtil.close(con, null, ps, rs);
        }
        return list;
    }
    /*修改操作*/
    public void change() throws IOException {
        int index[] = table.getSelectedRows();
        if(index.length == 0){
            JOptionPane.showMessageDialog(this, "请选择要修改的记录","提示",JOptionPane.PLAIN_MESSAGE);
        } else {
            Change change = new Change(table);
            change.setVisible(true);
        }
    }

    /*
    * 删除数据*/
    public void deleteData(){
        int index[] = table.getSelectedRows();
        if (index.length == 0){
            JOptionPane.showMessageDialog(this,"请选择要删除的记录：","提示",JOptionPane.PLAIN_MESSAGE);
        }else {
            try{
                int k = JOptionPane.showConfirmDialog(this,"您确定要从数据库删除所选数据吗？","删除",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
                if (k == JOptionPane.YES_OPTION){
                    try{
                        Connection con = JDBCUtil.getConnection();
                        PreparedStatement psm = null;
                        String sql ="delete from users where uid = ?";
                        psm = con.prepareStatement(sql);
                        String uid = table.getValueAt(index[0],0).toString();
                        psm.setString(1,uid);
                        int count = psm.executeUpdate();
                        if(count == 1){
                            JOptionPane.showMessageDialog(this,"成功删除！","成功",JOptionPane.PLAIN_MESSAGE);
                            showData();
                        }else {
                            JOptionPane.showMessageDialog(this,"抱歉，删除数据失败！","失败",0);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        JDBCUtil.close();
                    }
                }
            }catch (Exception ee){
                JOptionPane.showMessageDialog(this,"抱歉，删除数据失败！【系统异常！】","失败",0);
            }
        }
    }
}
