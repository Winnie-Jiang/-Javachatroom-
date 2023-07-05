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
    private ArrayList<ChatThread> users = new ArrayList<ChatThread>();//��ȫ���Ŀͻ���
    private ArrayList<ChatThread> users1 = new ArrayList<ChatThread>();//��Ⱥ1ȫ���Ŀͻ���
    private ArrayList<ChatThread> users2 = new ArrayList<ChatThread>();//��Ⱥ2ȫ���Ŀͻ���
    private ArrayList<ChatThread> users3 = new ArrayList<ChatThread>();//��Ⱥ3ȫ���Ŀͻ���
    private ArrayList<ChatThread> users4 = new ArrayList<ChatThread>();//��Ⱥ4ȫ���Ŀͻ���
    private JPanel jpl=new JPanel();//����һ��JPanel����//���

    private JTextField jtf=new JTextField();

    BufferedReader br = null;
    PrintStream ps = null;
    File f = new File("data.txt");
    //���캯�����������
    public Server(String str) throws Exception {
        super(str);
        //�������
        this.setSize(400,500);
        //this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        // ��Ӵ��ڹر��¼�������
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
        jpl.setLayout(new GridLayout(15, 1));//���ַ�ʽ

        jpl.add(jtf);
        jtf.setFont(new Font("����",Font.BOLD,15));
        jtf.setText("ϵͳ��Ϣ��");
        jtf.addActionListener(this);
        //��������������
        ss = new ServerSocket(8888);
        new Thread(this).start();//�����߳�
    }
    //�ͻ��˲��Ͻ��룬��Ⱥ���ѣ��ж����ĸ�Ⱥ���㲥����֪ͨ������߳���ť����ʼ��ȡ�ͻ�����Ϣ���̣߳�
    public void run(){
        while(true){
            try{
                Socket s = ss.accept();//��������
                br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                ps = new PrintStream(s.getOutputStream());
                String acc = br.readLine();//��ȡ�ͻ�����Ϣ
                String[] st = acc.split(":");//�������ͷָ�
                //debug�õ�
                System.out.println("�������յ�����Ϣacc:"+acc);
                System.out.println("�û�"+st[1]+"����");
                //Ⱥ�� : �ж����ĸ�Ⱥ���㲥����֪ͨ������߳���ť����ʼ��ȡ�ͻ�����Ϣ���߳�
                if(st[0].equals("������Ѷ ���")){
                    ChatThread ct = new ChatThread(s,st[0],st[1]);
                    users1.add(ct);	//���¿ͻ������ӣ�����users�б���
                    users.add(ct);
                    for(ChatThread c : users1){
                        c.psc.println("---------------�û� "+st[1]+" ����Ⱥ��---------------");
                    }
                    ct.Button(s,st[1]);
                    ct.start();
                }else if(st[0].equals("������� ���")){
                    //select=1;
                    ChatThread ct = new ChatThread(s,st[0],st[1]);
                    users2.add(ct);	//���¿ͻ������ӣ�����users�б���
                    users.add(ct);
                    for(ChatThread c : users2){
                        c.psc.println("---------------�û� "+st[1]+" ����Ⱥ��---------------");
                    }
                    ct.Button(s,st[1]);
                    ct.start();
                }else if(st[0].equals("����ʱ�� ���")){
                    //select=2;
                    ChatThread ct = new ChatThread(s,st[0],st[1]);
                    users3.add(ct);	//���¿ͻ������ӣ�����users�б���
                    users.add(ct);
                    for(ChatThread c : users3){
                        c.psc.println("---------------�û� "+st[1]+" ����Ⱥ��---------------");
                    }
                    ct.Button(s,st[1]);
                    ct.start();
                }else if(st[0].equals("����ר̸ ���")){
                    //select=3;
                    ChatThread ct = new ChatThread(s,st[0],st[1]);
                    users4.add(ct);	//���¿ͻ������ӣ�����users�б���
                    users.add(ct);
                    for(ChatThread c : users4){
                        c.psc.println("---------------�û� "+st[1]+" ����Ⱥ��---------------");
                    }
                    ct.Button(s,st[1]);
                    ct.start();
                }
            }catch(Exception ex){
            }
        }
    }
    //����ϵͳ��Ϣ�¼�����
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==jtf){
            System.out.println("�¼�����������ϵͳ֪ͨ");
            System.out.println(jtf.getText());

            for(ChatThread c : users) {
                c.psc.println(jtf.getText());
            }
            jtf.setText("ϵͳ��Ϣ:");
        }
    }
    class ChatThread extends Thread{
        BufferedReader brc = null;
        PrintStream psc = null;
        String chatname;
        private JButton jbt = new JButton();
        boolean select1,select2,select3,select4,selectg=false;//�����flag
        //����ת����Ҫ��flag�����������
        ChatThread(Socket s,String item,String name) throws Exception{
            chatname=name;
            //��������ݡ�д����
            brc = new BufferedReader(new InputStreamReader(s.getInputStream()));
            psc = new PrintStream(s.getOutputStream());
            if(item.equals("������Ѷ ���")){
                select1=true;
            }else if(item.equals("������� ���")){
                select2=true;
            }else if(item.equals("����ʱ�� ���")){
                select3=true;
            }else if(item.equals("����ר̸ ���")){
                select4=true;
            }
        }
        //����ַ����㲥
        public void run(){
            while(true){
                try{
                    String msg = brc.readLine();//��ȡ�ͻ�����Ϣ
                    System.out.println("�������յ�����Ϣ:"+msg);
                    String[] leave = msg.split(" ");//�������ͷָ�
                    //�ж��ǲ��ǹ㲥��Ϣ
                    if(leave[0].equals("�㲥:")){
                        System.out.println("�㲥");
                        selectg=true;
                    }
                    //Ⱥת��
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
                    //�Ƴ���ť
                    if(leave[2].equals("�˳�Ⱥ��---------------")){
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
        //Ϊÿ���ͻ��˽������߳�����ť
        public void Button(Socket s,String Btext){
            jbt.setText(Btext);
            jpl.add(jbt);
            jbt.setBackground(new Color(201,192,211));
            jbt.setFont(new Font("����",Font.BOLD,15));
            jbt.addActionListener(e -> {
                try {
                    s.close();
                    JOptionPane.showMessageDialog(null, "�ɹ��߳�");
                    jpl.remove(jbt);  // ��JPanel���Ƴ���ť
                    jpl.revalidate();    // ���²���
                    jpl.repaint();       // �ػ����
                    rewrite();
                    if(select1==true){
                        for(ChatThread ct : users1) {
                            ct.psc.println("---------------�û� "+jbt.getText()+" ���Ƴ�������---------------");
                        }
                        users1.remove(this);
                        //s.close();
                    }else if(select2==true){
                        for(ChatThread ct : users2) {
                            ct.psc.println("---------------�û� "+jbt.getText()+" ���Ƴ�������---------------");
                        }
                        users2.remove(this);

                    }else if(select3==true){
                        for(ChatThread ct : users3) {
                            ct.psc.println("---------------�û� "+jbt.getText()+" ���Ƴ�������---------------");
                        }
                        users3.remove(this);
                    }else if(select4==true){
                        for(ChatThread ct : users4) {
                            ct.psc.println("---------------�û� "+jbt.getText()+" ���Ƴ�������---------------");
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
            jpl.remove(jbt);  // ��JPanel���Ƴ���ť
            jpl.revalidate();    // ���²���
            jpl.repaint();       // �ػ����
        }
        public void rewrite() throws IOException {
            BufferedReader br = new BufferedReader(new FileReader(f));
            List<String> list = new ArrayList<>();//����һ�� list �ַ���������������ÿһ�е��ַ�����Ϣ
            String str;
            while ((str = br.readLine()) != null) {
                list.add(str);
            }
            for( String del:list) {//����Ѱ��
                if(del.equals(jbt.getText())){
                    list.remove(del);//�ڼ�����ɾ������
                    FileWriter fDel = new FileWriter(f, false);//append���� false ��ʾд������ʱ���Ḳ���ļ���֮ǰ���ڵ�����
                    fDel.write("");//ִ��ɾ��������д������ݸ���֮ǰ������
                    fDel.close();
                    break;
                }
            }
            //���±���һ����ĺ�ļ��ϣ�����������д���ļ���
            PrintStream ps2 = new PrintStream(f);
            for (String newFile : list){
                ps2.println(newFile);
            }
            ps2.close();
        }
    }
    public static void main(String[] args) throws Exception   {
        new Server("java�����ҷ�����");
    }
}
