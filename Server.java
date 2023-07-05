package Test;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.AncestorListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.List;
class Server extends JFrame implements Runnable, ActionListener {
    private ServerSocket ss = null;
    private ArrayList<ChatThread> users = new ArrayList<ChatThread>();//存全部的客户端
    private ArrayList<ChatThread> users1 = new ArrayList<ChatThread>();//存群1全部的客户端
    private ArrayList<ChatThread> users2 = new ArrayList<ChatThread>();//存群2全部的客户端
    private ArrayList<ChatThread> users3 = new ArrayList<ChatThread>();//存群3全部的客户端
    private ArrayList<ChatThread> users4 = new ArrayList<ChatThread>();//存群4全部的客户端
    private JPanel jpl=new JPanel();//创建一个JPanel对象//面板

    private JTextField jtf=new JTextField();

    BufferedReader br = null;
    PrintStream ps = null;
    File f = new File("data.txt");
    //构造函数，窗口设计
    public Server(String str) throws Exception {
        super(str);
        //窗口设计
        this.setSize(400,500);
        //this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        // 添加窗口关闭事件监听器
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                PrintWriter writer = null;
                try {
                    writer = new PrintWriter(new FileWriter(f));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                writer.print("");
                System.exit(0);
            }
        });
        this.add(jpl);
        //this.add(jpltxt);
        this.setVisible(true);
        jpl.setSize(400,300);
        jpl.setLayout(new GridLayout(15, 1));//布局方式

        jpl.add(jtf);
        jtf.setFont(new Font("楷体",Font.BOLD,15));
        jtf.setText("系统消息：");
        jtf.addActionListener(this);
        //创建服务器连接
        ss = new ServerSocket(8888);
        new Thread(this).start();//启动线程
    }
    //客户端不断接入，入群提醒（判断是哪个群，广播上线通知，添加踢出按钮，开始读取客户端消息的线程）
    public void run(){
        while(true){
            try{
                Socket s = ss.accept();//接受连接
                br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                ps = new PrintStream(s.getOutputStream());
                String acc = br.readLine();//读取客户端信息
                String[] st = acc.split(":");//见到：就分割
                //debug用的
                System.out.println("服务器收到的消息acc:"+acc);
                System.out.println("用户"+st[1]+"上线");
                //群发 : 判断是哪个群，广播上线通知，添加踢出按钮，开始读取客户端消息的线程
                if(st[0].equals("娱乐资讯 你好")){
                    ChatThread ct = new ChatThread(s,st[0],st[1]);
                    users1.add(ct);	//有新客户端连接，加入users列表中
                    users.add(ct);
                    for(ChatThread c : users1){
                        c.psc.println("---------------用户 "+st[1]+" 进入群聊---------------");
                    }
                    ct.Button(s,st[1]);
                    ct.start();
                }else if(st[0].equals("读书分享 你好")){
                    //select=1;
                    ChatThread ct = new ChatThread(s,st[0],st[1]);
                    users2.add(ct);	//有新客户端连接，加入users列表中
                    users.add(ct);
                    for(ChatThread c : users2){
                        c.psc.println("---------------用户 "+st[1]+" 进入群聊---------------");
                    }
                    ct.Button(s,st[1]);
                    ct.start();
                }else if(st[0].equals("潮流时尚 你好")){
                    //select=2;
                    ChatThread ct = new ChatThread(s,st[0],st[1]);
                    users3.add(ct);	//有新客户端连接，加入users列表中
                    users.add(ct);
                    for(ChatThread c : users3){
                        c.psc.println("---------------用户 "+st[1]+" 进入群聊---------------");
                    }
                    ct.Button(s,st[1]);
                    ct.start();
                }else if(st[0].equals("体育专谈 你好")){
                    //select=3;
                    ChatThread ct = new ChatThread(s,st[0],st[1]);
                    users4.add(ct);	//有新客户端连接，加入users列表中
                    users.add(ct);
                    for(ChatThread c : users4){
                        c.psc.println("---------------用户 "+st[1]+" 进入群聊---------------");
                    }
                    ct.Button(s,st[1]);
                    ct.start();
                }
            }catch(Exception ex){
            }
        }
    }
    //发送系统消息事件监听
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==jtf){
            System.out.println("事件监听启动，系统通知");
            System.out.println(jtf.getText());

            for(ChatThread c : users) {
                c.psc.println(jtf.getText());
            }
            jtf.setText("系统信息:");
        }
    }
    class ChatThread extends Thread{
        BufferedReader brc = null;
        PrintStream psc = null;
        String chatname;
        private JButton jbt = new JButton();
        boolean select1,select2,select3,select4,selectg=false;//分类的flag
        //构造转发需要的flag和输入输出流
        ChatThread(Socket s,String item,String name) throws Exception{
            chatname=name;
            //构造读数据、写数据
            brc = new BufferedReader(new InputStreamReader(s.getInputStream()));
            psc = new PrintStream(s.getOutputStream());
            if(item.equals("娱乐资讯 你好")){
                select1=true;
            }else if(item.equals("读书分享 你好")){
                select2=true;
            }else if(item.equals("潮流时尚 你好")){
                select3=true;
            }else if(item.equals("体育专谈 你好")){
                select4=true;
            }
        }
        //分组分发，广播
        public void run(){
            while(true){
                try{
                    String msg = brc.readLine();//读取客户端消息
                    System.out.println("服务器收到的消息:"+msg);
                    String[] leave = msg.split(" ");//见到：就分割
                    //判断是不是广播信息
                    if(leave[0].equals("广播:")){
                        System.out.println("广播");
                        selectg=true;
                    }
                    //群转发
                    if(select1==true||selectg==true){
                        System.out.println("in 1");
                        for(ChatThread c : users1) {
                            //System.out.println(msg);
                            c.psc.println(msg);
                        }
                    }
                    if(select2==true||selectg==true){
                        //System.out.println("in 2");
                        for(ChatThread ct : users2){
                            ct.psc.println(msg);
                        }
                    }
                    if(select3==true||selectg==true){
                        //System.out.println("in 3");
                        for(ChatThread ct : users3){
                            ct.psc.println(msg);
                        }
                    }
                    if(select4==true||selectg==true){
                        //System.out.println("in 4");
                        for(ChatThread ct : users4){
                            ct.psc.println(msg);
                        }
                    }
                    selectg=false;
                    //移除按钮
                    if(leave[2].equals("退出群聊---------------")){
                        if(select1==true){
                            users1.remove(this);removejbt();
                        }else if(select2==true){
                            users2.remove(this);removejbt();
                        }else if(select3==true){
                            users3.remove(this);removejbt();
                        }else if(select4==true){
                            users4.remove(this);removejbt();
                        }
                    }
                }catch(Exception e){}
            }
        }
        //为每个客户端建立“踢出”按钮
        public void Button(Socket s,String Btext){
            jbt.setText(Btext);
            jpl.add(jbt);
            jbt.setBackground(new Color(201,192,211));
            jbt.setFont(new Font("宋体",Font.BOLD,15));
            jbt.addActionListener(e -> {
                try {
                    s.close();
                    JOptionPane.showMessageDialog(null, "成功踢出");
                    jpl.remove(jbt);  // 从JPanel中移除按钮
                    jpl.revalidate();    // 重新布局
                    jpl.repaint();       // 重绘界面
                    rewrite();
                    if(select1==true){
                        for(ChatThread ct : users1) {
                            ct.psc.println("---------------用户 "+jbt.getText()+" 被移出聊天室---------------");
                        }
                        users1.remove(this);
                        //s.close();
                    }else if(select2==true){
                        for(ChatThread ct : users2) {
                            ct.psc.println("---------------用户 "+jbt.getText()+" 被移出聊天室---------------");
                        }
                        users2.remove(this);

                    }else if(select3==true){
                        for(ChatThread ct : users3) {
                            ct.psc.println("---------------用户 "+jbt.getText()+" 被移出聊天室---------------");
                        }
                        users3.remove(this);
                    }else if(select4==true){
                        for(ChatThread ct : users4) {
                            ct.psc.println("---------------用户 "+jbt.getText()+" 被移出聊天室---------------");
                        }
                        users4.remove(this);
                    }
                }catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
        public void removejbt() throws IOException {
            rewrite();
            jpl.remove(jbt);  // 从JPanel中移除按钮
            jpl.revalidate();    // 重新布局
            jpl.repaint();       // 重绘界面
        }
        public void rewrite() throws IOException {
            BufferedReader br = new BufferedReader(new FileReader(f));
            List<String> list = new ArrayList<>();//定义一个 list 字符串集合用来储存每一行的字符串信息
            String str;
            while ((str = br.readLine()) != null) {
                list.add(str);
            }
            for( String del:list) {//遍历寻找
                if(del.equals(jbt.getText())){
                    list.remove(del);//在集合中删除该行
                    FileWriter fDel = new FileWriter(f, false);//append传入 false 表示写入内容时将会覆盖文件中之前存在的内容
                    fDel.write("");//执行删除操作，写入空内容覆盖之前的内容
                    fDel.close();
                    break;
                }
            }
            //重新遍历一遍更改后的集合，将内容重新写入文件内
            PrintStream ps2 = new PrintStream(f);
            for (String newFile : list){
                ps2.println(newFile);
            }
            ps2.close();
        }
    }
    public static void main(String[] args) throws Exception   {
        new Server("java聊天室服务器");
    }
}
