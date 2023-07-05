package Test;

import javax.swing.*;
import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class Login  extends JFrame  implements ActionListener{

    private File f = new File("data.txt");
    private BufferedReader brf = new BufferedReader(new FileReader(f));
    FileOutputStream fos = new FileOutputStream(f, true);
    PrintStream ps2 = new PrintStream(fos);
    private JPanel jpl=new JPanel();//创建一个JPanel对象//面板
    private JTextField jtfz = new JTextField();//账号

    private JLabel jlb = new JLabel("欢迎进入JAVA聊天室");
    private JLabel jlbz = new JLabel("昵称：");
    private JButton jbty = new JButton("确认");
    private JButton jbtn = new JButton("取消");
    //构造函数：窗口的设计
    public Login(String str) throws Exception  {
        super(str);
        //窗口设计
        this.setLocation(400, 100);
        this.setSize(500,400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(jpl);//面板添加到容器中
        jpl.setLayout(null);//布局方式，任意
        this.setVisible(true);
        //欢迎字体
        jpl.add(jlb);
        jlb.setSize(500,100);
        jlb.setLocation(70,22);
        jlb.setFont(new Font("楷体",Font.BOLD,35));
        //昵称
        jpl.add(jlbz);
        jlbz.setSize(100,30);
        jlbz.setLocation(70,152);
        jlbz.setFont(new Font("楷体",Font.BOLD,25));
        jpl.add(jtfz);
        jtfz.setSize(250,30);
        jtfz.setLocation(150,152);
        jtfz.setFont(new Font("楷体",Font.BOLD,25));
        //按钮
        jpl.add(jbty);
        jbty.setSize(80,30);
        jbty.setLocation(100,252);
        jbty.setBackground(new Color(201,192,211));
        jbty.setFont(new Font("宋体",Font.BOLD,20));
        jbty.addActionListener(this);//监听绑定
        jpl.add(jbtn);
        jbtn.setSize(80,30);
        jbtn.setLocation(300,252);
        jbtn.setBackground(new Color(201,192,211));
        jbtn.setFont(new Font("宋体",Font.BOLD,20));
        jbtn.addActionListener(this);//监听绑定
    }
    //按钮事件监听
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==jbtn){
            System.exit(0);
        }
        if(e.getSource()==jbty){
            try {
                String str;
                boolean canlogin=true;
                while ((str = brf.readLine()) != null){
                    if(str.equals(jtfz.getText())){
                        JOptionPane.showMessageDialog(null, "昵称已存在！\n请重新命名");
                        canlogin=false;
                        dispose();
                        new Login("用户登入");
                        break;
                    }
                }
                brf.close();
                if(canlogin==true){
                    System.out.println(jtfz.getText());
                    this.setVisible(false);//隐藏界面
                    new Panel("欢迎 "+jtfz.getText());
                }else{
                    jtfz.setText("");
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    public static void main(String[] args) throws Exception   {
        new Login("用户登入");
    }
}
