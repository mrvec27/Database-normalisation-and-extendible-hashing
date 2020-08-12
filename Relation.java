import java.util.*;
import java.lang.*;
//package net.codejava.swing;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.JTextArea;
import javax.swing.*;
import javax.swing.Timer;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
//import login.login;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import java.util.concurrent.TimeUnit;
import java.lang.*;
import java.awt.geom.*;
import javax.swing.JOptionPane;
 
/**
 * This class extends from OutputStream to redirect output to a JTextArrea
 * @author www.codejava.net
 *
 */
class CustomOutputStream extends OutputStream {
    private JTextArea textArea;
     
    public CustomOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }
     
    @Override
    public void write(int b) throws IOException {
        // redirects data to the text area
        textArea.append(String.valueOf((char)b));
        // scrolls the text area to the end of data
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}


class ImagePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private Image img;
    public ImagePanel(String img) {
      this(new ImageIcon(img).getImage());
    }
    public ImagePanel(Image img) {
      this.img = img;
      Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
      setPreferredSize(size);
      setMinimumSize(size);
      setMaximumSize(size);
      setSize(size);
      setLayout(null);
    }
    @Override
    public void paintComponent(Graphics g) {
      g.drawImage(img, 0, 0, null);
    }
  }



class Dialog{  
    private static JDialog d;  
    Dialog(String str) {  
        JFrame f= new JFrame();  
        d = new JDialog(f , "SAFAR", true); 
        d.getContentPane().setBackground(Color.BLACK);
        d.getContentPane().setForeground(Color.WHITE);
        
        d.setLayout( new FlowLayout() );  
        JButton b = new JButton ("OK");  
        b.addActionListener ( new ActionListener()  
        {  
            public void actionPerformed( ActionEvent e )  
            {  
                Dialog.d.setVisible(false);  
            }  
        });
        JLabel l1=new JLabel(str);  
        l1.setForeground(Color.WHITE);
        d.add(l1);  
        d.add(b);
        d.setLocationRelativeTo(null);   
        d.setSize(400,130);    
        d.setVisible(true);  
    }
    Dialog(String str,int t) {  
        JFrame f= new JFrame();  
        
        d = new JDialog(f , "SAFAR", true);
        d.getContentPane().setBackground(Color.BLACK);
        d.getContentPane().setForeground(Color.WHITE);
        d.setLayout( new FlowLayout() ); 

        JLabel l1=new JLabel(str);  
        l1.setForeground(Color.WHITE); 
        d.add(l1);  
        d.setLocationRelativeTo(null);   
        d.setSize(400,130);     
        d.setVisible(true);
    } 
}




class Relation implements ActionListener
{
    static boolean[] BCNFvis = new boolean[26];
    static int no_of_fds;
    static String[][] fds;
    static String Key;
    static String attributes;
    private PrintStream standardOut;
    private JTextArea textArea;
    JTextField t1;
    JTextField t2;  
    JLabel l1,l2,l3;
    JButton b1,b2;
    JPanel p;

    Relation(/*String att, int num , String[][] fd*/) throws IOException
    {
        ImagePanel panel = new ImagePanel(new ImageIcon(getClass().getResource("front.jpg")).getImage());
        p=new JPanel(null);  
        
        t1=new JTextField();  
        t1.setBounds(550,200,250,30);  
        //t1.setBackground(Color.BLACK);
        //t1.setForeground(Color.white);
        t2=new JTextField();  
        t2.setBounds(550,270,250,30);  
        textArea =new JTextArea();  
        textArea.setBounds(350,420,370,150);

          
        l1=new JLabel("ENTER RELATION"); 
        l1.setForeground(Color.white); 
        l1.setBounds(350,200,300,30);  
        l2=new JLabel("NO OF FDS");  
        l2.setBounds(350,270,250,30);
        l2.setForeground(Color.white);
       
        b1 = new JButton("NEXT");  
        b1.setBounds(470,340,100,30);
       
        b1.addActionListener(this);  
       
        p.add(b1);   
        p.add(l1); p.add(l2); 
        p.add(t1); p.add(t2);
        p.add(textArea);
        p.add(panel);

    
    }

    public void actionPerformed(ActionEvent e) 
    {  
          
        if(e.getSource() == b1)
        {  
            int flag=0;
            String s1_relation_attributes = t1.getText();
            if(s1_relation_attributes.equals(""))
            {
                new Dialog("Please Enter Relation");
                flag=1;
            }
            String s2 = t2.getText();   
            if(s2.equals("") && flag==0)
            {
                new Dialog("Please Enter NO OF FD'S");
                flag=1;
            }
            if(flag==0)
            {
                int numFD=Integer.parseInt(s2);
                fds = new String[numFD][2];
                
                for(int i = 0; i < numFD; ++i)
                {
                    String FDS;
                    FDS = JOptionPane.showInputDialog("ENTER THE FD ");

                        String curr = FDS;
                        fds[i]=curr.split(" ",2);
                        fds[i][0]=sortString(fds[i][0]);
                        fds[i][1]=sortString(fds[i][1]);
                }
                try
                {
                            no_of_fds = numFD;
                            attributes = s1_relation_attributes;
                            
                            for(int i=0;i<26;i++)
                               BCNFvis[i]=false;
                           PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
                             
                            // keeps reference of standard output stream
                            standardOut = System.out;
                             
                            // re-assigns standard output stream and error output stream
                            System.setOut(printStream);
                            System.setErr(printStream);
                            check();
                }
                catch(Exception e0)
                    {
                        System.out.println(e0);
                    }
            }       
        }
    }

    public static String sortString(String inputString)
    {
        char tempArray[] = inputString.toCharArray();
        Arrays.sort(tempArray);
        return new String(tempArray);
    }

   public static String closure(String attributes, String X, int no_of_fds, String fds[][])
    {
        String closure=X;
        String newclosure=X;
        do{
            closure=newclosure;
            for(int i=0;i<no_of_fds;i++)
            {
                boolean flag=true;
                for(int j=0; j<fds[i][0].length();j++)
                {
                    String temp=""+fds[i][0].charAt(j);
                    if(attributes.contains(temp))
                        continue;
                    else
                    {
                        flag=false;
                        break;
                    }
                }
                for(int j=0; j<fds[i][0].length();j++)
                {
                    String temp=""+fds[i][0].charAt(j);
                    if(newclosure.contains(temp))
                        continue;
                    else
                    {
                        flag=false;
                        break;
                    }
                }for(int j=0; j<fds[i][1].length();j++)
                {
                    String temp=""+fds[i][1].charAt(j);
                    if(attributes.contains(temp))
                        continue;
                    else
                    {
                        flag=false;
                        break;
                    }
                }
                if(flag) //CHANGE HERE
                {
                    boolean flag1=true;
                    for(int j=0; j<fds[i][1].length();j++)
                    {
                    String temp=""+fds[i][1].charAt(j);
                    if(newclosure.contains(temp))
                        continue;
                    else
                    {
                        flag1=false;
                        break;
                    }
                    }
                    if(flag1)
                        continue;
                    else
                    {
                        for(int h=0;h<fds[i][1].length();h++)
                        {
                            String st = "";
                            st+=fds[i][1].charAt(h);
                            if(!(newclosure.contains(st)))
                            {
                                newclosure+=st;
                            }
                        }
                        newclosure=sortString(newclosure);
                    }
                }
            }

        }while(!(closure.equals(newclosure)));
       

        boolean[] freq = new boolean[26];

        for(int i=0;i<26;i++)
            freq[i]=false;

        for(int i=0;i<newclosure.length();i++){
           
            freq[newclosure.charAt(i)-'a']=true;
        }

        closure="";

        for(int i=0;i<26;i++)
            if(freq[i])
                closure+=(char)('a'+i);
       
        return closure;
    }

    public static String key(String attributes, String fds[][], int no_of_fds)
    {
        String key=attributes;
       String set=attributes;
       int n = set.length(); 
        int no_of_subsets= (1<<n);
        String[] subsets=new String[no_of_subsets];
        int subset_count=0;
        // Run a loop for printing all 2^n 
        // subsets one by one 
        for (int i = 0; i < (1<<n); i++) 
        { 
            //System.out.print("{ "); 
    String curr="";
            // Print current subset 
            for (int j = 0; j < n; j++) 
                if ((i & (1 << j)) > 0) 
                   // System.out.print(set[j] + " "); 
                    curr=curr+set.charAt(j);
                    subsets[subset_count++]=curr;
           // System.out.println("}"); 
        }


        int leng=attributes.length();
        for(int k=0;k<no_of_subsets;k++)
        {
              String temp=sortString(subsets[k]);

              if((closure(attributes,temp,no_of_fds,fds)).equals(attributes) && key.length()>temp.length())
                {
                    if(temp.length()<leng){key=temp;}
//                    System.out.println(temp + " " + closure(temp,no_of_fds,fds));
                }
//            }
        }

        return key;
    }



    public static boolean convto1NF(String attributes, String Key)
    {
       /* System.out.println("Are there any multivalued attributes?(y/n)");
        char ch=sc.next().charAt(0);
        sc.nextLine();
        if(ch=='y')
        {
            System.out.println("Unnormalized table\nEnter multivalued attributes to convert to 1NF:");
            String multi_attributes=sc.nextLine();

            boolean[] freq = new boolean[26];

            for(int i=0;i<26;i++)
                freq[i]=false;

            for(int i=0;i<multi_attributes.length();i++)
                freq[multi_attributes.charAt(i)-'a']=true;

            String temp="";

            for(int i=0;i<attributes.length();i++)
            {
                if(freq[attributes.charAt(i)-'a'])
                    continue;
                else
                    temp+=attributes.charAt(i);
            }

            attributes=temp;
            System.out.println("1NF:\nTable 1: "+attributes);
            for(int i=0;i<multi_attributes.length();i++)
            {
                int j=i+1;
                System.out.println("Table "+(j)+": "+Key+multi_attributes.charAt(i));
            }
            return false;
        }
        else*/
            return true;

    }

    public static boolean convto2NF(String attributes, String Key, int no_of_fds, String fds[][])
    {
        Scanner sc = new Scanner(System.in);
        if(Key.length()==1)
            return true;

        boolean[] vis = new boolean[26];

        for(int i=0;i<26;i++)
           vis[i]=false;

        int no_of_subsets=(Key.length()*(Key.length()+1))/2;

        //String[][] fds = new String[no_of_fds][2];
        String[] subsets=new String[no_of_subsets];
        int subset_count=0;

        for(int len=1;len<=Key.length(); len++)
        {
            for(int i=0;i<=Key.length()-len;i++)
            {

                int j = i + len - 1;
                String curr="";

                for(int k=i;k<=j;k++)
                    curr+=Key.charAt(k);

                subsets[subset_count++]=curr;
            }
        }

        boolean check=true;
        String full_check="";
        int table_no=0;

        for(int i=0;i<no_of_subsets-1;i++)
        {
            for(int x=0;x<no_of_fds;x++)
               { boolean f=true;
                for(int j=0; j<fds[x][0].length();j++)
                {
                    String temp=""+fds[x][0].charAt(j);
                    if(subsets[i].contains(temp))
                        continue;
                    else
                        {f=false; 
                            break;}
                }
                //(subsets[i].contains(fds[x][0])) 
                if(f)
                {
                    if(check)
                    {
                        System.out.println("Relation is in 1NF");
                        System.out.println("\n");
                        System.out.println("Relation in 1NF form converted to 2NF:");
                    }

                    check=false;
                    String ans="";

                    for(int m=0;m<fds[x][1].length();m++)
                        if(!vis[(fds[x][1].charAt(m))-'a'])
                        {
                            ans+=fds[x][1].charAt(m)+" ";
                            vis[(fds[x][1].charAt(m))-'a']=true;
                        }

                    if(ans.length()!=0){
                        System.out.print("Table:"+(++table_no)+" ");
                        for(int h=0;h<subsets[i].length();h++)
                        {
                            System.out.print(subsets[i].charAt(h)+" ");
                        }
                        System.out.println(ans);
                    }
                   

                    for(int m=0;m<26;m++)
                        if(vis[m])
                           full_check+=(char)('a'+m);

                    full_check  = sortString(full_check);
                    if(full_check.equals(attributes))
                        break;
                }
            }
            if(full_check.equals(attributes))
                    break;
        }
        if(!check)
        {
            String temp="";
            for(int i=0;i<attributes.length();i++)
                if(vis[attributes.charAt(i)-'a']==false)
                {
                    temp+=attributes.charAt(i)+" ";
                }
            if(temp.length()!=0)
            System.out.println("Table "+(++table_no)+": "+temp);

        }

        return check;
    }

    public static boolean convto3NF( String attributes, String fds[][], int no_of_fds,String Key )
    {

        boolean[] vis = new boolean[26];
        boolean check=true;

        for(int i=0;i<26;i++)
           vis[i]=false;

        int t_no=0;
        for(int i=0;i<no_of_fds;i++)
        {
            String newTable="";
            boolean flag3=true;
            for(int j=0; j<fds[i][1].length();j++)
            {
                String temp=""+fds[i][1].charAt(j);
                if(Key.contains(temp))
                    continue;
                else
                {
                    flag3=false;
                    break;
                }
            }
               //f(!fds[i][0].equals(Key) && !Key.contains(fds[i][1]))
              if(!fds[i][0].equals(Key) && !flag3)
                {
                    if(check)
                    {
                        System.out.println("Relation is in 2NF");
                        System.out.println("\n");
                        System.out.println("Relation in 2NF form converted to 3NF:");
                    }
                    

                    check=false;
                    for(int k=0;k<fds[i][0].length();k++){
                        newTable+=fds[i][0].charAt(k)+" ";
                    }

                    for(int k=0;k<fds[i][1].length();k++)
                    {
                        String key_check = "";
                        key_check+=fds[i][1].charAt(k);
                        if(vis[fds[i][1].charAt(k)-'a']==false && !key_check.equals(Key))
                            {
                                 newTable+=fds[i][1].charAt(k)+" ";
                                 vis[fds[i][1].charAt(k)-'a']=true;
                            }
                    }
                    System.out.println("Table "+(++t_no)+": "+newTable);
                }
        }

        if(!check)
        {
            String temp="";
            for(int i=0;i<attributes.length();i++)
                if(vis[attributes.charAt(i)-'a']==false)
                {
                    temp+=attributes.charAt(i)+" ";
                }
            if(temp.length()!=0)
            System.out.println("Table "+(++t_no)+": "+temp);
        }

        return check;
    }

    public static boolean check1=true;

    public static boolean convtoBCNF( String attributes, String fds[][], int no_of_fds,String Key)
    {
        boolean check=true;

        for(int i=0;i<no_of_fds;i++)
        {
            boolean flag4=true;
            for(int j=0; j<fds[i][0].length();j++)
            {
                String temp=""+fds[i][0].charAt(j);
                if(attributes.contains(temp))
                    continue;
                else
                {
                    flag4=false;
                    break;
                }
            }
            for(int j=0; j<fds[i][1].length();j++)
            {
                String temp=""+fds[i][1].charAt(j);
                if(attributes.contains(temp))
                    continue;
                else
                {
                    flag4=false;
                    break;
                }
            }

            //(fds[i][0].equals(Key)==false && attributes.contains(fds[i][0]) && attributes.contains(fds[i][1]) )
          if(fds[i][0].equals(Key)==false && flag4)
            {

                if(check1)
                {
                    System.out.println("Relation is in 3NF");
                    System.out.println("\n");
                    System.out.println("Relation in 3NF converted to BCNF: ");
                    check1=false;
                }
                check=false;
                String tab1="";

                for(int j=0;j<fds[i][0].length();j++)
                {
                    tab1+=fds[i][0].charAt(j)+" ";
                    BCNFvis[fds[i][0].charAt(j)-'a']=true;
                }

                for(int j=0;j<fds[i][1].length();j++)
                {
                    if(!BCNFvis[fds[i][1].charAt(j)-'a'])
                        tab1+=fds[i][1].charAt(j)+" ";

                    BCNFvis[fds[i][1].charAt(j)-'a']=true;
                }

                System.out.println("Table: "+tab1);

                String tab2="";

                String beta=sortString(fds[i][1]);
                String alpha=sortString(fds[i][0]);
                String diff="";

                for(int j=0;j<beta.length();j++)
                {
                    String X="";
                    X+=beta.charAt(j);

                    if(alpha.contains(X))
                        continue;
                    else
                        diff+=beta.charAt(j);
                }

                for(int j=0;j<attributes.length();j++)
                {
                    String X="";
                    X+=attributes.charAt(j);

                    if(diff.contains(X))
                        continue;
                    else
                    {
                        tab2+=attributes.charAt(j)+" ";
                        BCNFvis[attributes.charAt(j)-'a']=true;
                    }
                }

//               fds[i][0]="##";
                System.out.println("Table: "+tab2);


               tab1 = tab1.replace(" ","");
               tab1=sortString(tab1);
               String newKey=key(tab1,fds,no_of_fds);
               convtoBCNF(tab1,fds,no_of_fds,newKey);

               tab2 = tab2.replace(" ","");
               tab2=sortString(tab2);
               newKey=key(tab2,fds,no_of_fds);
               convtoBCNF(tab2,fds,no_of_fds,newKey);

            }
        }

        return check;
    }
    public static void check()
    {
        String Key=key(attributes,fds,no_of_fds);
        System.out.println("THE KEY IS : "+Key);
        try
        {
            Scanner sc = new Scanner(System.in);
            if(convto1NF(attributes,Key))
                if(convto2NF(attributes,Key,no_of_fds,fds))
                    if(convto3NF(attributes,fds,no_of_fds,Key))
                    {
                        if(convtoBCNF(attributes,fds,no_of_fds,Key))
                            System.out.println("Table already in BCNF form");
                        else
                        {
                            String fin="";
                            boolean check=false;
                            for(int i=0;i<attributes.length();i++)
                            {
                                if(BCNFvis[i]==false)
                                {
                                    fin+=attributes.charAt(i)+" ";
                                    BCNFvis[i]=true;
                                    check=true;
                                }
                            }
                            if(check==true)
                             System.out.println("Table: "+fin);
                        }
                    }
        }
        catch(Exception e1)
        {
            System.out.println(e1);
        }
    }

    public static JFrame f=new JFrame("DBS");
    public static void main(String args[])  throws IOException
    {
        Relation R = new Relation();
        f.setSize(1000,750);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(R.p);
        f.setLocationRelativeTo(null);
        f.setResizable(false);       
        f.setVisible(true);
    }
}
 
