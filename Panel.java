package Test;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Panel  extends JFrame  implements ActionListener {

    private File f = new File("data.txt");
    FileOutputStream fos = new FileOutputStream(f, true);
    PrintStream ps2 = new PrintStream(fos);
    private JPanel jpl=new JPanel();//创建一个JPanel对象//面板
    private JLabel jlb = new JLabel("请选择你喜欢的群组加入");
    private JLabel jlb2 = new JLabel("还有更多聊天室，敬请期待！");
    private JButton jbt1= new JButton("娱乐资讯");
    private JButton jbt2= new JButton("读书分享");
    private JButton jbt3= new JButton("潮流时尚");
    private JButton jbt4= new JButton("体育专谈");
    String []st;
    public Panel(String string) throws FileNotFoundException {
        super(string);
        //窗口设计
        this.setLocation(400, 100);
        this.setSize(500,400);
        //this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(jpl);//面板添加到容器中
        jpl.setLayout(null);//布局方式，任意
        this.setVisible(true);
        //退出按钮操作
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    rewrite();//重写文件
                    System.exit(0);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        //欢迎字体
        jpl.add(jlb);
        jlb.setSize(500,100);
        jlb.setLocation(70,22);
        jlb.setFont(new Font("楷体",Font.BOLD,30));
        jpl.add(jlb2);
        jlb2.setSize(500,100);
        jlb2.setLocation(135,290);
        jlb2.setFont(new Font("宋体",Font.BOLD,15));
        //按钮
        jpl.add(jbt1);
        jbt1.setSize(180,30);
        jbt1.setLocation(140,122);
        jbt1.setBackground(new Color(201,192,211));
        jbt1.setFont(new Font("宋体",Font.BOLD,20));
        jbt1.addActionListener(this);//监听绑定

        jpl.add(jbt2);
        jbt2.setSize(180,30);
        jbt2.setLocation(140,172);
        jbt2.setBackground(new Color(201,192,211));
        jbt2.setFont(new Font("宋体",Font.BOLD,20));
        jbt2.addActionListener(this);//监听绑定

        jpl.add(jbt3);
        jbt3.setSize(180,30);
        jbt3.setLocation(140,222);
        jbt3.setBackground(new Color(201,192,211));
        jbt3.setFont(new Font("宋体",Font.BOLD,20));
        jbt3.addActionListener( this);//监听绑定

        jpl.add(jbt4);
        jbt4.setSize(180,30);
        jbt4.setLocation(140,272);
        jbt4.setBackground(new Color(201,192,211));
        jbt4.setFont(new Font("宋体",Font.BOLD,20));
        jbt4.addActionListener(this);//监听绑定
        st=string.split(" ");//见到：就分割开
        ps2.println(st[1]);
        ps2.close();
    }
    //退出入群按钮就要重写用户名
    public void rewrite() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(f));
        List<String> list = new ArrayList<>();//定义一个 list 字符串集合用来储存每一行的字符串信息
        String str;
        while ((str = br.readLine()) != null) {
            list.add(str);
        }
        for( String del:list) {//遍历寻找
            if(del.equals(st[1])){
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==jbt1){
            try {
                this.setVisible(false);//隐藏界面
                new Client("娱乐资讯 你好:"+st[1]);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        if(e.getSource()==jbt2){
            try {
                this.setVisible(false);//隐藏界面
                new Client("读书分享 你好:"+st[1]);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        if(e.getSource()==jbt3){
            try {
                this.setVisible(false);//隐藏界面
                new Client("潮流时尚 你好:"+st[1]);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        }
        if(e.getSource()==jbt4){
            try {
                this.setVisible(false);//隐藏界面
                new Client("体育专谈 你好:"+st[1]);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        }
    }
}
