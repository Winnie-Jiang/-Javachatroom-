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
    private JPanel jpl=new JPanel();//����һ��JPanel����//���
    private JTextField jtfz = new JTextField();//�˺�

    private JLabel jlb = new JLabel("��ӭ����JAVA������");
    private JLabel jlbz = new JLabel("�ǳƣ�");
    private JButton jbty = new JButton("ȷ��");
    private JButton jbtn = new JButton("ȡ��");
    //���캯�������ڵ����
    public Login(String str) throws Exception  {
        super(str);
        //�������
        this.setLocation(400, 100);
        this.setSize(500,400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(jpl);//�����ӵ�������
        jpl.setLayout(null);//���ַ�ʽ������
        this.setVisible(true);
        //��ӭ����
        jpl.add(jlb);
        jlb.setSize(500,100);
        jlb.setLocation(70,22);
        jlb.setFont(new Font("����",Font.BOLD,35));
        //�ǳ�
        jpl.add(jlbz);
        jlbz.setSize(100,30);
        jlbz.setLocation(70,152);
        jlbz.setFont(new Font("����",Font.BOLD,25));
        jpl.add(jtfz);
        jtfz.setSize(250,30);
        jtfz.setLocation(150,152);
        jtfz.setFont(new Font("����",Font.BOLD,25));
        //��ť
        jpl.add(jbty);
        jbty.setSize(80,30);
        jbty.setLocation(100,252);
        jbty.setBackground(new Color(201,192,211));
        jbty.setFont(new Font("����",Font.BOLD,20));
        jbty.addActionListener(this);//������
        jpl.add(jbtn);
        jbtn.setSize(80,30);
        jbtn.setLocation(300,252);
        jbtn.setBackground(new Color(201,192,211));
        jbtn.setFont(new Font("����",Font.BOLD,20));
        jbtn.addActionListener(this);//������
    }
    //��ť�¼�����
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
                        JOptionPane.showMessageDialog(null, "�ǳ��Ѵ��ڣ�\n����������");
                        canlogin=false;
                        dispose();
                        new Login("�û�����");
                        break;
                    }
                }
                brf.close();
                if(canlogin==true){
                    System.out.println(jtfz.getText());
                    this.setVisible(false);//���ؽ���
                    new Panel("��ӭ "+jtfz.getText());
                }else{
                    jtfz.setText("");
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    public static void main(String[] args) throws Exception   {
        new Login("�û�����");
    }
}
