
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyappTest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
	   Scanner sc = new Scanner(System.in);
       System.out.println("-----------------------------------------------------"); 
       System.out.println("Сѧ�������� -���ܣ�                                                                                                                           "); 
       System.out.println("Myapp.exe -n [������Ŀ����]                               "); 
       System.out.println("Myapp.exe -r [��Ŀ��ֵ��Χ]                               ");
       System.out.println("Myapp.exe -e <exercisefile>.txt -a <answerfile>.txt  "); 
       System.out.println("-----------------------------------------------------"); 
       String [] temp;
      // while(true){
    	   System.out.println("���������");
    	   String s = sc.nextLine();
    	   String s2 = sc.nextLine();
    	   String [] in = s.trim().split(" ");
    	   String [] in2 = s2.trim().split(" ");
    	   if(in.length==2){
    		   BufferedWriter bw1 = new BufferedWriter(new FileWriter("G:/java/Myapp/Exercises.txt"));
        	   BufferedWriter bw2 = new BufferedWriter(new FileWriter("G:/java/Myapp/Answers.txt"));
        	   Expression e = new Expression();
               Fraction f = new Fraction();
               Calculation c = new Calculation();
               ArrayList<String> list = new ArrayList<>();
    		   int resetRange = 0;
    		   if(in[0].equals("-n") && in2[0].equals("-r")){
    			   e.setnumofexp(Integer.valueOf(in[1]));
    		       resetRange = Integer.valueOf(in2[1]);
    		   }
    		   
    		   else if(in[0].equals("-n") ){
    			   e.setnumofexp(Integer.valueOf(in[1]));
    			   resetRange = f.defaultRange;
    			   
    		   }
    		   else if(in[0].equals("-r") ){
    			   resetRange = Integer.valueOf(in[1]);
    			   
    		   }
    		   else{
    		       System.out.println("ָ���������,���������룡");
    		       //continue;
    		   }
    		   list = c.getExp_Ans(e,resetRange);
    		   for(int i=0;i<list.size();i++){
    			    temp = list.get(i).split(":");
    			    i++;
    			    bw1.write(i+"��"+temp[0]);
					bw1.newLine();
					bw1.flush();
					bw2.write(i+"��"+temp[1]);
					bw2.newLine();
					bw2.flush();
					i--;
    			   
    		   } 
    		   bw1.close();
    		   bw2.close();
    		   
    	  }		
    	   /*
    	     * ������Ҫ�Լ�дһ����Ŀ�ļ�����Ŀ�𰸣�Ȼ�����ȥ������ļ���
    	     * Ȼ��Ӧ����Ҫ�����Լ��ٸ��ݶ�������Ŀ�߼�����ж�֮ǰ�Լ�д����Ŀ�𰸺���ȷ�𰸵ıȽ����
    	     * Ȼ��ѱȽϽ��д��Grade�ļ���ȥ
    	     */	   
    	   else if(in.length==4 && in[0].equals("-e") && in[2].equals("-a")){
  		    File fExercises = new File(in[1]);
  		    File fAnswers = new File(in[3]);
  		      if( fExercises.exists() && fAnswers.exists()) {
  			      BufferedReader br1=new BufferedReader(new FileReader(fExercises));
                  BufferedReader br2=new BufferedReader(new FileReader(fAnswers));
                  Calculation c = new Calculation();
  		            //themeΪ�������Ŀ
                  String theme ;
                  int right = 0;
                  int wrong = 0;
                  int l = 0;
                  int rcount[] = new int[10];
                  int wcount[] = new int[10];
                  String regex = "\\=";
                  Pattern r = Pattern.compile(regex);
                  while((theme = br1.readLine()) != null ){
              	    String str[] = theme.split("��");
              	    theme = str[1];
              	    Matcher m = r.matcher(theme);
              	    String []arr = m.replaceAll(" ").trim().split(" ");
                 	//��ÿ����Ŀ���м���, corranswerΪ��ȷ��
              	    String corranswer = c.getresult(c.getpostfixExp(arr));	
              	   //testanswerΪ������ԵĴ�
              	    String[] str2=br2.readLine().split("��");
                    String testanswer=str2[1];         
                          if(corranswer.equals(testanswer)){ //��֤�𰸣�ͳ����ȷ�ʹ���ĸ��� 
                        	  rcount[l] = Integer.valueOf(str2[0]);
                              right++;
                          }
                          else {
                        	  wcount[l] = Integer.valueOf(str2[0]);
                              wrong++; 
                          }
                      l++;
                   }          
 
                   br2.close();
                   br1.close();
                   
                 File fi=new File("G:/java/Myapp/Grade.txt");
                 int count1 = 0;
                 int count2 = 0;
                                                               
                 FileWriter fw = new FileWriter(fi, false);
                 PrintWriter pw = new PrintWriter(fw);
                 pw.println(" ");
                 pw.print("Correct:"+right+"(");
                 for (int i = 0; i < rcount.length; i++) {
                     if (rcount[i] != 0 && (count1 == right-1)){ 
                    	pw.print(rcount[i]);
                     }
                     else if(rcount[i] != 0){
                    	pw.print(rcount[i] + ",");
                        count1++;
                     }
                    	 
                 } 
                 pw.println(")");
                 pw.print("Wrong:"+wrong+"(");
                 for (int j = 0; j < wcount.length; j++) {
                     if (wcount[j] != 0 && (count2 == wrong-1)){ 
                       pw.print(wcount[j]);
                     }
                     else if(wcount[j] != 0){
                       pw.print(wcount[j] + ",");
                       count2++;
                       
                     }
                 }
                 pw.print(")");
                 pw.flush();
                 try {
              	     fw.flush();
                     pw.close();
                     fw.close();
                 } catch (IOException e1) {
                     e1.printStackTrace();
                 }   
  		    }else 
  		         throw new FileNotFoundException("�ļ�������");
    	  }
    	   
    	  else {
              System.out.println("���������ģʽ�������������룡");
             // break;
              
    	  }
    	     	   
       //} 
      sc.close();		
	}

}


