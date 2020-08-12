
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author desai
 */
class MemoryExceededException extends Exception{
MemoryExceededException(List<Integer> keys,String s){
    String error="Error: Memory Limit Exceeded!!"+"\n"+"The entered key exceeds data structure capacity of bucket "+s+" !!";
home obj = new home();
System.out.println("Error: Memory Limit Exceeded!!"+"\n"+"The entered key exceeds data structure capacity of bucket "+s+" !!");
home.Bucket_overflow(keys,s);
}
}
public class home extends javax.swing.JFrame {

    /**
     * Creates new form home
     */
    int gd=0,bfr=0,num=0,flag=1,count=1,err=0;
    List<Integer> store=new ArrayList<>();
    String fop=null;
    public home() {
        initComponents();
        final_output.setVisible(false);
        operation_name.setVisible(false);
        operation_num.setVisible(false);
        input_text.setText("Enter value of GD: ");
        output_text.setText("");
    }
    public List<String> hash_fn(List<Integer> keys){
List<String> hash_val = new ArrayList<>();
for(Integer x: keys){
int mod = x%16;
String s = Integer.toBinaryString(mod);
while(s.length()!=4){
          s='0'+s;
        }
        hash_val.add(s);
}
return hash_val;
    }
    public static List<String> hash_fn_1(List<Integer> keys){
List<String> hash_val = new ArrayList<>();
for(Integer x: keys){
int mod = x%10;
String s = Integer.toBinaryString(mod);
while(s.length()!=4){
          s='0'+s;
        }
        hash_val.add(s);
}
return hash_val;
    }
    public String get_last_digits(String hash_val,int num){
        return (hash_val.substring(4-num,4));
    }
   
    public int[] get_num(List<String> hash_val,String s1,String s2,int i){
    int[] arr = new int[2];
    for(String s: hash_val){
    if(get_last_digits(s,i).equals(s1)){
    arr[0]++;
    }
    else if(get_last_digits(s,i).equals(s2)){
    arr[1]++;
    }
    }
    return arr;
    }
    public static void Bucket_overflow(List<Integer> keys,String s){
    List<String> hash_val = hash_fn_1(keys);
    String op="";
    int[][] array = new int[keys.size()][2];
for(int i=0; i<keys.size(); i++){
array[i][0] = keys.get(i);
array[i][1] = Integer.parseInt(hash_val.get(i));
}
op+="Overflow bucket '"+s+"' contains keys: ";
for(int i=0; i<keys.size(); i++){
String s1 = Integer.toString(array[i][1]);
while(s1.length()!=4){
          s1='0'+s1;
        }
if(s1.equals(s)){
    op+=array[i][0]+"  ";
}
}
op+="\n";
System.out.println(op);
    }
public List<String> Calculate(List<Integer> keys,String oper,int bfr,int gd) throws MemoryExceededException{
List<String> hash_val = hash_fn(keys);
int[] counti = new int[2];
counti[0]=0; counti[1]=0;
for(String s: hash_val){
if(get_last_digits(s,1).equals("0"))
counti[0]++;
else if(get_last_digits(s,1).equals("1")){
counti[1]++;
}
}
List<String> ans = new ArrayList<>();
ans.clear();

if(counti[0]>bfr){
int[] arr1 = get_num(hash_val,"00","10",2);
if(arr1[0]>bfr){
int[] arr2 = get_num(hash_val,"000","100",3);
if(arr2[0]>bfr){
int[] arr3 = get_num(hash_val,"0000","1000",4);
if(arr3[0]>bfr || arr3[1]>bfr){
if(arr3[0]>bfr)   throw new MemoryExceededException(keys,"0000");
if(arr3[1]>bfr) throw new MemoryExceededException(keys,"1000");
}
else{
if(arr3[0]<=bfr) { ans.add("0000");}
if( arr3[1]<=bfr) { ans.add("1000");}
}
}
else if(arr2[0]<=bfr) { ans.add("000"); }
if(arr2[1]>bfr){
int[] arr3 = get_num(hash_val,"0100","1100",4);
if(arr3[0]>bfr || arr3[1]>bfr){
if(arr3[0]>bfr) throw new MemoryExceededException(keys,"0100");
if(arr3[1]>bfr) throw new MemoryExceededException(keys,"1100");
}
else{
if( arr3[0]<=bfr) { ans.add("0100");}
if( arr3[1]<=bfr) { ans.add("1100");}
}
}
else if( arr2[1]<=bfr) {  ans.add("100"); }
}
else if( arr1[0]<=bfr) {  ans.add("00"); }
if(arr1[1]>bfr){
int[] arr2 = get_num(hash_val,"010","110",3);
if(arr2[0]>bfr){
int[] arr3 = get_num(hash_val,"0010","1010",4);
if(arr3[0]>bfr || arr3[1]>bfr){
if(arr3[0]>bfr) throw new MemoryExceededException(keys,"0010");
if(arr3[1]>bfr) throw new MemoryExceededException(keys,"1010");
}
else{
if(arr3[0]>0 && arr3[0]<=bfr) { ans.add("0010");}
if(arr3[1]>0 && arr3[1]<=bfr) { ans.add("1010");}
}
}
else if(arr2[0]>0 && arr2[0]<=bfr) {  ans.add("010"); }
if(arr2[1]>bfr){
int[] arr3 = get_num(hash_val,"0110","1110",4);
if(arr3[0]>2 || arr3[1]>bfr){
if(arr3[0]>bfr) throw new MemoryExceededException(keys,"0110");
if(arr3[1]>bfr) throw new MemoryExceededException(keys,"1110");
}
else{
if(arr3[0]>0 && arr3[0]<=bfr) { ans.add("0110");}
if(arr3[1]>0 && arr3[1]<=bfr) { ans.add("1110");}
}
}
else if(arr2[1]>0 && arr2[1]<=bfr) {  ans.add("110"); }
}
else if(arr1[0]>0 && arr1[0]<=bfr) {  ans.add("10"); }

}
else if(counti[0]>0 && counti[0]<=bfr){
ans.add("0");
}
if(counti[1]>bfr){
int[] arr1 = get_num(hash_val,"01","11",2);
if(arr1[0]>bfr){
int[] arr2 = get_num(hash_val,"001","101",3);
if(arr2[0]>bfr){
int[] arr3 = get_num(hash_val,"0001","1001",4);
if(arr3[0]>bfr || arr3[1]>bfr){
if(arr3[0]>bfr) throw new MemoryExceededException(keys,"0001");
if(arr3[1]>bfr) throw new MemoryExceededException(keys,"1001");
}
else{
if(arr3[0]>0 && arr3[0]<=bfr) { ans.add("0001");}
if(arr3[1]>0 && arr3[1]<=bfr) { ans.add("1001");}
}
}
else if(arr2[0]>0 && arr2[0]<=bfr) {  ans.add("001"); }
if(arr2[1]>bfr){
int[] arr3 = get_num(hash_val,"0101","1101",4);
if(arr3[0]>bfr || arr3[1]>bfr){
if(arr3[0]>bfr) throw new MemoryExceededException(keys,"0101");
if(arr3[1]>bfr) throw new MemoryExceededException(keys,"1101");
}
else{
if(arr3[0]>0 && arr3[0]<=bfr) { ans.add("0101");}
if(arr3[1]>0 && arr3[1]<=bfr) { ans.add("1101");}
}
}
else if(arr2[1]>0 && arr2[1]<=bfr) {  ans.add("101"); }
}
else if(arr1[0]>0 && arr1[0]<=bfr) {  ans.add("01"); }
if(arr1[1]>bfr){
int[] arr2 = get_num(hash_val,"011","111",3);
if(arr2[0]>bfr){
int[] arr3 = get_num(hash_val,"0011","1011",4);
if(arr3[0]>bfr || arr3[1]>bfr){
if(arr3[0]>bfr) throw new MemoryExceededException(keys,"0011");
if(arr3[1]>bfr) throw new MemoryExceededException(keys,"1011");
}
else{
if(arr3[0]>0 && arr3[0]<=bfr) { ans.add("0011");}
if(arr3[1]>0 && arr3[1]<=bfr) { ans.add("1011");}
}
}
else if(arr2[0]>0 && arr2[0]<=bfr) {  ans.add("011"); }
if(arr2[1]>bfr){
int[] arr3 = get_num(hash_val,"0111","1111",4);
if(arr3[0]>bfr || arr3[1]>bfr){
if(arr3[0]>bfr) throw new MemoryExceededException(keys,"0111");
if(arr3[1]>bfr) throw new MemoryExceededException(keys,"1111");
}
else{
if(arr3[0]>0 && arr3[0]<=bfr) { ans.add("0111");}
if(arr3[1]>0 && arr3[1]<=bfr) { ans.add("1111");}
}
}
else if(arr2[1]>0 && arr2[1]<=bfr) {  ans.add("111"); }
}
else if(arr1[1]>0 && arr1[1]<=bfr) {  ans.add("11"); }
}
else if(counti[1]>0 && counti[1]<=bfr){
ans.add("1");
}

int[][] array = new int[keys.size()][2];
for(int i=0; i<hash_val.size(); i++){
array[i][0] = keys.get(i);
array[i][1] = Integer.parseInt(hash_val.get(i));
}
int GD=0;
for(String s: ans){
if(s.length()>GD){
GD=s.length();
}
}
GD = max(GD,gd);
if(oper.equals("insert")||oper.equals("delete")){
print_result(ans,array,GD);
}
return ans;
}
public int max(int x,int y){
if(x>y)
return x;
else
return y;
}
public void print_result(List<String> ans,int[][] array,int GD){
    String op="";
    op+="GD : "+Integer.toString(GD);
for(String s: ans){
    op+="LD : "+s.length()+" ";
    op+="Bucket : "+s+" ";
for(int i=0; i<array.length; i++){
String s1 = Integer.toString(array[i][1]);
while(s1.length()!=4){
          s1='0'+s1;
        }
if(s.equals(get_last_digits(s1,s.length()))){
    op+=array[i][0]+"  ";
}
}
op+="\n";
}
final_output.setText(op);
}
public void search(List<Integer> keys,int x,int bfr,int gd){
int mod = x%10;
String op="";
String s = Integer.toBinaryString(mod);
while(s.length()!=4){
        s='0'+s;
        }
List<String> buckets = new ArrayList<>();
try{
buckets = Calculate(keys,"search",bfr,gd);
}
catch(MemoryExceededException mee) { return;}

for(String s1: buckets){
if(s1.equals(get_last_digits(s,s1.length()))){
    op+="Key "+x+" is present in bucket "+s1+"\n";
}
else
{
    op+="Key "+x+" is not present in bucket "+s1+"\n";
}
}
final_output.setText(op);
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        input_text = new javax.swing.JLabel();
        output_text = new javax.swing.JTextField();
        submit = new javax.swing.JButton();
        operation_num = new javax.swing.JLabel();
        final_output = new javax.swing.JLabel();
        operation_name = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        input_text.setText("jLabel1");

        output_text.setText("jTextField1");

        submit.setText("Submit");
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitActionPerformed(evt);
            }
        });

        operation_num.setText("jLabel1");

        final_output.setText("jLabel1");

        operation_name.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "insert", "search", "delete" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(142, 142, 142)
                        .addComponent(input_text, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(187, 187, 187)
                        .addComponent(output_text, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(121, 121, 121)
                        .addComponent(operation_name, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(475, 475, 475)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(submit, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(operation_num, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(final_output, javax.swing.GroupLayout.PREFERRED_SIZE, 1098, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(operation_num, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(operation_name, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(input_text, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(output_text, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)))
                .addGap(81, 81, 81)
                .addComponent(submit, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(final_output, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    private void submitActionPerformed(java.awt.event.ActionEvent evt) {                                      
        // TODO add your handling code here:
        String s=output_text.getText();
        if(flag==1)
        {
          try
          {
              gd=Integer.parseInt(s);
              flag++;
              input_text.setText("Enter value of BFR: ");
              output_text.setText("");
          }
          catch(Exception e)
          {
              JOptionPane.showMessageDialog(this, "Please enter value of GD in numbers");
          }
        }
        else if(flag==2)
        {
            try
          {
              bfr=Integer.parseInt(s);
              flag++;
              input_text.setText("Enter value of number of operations ");
              output_text.setText("");
          }
          catch(Exception e)
          {
              JOptionPane.showMessageDialog(this, "Please enter value of bfr in numbers");
          }
        }
        else if(flag==3)
        {
            try
          {
              num=Integer.parseInt(s);
              flag++;
              input_text.setText("Select Action and enter the value ");
              output_text.setText("");
              operation_name.setVisible(true);
              operation_num.setVisible(true);
              operation_num.setText(Integer.toString(count));
             
             
          }
          catch(Exception e)
          {
              JOptionPane.showMessageDialog(this, "Please enter value of operation number in numbers");
          }

        }
        else if(flag==4)
        {
            try
            {
                int key=Integer.parseInt(s);
                String oper=(String) operation_name.getSelectedItem();
                final_output.setVisible(true);
                if(oper.equals("insert")){
                if(store.contains(key)){
                    JOptionPane.showMessageDialog(this,"Error: Duplicate key error!\n\tThis key is already present in the database!");
                }
                else{
                    store.add(key);
                    try{  Calculate(store,"insert",bfr,gd); }
                    catch(MemoryExceededException mee){
                        JOptionPane.showMessageDialog(this, mee);
                    }
                }
            }
            else if(oper.equals("delete")){
                if(store.contains(key)){
                    store.remove(new Integer(key));
                    try{  Calculate(store,"delete",bfr,gd); }
                    catch(MemoryExceededException mee){
                    JOptionPane.showMessageDialog(this, mee);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(this,"Error: Missing key error!\n\tThe entered key does not exist in the database!");
                }  
            }
            else if(oper.equals("search")){
            if(store.contains(key)){
            search(store,key,bfr,gd);
            }
            else{
            JOptionPane.showMessageDialog(this,"Error: Missing key error!\n\tThe entered key does not exist in the database!");
            }
            }
                count+=1;
                operation_num.setText(Integer.toString(count));
                output_text.setText("");
                if(count>num)
                    flag++;
                 }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(this, "Please enter value in numbers.");
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "operations completed");
        }
       
       
    }                                      

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                    
    private javax.swing.JLabel final_output;
    private javax.swing.JLabel input_text;
    private javax.swing.JComboBox<String> operation_name;
    private javax.swing.JLabel operation_num;
    private javax.swing.JTextField output_text;
    private javax.swing.JButton submit;
    // End of variables declaration                  
}

