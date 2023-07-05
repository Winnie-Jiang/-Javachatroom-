package Test;

import javax.swing.*;
import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Client extends JFrame implements Runnable ,ActionListener{
    private JPanel jpl3=new JPanel();//����һ��JPanel����//���
    private JTextField jtf1 = new JTextField();
    private JTextArea jta1 = new JTextArea();
    private JButton jbt1 = new JButton("����");
    private File f = new File("data.txt");
    private BufferedReader brf = new BufferedReader(new FileReader(f));
    // private PrintStream ps_friend =new PrintStream(new FileOutputStream(f,false),true);

    private JPanel jpl=new JPanel();//����һ��JPanel����//���
    private JPanel jpl2=new JPanel();//����һ��JPanel����//���
    private JTextField jtf = new JTextField();
    private JTextArea jta = new JTextArea();
    private JButton jbt = new JButton("����");
    private JLabel jlbf = new JLabel(" �����б�");
    private JButton jbtf = new JButton("�鿴");
    private JButton jbtg = new JButton("�㲥");
    private BufferedReader br = null;
    private PrintStream ps = null;
    private String account = null;
    String [] accstr;
    public Client(String str) throws Exception{
        super(str);
        //�������
        this.setLocation(10, 20);
        this.setSize(595,600);
        this.add(jpl);//�����ӵ�������
        jpl.setLayout(null);//���ַ�ʽ������
        this.setVisible(true);
        //�����
        jpl.add(jtf);
        jtf.setSize(480,40);
        jtf.setLocation(0,522);
        jtf.setFont(new Font("����",Font.BOLD,15));
        //�����
        jpl.add(jta);
        jta.setSize(480,521);
        jta.setLocation(0,0);
        jta.setFont(new Font("����",Font.BOLD,20));
        //�����б�
        jpl.add(jpl2);
        jpl2.setSize(100,500);
        jpl2.setLocation(480,0);
        jpl2.add(jlbf,BorderLayout.NORTH);
        jlbf.setFont(new Font("����",Font.BOLD,20));
        jpl2.add(jbtf,BorderLayout.CENTER);
        jpl2.add(jbtg,BorderLayout.SOUTH);
        jbtf.setFont(new Font("����",Font.BOLD,20));
        jbtg.setFont(new Font("����",Font.BOLD,20));
        //���Ͱ�ť
        jpl.add(jbt);
        jbt.setSize(70,40);
        jbt.setLocation(490,522);
        jbt.setFont(new Font("����",Font.BOLD,15));
        jbt.setBackground(new Color(201,192,211));
        //�ǳƵ���Ϣ
        accstr = str.split(":");//�������ͷָ
        //���ø��Ի�����ɫ���������ֲ�ͬȺ��
        if(accstr[0].equals("������Ѷ ���")) {
            jta.setBackground(new Color(196, 214, 230));
        }else if(accstr[0].equals("������� ���")){
            jta.setBackground(new Color(234,207, 209));
        }else if(accstr[0].equals("����ʱ�� ���")){
            jta.setBackground(new Color(202, 195, 187));
        }else if(accstr[0].equals("����ר̸ ���")) {
            jta.setBackground(new Color(150, 164, 138));
        }
        //�¼�����
        jtf.addActionListener(e -> {
            ps.println(accstr[1] + ":" + jtf.getText());
            System.out.println(accstr[1] + ":" + jtf.getText());
            jtf.setText(""); //����ı�������
        });
        jbt.addActionListener(e -> {
            ps.println(accstr[1] + ":" + jtf.getText());
            System.out.println(accstr[1] + ":" + jtf.getText());
            jtf.setText(""); // ����ı�������
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


        //���ӷ�����
        Socket s = new Socket("127.0.0.1",8888);
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        ps = new PrintStream(s.getOutputStream());
        ps.println(str);//�Ȱѱ������Ϣ����ȥ
        //�˳���ť����
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                ps.println("---------------�û� "+accstr[1]+" �˳�Ⱥ��---------------");
                //System.exit(0);
                try {
                    dispose();
                    new Panel("��ӭ "+accstr[1]);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        new Thread(this).start();
    }

    //�㲥
    String g;
    public void guangbo(){
        g = JOptionPane.showInputDialog("������㲥����");
        ps.println("�㲥: "+g);
    }
    //˽����Ⱥ�����㲥���ж�,д�������
    public void run(){
        while(true){
            try{
                String msg = br.readLine();
                System.out.println("�յ�����Ϣ��"+msg);
                //�յ�Ⱥ����Ϣ
                String[] st = msg.split(":");//�������ͷָ
                String[] stri=msg.split(" ");
                if(st[0].equals(accstr[1])){
                    jta.append("��:"+st[1]+ "\n");
                }
                else if(st[0].equals("(˽��)")){
                    System.out.println("test in");
                    jta1.append(st[1]+":"+st[2]+"\n");
                }else if(st[0].equals("˽����")&&(stri[2].equals(accstr[1])||stri[0].equals("˽����:"+accstr[1]))){
                    System.out.println("�յ�");
                    chat chat=new chat(accstr[1] + " ��˽��");
                }

                else if(st[0].equals("�㲥")){
                    JOptionPane.showMessageDialog(null, "�û�"+accstr[1]+"�յ��㲥\n"+st[1]);
                }
                else{
                    jta.append(msg + "\n");
                }
               /*
                if(stri[1].equals(accstr[1])&&stri[2].equals("���Ƴ�������---------------")){
                    JOptionPane.showMessageDialog(null, "���ѱ�����Ա�߳�������");
                    System.exit(0);
                }*/


            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "���Ӳ��Ϸ�������ǿ������");
                System.exit(0);
            }
        }
    }
    public void actionPerformed(ActionEvent e){
    }
    public class friend extends JFrame {
        private File f = new File("data.txt");
        private BufferedReader brf = new BufferedReader(new FileReader(f));
        private JPanel jpl2=new JPanel();//����һ��JPanel����//���
        private JLabel jlbf = new JLabel("          ����Ի�");
        //���� �������
        public friend(String accstr) throws Exception{
            super(accstr);
            //�������
            this.setLocation(480, 20);
            this.setSize(295,600);
            //this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.add(jpl2);//�����ӵ�������
            this.setVisible(true);
            //�����б�
            this.add(jpl2);
            jpl2.setSize(100,500);
            jpl2.setLocation(480,0);
            jpl2.setLayout(new GridLayout(15, 1));//���ַ�ʽ
            jpl2.setBackground(new Color(234,207, 209));
            jpl2.add(jlbf);
            jlbf.setFont(new Font("����",Font.BOLD,20));
            read(accstr);
        }
        //��txt�ļ��л�ȡ���߿ͻ�
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
                jbtf.setFont(new Font("����",Font.BOLD,15));
                jbtf.addActionListener(e -> {
                    dispose();
                    ps.println("˽����:"+accstr + " �� " + jbtf.getText()  );//�������������Ϣ
                });
            }
            public void write(){
                jpl2.add(jbtf);
            }

        }
    }
    //˽�Ĵ�����Ϣ�ķ��ͣ�����������������������ת��
    public class chat extends JFrame {
        String []chat;
        chat(String s){
            super(s);
            //�������
            this.setLocation(480, 20);
            this.setSize(510,600);
            this.add(jpl3);//�����ӵ�������
            jpl3.setLayout(null);//���ַ�ʽ������
            this.setVisible(true);
            //�����
            jpl3.add(jtf1);
            jtf1.setSize(420,40);
            jtf1.setLocation(0,522);
            jtf1.setFont(new Font("����",Font.BOLD,15));
            //�����
            jpl3.add(jta1);
            jta1.setText("");
            jta1.setSize(495,521);
            jta1.setLocation(0,0);
            jta1.setBackground(new Color(224, 229, 223));
            jta1.setFont(new Font("����",Font.BOLD,20));
            //���Ͱ�ť
            jpl3.add(jbt1);
            jbt1.setSize(70,40);
            jbt1.setLocation(420,522);
            jbt1.setFont(new Font("����",Font.BOLD,15));
            jbt1.setBackground(new Color(201,192,211));
            chat=s.split(" ");
            //�¼�����
            jtf1.addActionListener(e -> {
                System.out.println("������˽��");
                ps.println("(˽��):"+ chat[0] + ":" + jtf1.getText());
                jtf1.setText(""); //����ı�������
            });
            jbt1.addActionListener(e -> {
                System.out.println("������˽��");
                ps.println("(˽��):"+chat[0] + ":" + jtf1.getText());
                jtf1.setText(""); // ����ı�������
            });

        }
    }
}
