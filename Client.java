package Test;

import javax.swing.*;
import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Client extends JFrame implements Runnable ,ActionListener{
    private JPanel jpl3=new JPanel();//创建一个JPanel对象//面板
    private JTextField jtf1 = new JTextField();
    private JTextArea jta1 = new JTextArea();
    private JButton jbt1 = new JButton("发送");
    private File f = new File("data.txt");
    private BufferedReader brf = new BufferedReader(new FileReader(f));
    // private PrintStream ps_friend =new PrintStream(new FileOutputStream(f,false),true);

    private JPanel jpl=new JPanel();//创建一个JPanel对象//面板
    private JPanel jpl2=new JPanel();//创建一个JPanel对象//面板
    private JTextField jtf = new JTextField();
    private JTextArea jta = new JTextArea();
    private JButton jbt = new JButton("发送");
    private JLabel jlbf = new JLabel(" 好友列表");
    private JButton jbtf = new JButton("查看");
    private JButton jbtg = new JButton("广播");
    private BufferedReader br = null;
    private PrintStream ps = null;
    private String account = null;
    String [] accstr;
    public Client(String str) throws Exception{
        super(str);
        //窗口设计
        this.setLocation(10, 20);
        this.setSize(595,600);
        this.add(jpl);//面板添加到容器中
        jpl.setLayout(null);//布局方式，任意
        this.setVisible(true);
        //输入框
        jpl.add(jtf);
        jtf.setSize(480,40);
        jtf.setLocation(0,522);
        jtf.setFont(new Font("宋体",Font.BOLD,15));
        //输出框
        jpl.add(jta);
        jta.setSize(480,521);
        jta.setLocation(0,0);
        jta.setFont(new Font("楷体",Font.BOLD,20));
        //好友列表
        jpl.add(jpl2);
        jpl2.setSize(100,500);
        jpl2.setLocation(480,0);
        jpl2.add(jlbf,BorderLayout.NORTH);
        jlbf.setFont(new Font("宋体",Font.BOLD,20));
        jpl2.add(jbtf,BorderLayout.CENTER);
        jpl2.add(jbtg,BorderLayout.SOUTH);
        jbtf.setFont(new Font("宋体",Font.BOLD,20));
        jbtg.setFont(new Font("宋体",Font.BOLD,20));
        //发送按钮
        jpl.add(jbt);
        jbt.setSize(70,40);
        jbt.setLocation(490,522);
        jbt.setFont(new Font("宋体",Font.BOLD,15));
        jbt.setBackground(new Color(201,192,211));
        //昵称等信息
        accstr = str.split(":");//见到：就分割开
        //设置个性化的颜色，利于区分不同群组
        if(accstr[0].equals("娱乐资讯 你好")) {
            jta.setBackground(new Color(196, 214, 230));
        }else if(accstr[0].equals("读书分享 你好")){
            jta.setBackground(new Color(234,207, 209));
        }else if(accstr[0].equals("潮流时尚 你好")){
            jta.setBackground(new Color(202, 195, 187));
        }else if(accstr[0].equals("体育专谈 你好")) {
            jta.setBackground(new Color(150, 164, 138));
        }
        //事件监听
        jtf.addActionListener(e -> {
            ps.println(accstr[1] + ":" + jtf.getText());
            System.out.println(accstr[1] + ":" + jtf.getText());
            jtf.setText(""); //清空文本框内容
        });
        jbt.addActionListener(e -> {
            ps.println(accstr[1] + ":" + jtf.getText());
            System.out.println(accstr[1] + ":" + jtf.getText());
            jtf.setText(""); // 清空文本框内容
        });
        jbtf.addActionListener(e -> {
            try {
                new friend(accstr[1]);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        jbtg.addActionListener(e -> {
           guangbo();
        });


        //连接服务器
        Socket s = new Socket("127.0.0.1",8888);
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        ps = new PrintStream(s.getOutputStream());
        ps.println(str);//先把标题的信息传过去
        //退出按钮操作
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                ps.println("---------------用户 "+accstr[1]+" 退出群聊---------------");
                //System.exit(0);
                try {
                    dispose();
                    new Panel("欢迎 "+accstr[1]);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        new Thread(this).start();
    }

    //广播
    String g;
    public void guangbo(){
        g = JOptionPane.showInputDialog("请输入广播内容");
        ps.println("广播: "+g);
    }
    //私发、群发、广播的判断,写入聊天框
    public void run(){
        while(true){
            try{
                String msg = br.readLine();
                System.out.println("收到的信息："+msg);
                //收到群聊消息
                String[] st = msg.split(":");//见到：就分割开
                String[] stri=msg.split(" ");
                if(st[0].equals(accstr[1])){
                    jta.append("我:"+st[1]+ "\n");
                }
                else if(st[0].equals("(私聊)")){
                    System.out.println("test in");
                    jta1.append(st[1]+":"+st[2]+"\n");
                }else if(st[0].equals("私聊中")&&(stri[2].equals(accstr[1])||stri[0].equals("私聊中:"+accstr[1]))){
                    System.out.println("收到");
                    chat chat=new chat(accstr[1] + " 的私聊");
                }

                else if(st[0].equals("广播")){
                    JOptionPane.showMessageDialog(null, "用户"+accstr[1]+"收到广播\n"+st[1]);
                }
                else{
                    jta.append(msg + "\n");
                }
               /*
                if(stri[1].equals(accstr[1])&&stri[2].equals("被移出聊天室---------------")){
                    JOptionPane.showMessageDialog(null, "您已被管理员踢出聊天室");
                    System.exit(0);
                }*/


            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "连接不上服务器，强制下线");
                System.exit(0);
            }
        }
    }
    public void actionPerformed(ActionEvent e){
    }
    public class friend extends JFrame {
        private File f = new File("data.txt");
        private BufferedReader brf = new BufferedReader(new FileReader(f));
        private JPanel jpl2=new JPanel();//创建一个JPanel对象//面板
        private JLabel jlbf = new JLabel("          点击对话");
        //构造 窗口设计
        public friend(String accstr) throws Exception{
            super(accstr);
            //窗口设计
            this.setLocation(480, 20);
            this.setSize(295,600);
            //this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.add(jpl2);//面板添加到容器中
            this.setVisible(true);
            //好友列表
            this.add(jpl2);
            jpl2.setSize(100,500);
            jpl2.setLocation(480,0);
            jpl2.setLayout(new GridLayout(15, 1));//布局方式
            jpl2.setBackground(new Color(234,207, 209));
            jpl2.add(jlbf);
            jlbf.setFont(new Font("宋体",Font.BOLD,20));
            read(accstr);
        }
        //从txt文件中获取在线客户
        public void read(String accstr) throws IOException {
            String str;
            while ((str = brf.readLine()) != null){
                if(str.equals(accstr)==false){
                    add a=new add(str,accstr);
                    a.write();
                }
            }
        }
        class add {
            private JButton jbtf = new JButton();
            add(String fri,String accstr){
                System.out.println(fri);
                jbtf.setText(fri);
                jbtf.setBackground(new Color(234,207, 209));
                jbtf.setFont(new Font("宋体",Font.BOLD,15));
                jbtf.addActionListener(e -> {
                    dispose();
                    ps.println("私聊中:"+accstr + " 与 " + jbtf.getText()  );//向服务器发送消息
                });
            }
            public void write(){
                jpl2.add(jbtf);
            }

        }
    }
    //私聊窗口信息的发送，发给服务器，服务器负责转发
    public class chat extends JFrame {
        String []chat;
        chat(String s){
            super(s);
            //窗口设计
            this.setLocation(480, 20);
            this.setSize(510,600);
            this.add(jpl3);//面板添加到容器中
            jpl3.setLayout(null);//布局方式，任意
            this.setVisible(true);
            //输入框
            jpl3.add(jtf1);
            jtf1.setSize(420,40);
            jtf1.setLocation(0,522);
            jtf1.setFont(new Font("宋体",Font.BOLD,15));
            //输出框
            jpl3.add(jta1);
            jta1.setText("");
            jta1.setSize(495,521);
            jta1.setLocation(0,0);
            jta1.setBackground(new Color(224, 229, 223));
            jta1.setFont(new Font("楷体",Font.BOLD,20));
            //发送按钮
            jpl3.add(jbt1);
            jbt1.setSize(70,40);
            jbt1.setLocation(420,522);
            jbt1.setFont(new Font("宋体",Font.BOLD,15));
            jbt1.setBackground(new Color(201,192,211));
            chat=s.split(" ");
            //事件监听
            jtf1.addActionListener(e -> {
                System.out.println("发送了私聊");
                ps.println("(私聊):"+ chat[0] + ":" + jtf1.getText());
                jtf1.setText(""); //清空文本框内容
            });
            jbt1.addActionListener(e -> {
                System.out.println("发送了私聊");
                ps.println("(私聊):"+chat[0] + ":" + jtf1.getText());
                jtf1.setText(""); // 清空文本框内容
            });

        }
    }
}
