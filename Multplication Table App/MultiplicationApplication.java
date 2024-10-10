import javax.swing.*;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.Cursor;
import java.awt.event.ActionEvent;

public class MultiplicationApplication extends JFrame{
    private Container c;
    private Color clr;
    private JTextArea ta;
    private JLabel imglabel,textlabel;
    private JButton clear;
    private JTextField tf;
    private ImageIcon img;
    private Font f1,f2,f3;
    private Cursor crsr;


    MultiplicationApplication(){
        initComponents();
    }

    public void initComponents(){
       c=this.getContentPane();
       c.setLayout(null);
       c.setBackground(Color.PINK);

       crsr=new Cursor(Cursor.HAND_CURSOR);

       f1=new Font("Times New Roman",Font.BOLD,20);
       f2=new Font("Times New Roman",Font.BOLD,25);
       f3=new Font("Times New Roman",Font.BOLD,15);

       img=new ImageIcon(getClass().getResource("image.png"));

       imglabel=new JLabel(img);
       imglabel.setBounds(22,15,img.getIconWidth(),img.getIconHeight());
       c.add(imglabel);

       textlabel=new JLabel("Enter any number : ");
       textlabel.setBounds(25,245,250,50);
       textlabel.setFont(f1);
       textlabel.setForeground(Color.MAGENTA);
       c.add(textlabel);
     
       tf=new JTextField();
       tf.setBounds(215,247,90,50);
       tf.setBackground(Color.ORANGE);
       tf.setFont(f2);
       tf.setHorizontalAlignment(JTextField.CENTER);
       c.add(tf);

       clear=new JButton("Clear");
       clear.setBounds(226,307,70,40);
       clear.setFont(f3);
       clear.setForeground(Color.red);
       clear.setBackground(Color.green);
       clear.setCursor(crsr);
       c.add(clear);

       ta=new JTextArea();
       ta.setBounds(19,355,305,297);
       ta.setBackground(Color.CYAN);
       ta.setForeground(Color.BLUE);
       ta.setFont(f2);
       c.add(ta);

       tf.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            String value=tf.getText();
            if(value.isEmpty())
            {
                JOptionPane.showMessageDialog(null,"You didn't put any number");
            }
            else
            {
                ta.setText("");

              int num=Integer.parseInt(tf.getText());
              for(int i=1;i<=10;i++){
                int result=num*i;
                String r=String.valueOf(result);
                String n=String.valueOf(num);
                String ii=String.valueOf(i);
                ta.append(n+" X "+ii+" = "+r+"\n");
              }
            }

        }
       });

       clear.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e){
               ta.setText("");
               tf.setText("");
        }
       });

    }

    public static void main(String[] args) {
        MultiplicationApplication frame=new MultiplicationApplication();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(300,20,360,700);
        frame.setTitle("Multiplication Table");
        frame.setResizable(false);
    }
}
