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
    private JPanel jpl=new JPanel();//����һ��JPanel����//���
    private JLabel jlb = new JLabel("��ѡ����ϲ����Ⱥ�����");
    private JLabel jlb2 = new JLabel("���и��������ң������ڴ���");
    private JButton jbt1= new JButton("������Ѷ");
    private JButton jbt2= new JButton("�������");
    private JButton jbt3= new JButton("����ʱ��");
    private JButton jbt4= new JButton("����ר̸");
    String []st;
    public Panel(String string) throws FileNotFoundException {
        super(string);
        //�������
        this.setLocation(400, 100);
        this.setSize(500,400);
        //this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(jpl);//�����ӵ�������
        jpl.setLayout(null);//���ַ�ʽ������
        this.setVisible(true);
        //�˳���ť����
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    rewrite();//��д�ļ�
                    System.exit(0);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        //��ӭ����
        jpl.add(jlb);
        jlb.setSize(500,100);
        jlb.setLocation(70,22);
        jlb.setFont(new Font("����",Font.BOLD,30));
        jpl.add(jlb2);
        jlb2.setSize(500,100);
        jlb2.setLocation(135,290);
        jlb2.setFont(new Font("����",Font.BOLD,15));
        //��ť
        jpl.add(jbt1);
        jbt1.setSize(180,30);
        jbt1.setLocation(140,122);
        jbt1.setBackground(new Color(201,192,211));
        jbt1.setFont(new Font("����",Font.BOLD,20));
        jbt1.addActionListener(this);//������

        jpl.add(jbt2);
        jbt2.setSize(180,30);
        jbt2.setLocation(140,172);
        jbt2.setBackground(new Color(201,192,211));
        jbt2.setFont(new Font("����",Font.BOLD,20));
        jbt2.addActionListener(this);//������

        jpl.add(jbt3);
        jbt3.setSize(180,30);
        jbt3.setLocation(140,222);
        jbt3.setBackground(new Color(201,192,211));
        jbt3.setFont(new Font("����",Font.BOLD,20));
        jbt3.addActionListener( this);//������

        jpl.add(jbt4);
        jbt4.setSize(180,30);
        jbt4.setLocation(140,272);
        jbt4.setBackground(new Color(201,192,211));
        jbt4.setFont(new Font("����",Font.BOLD,20));
        jbt4.addActionListener(this);//������
        st=string.split(" ");//�������ͷָ
        ps2.println(st[1]);
        ps2.close();
    }
    //�˳���Ⱥ��ť��Ҫ��д�û���
    public void rewrite() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(f));
        List<String> list = new ArrayList<>();//����һ�� list �ַ���������������ÿһ�е��ַ�����Ϣ
        String str;
        while ((str = br.readLine()) != null) {
            list.add(str);
        }
        for( String del:list) {//����Ѱ��
            if(del.equals(st[1])){
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==jbt1){
            try {
                this.setVisible(false);//���ؽ���
                new Client("������Ѷ ���:"+st[1]);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        if(e.getSource()==jbt2){
            try {
                this.setVisible(false);//���ؽ���
                new Client("������� ���:"+st[1]);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        if(e.getSource()==jbt3){
            try {
                this.setVisible(false);//���ؽ���
                new Client("����ʱ�� ���:"+st[1]);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        }
        if(e.getSource()==jbt4){
            try {
                this.setVisible(false);//���ؽ���
                new Client("����ר̸ ���:"+st[1]);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        }
    }
}
