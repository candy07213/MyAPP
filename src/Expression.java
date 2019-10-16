import java.util.*;
public class Expression {
	String[] op = new String[]{"+","-","��","��"};
	Random rand = new Random();
	int defaultnumofexp = 10;
	
	//������ֵ�ķ�Χ
	public void setnumofexp(int a){
		this.defaultnumofexp= a;
	}
	
	public int getnumofexp(){
		return this.defaultnumofexp;
	}
	/*
	 * �÷�����ʵ�����ɱ��ʽ�Ĺ��ܣ�����Fraction��ķ��ӷ�ĸ���飬
	 * ��Ӧת���ɷ�����ʽ���浽�ַ���s���飬
	 * �����������������������ŵĴ����ǰ����е����ſ��ܵ�λ�ö��������о٣�
	 * �ٺ�֮ǰ�γɵķ����������һ��ƴ�ӳɱ��ʽ
	 */
	public String generateExp(int numRange){
		Fraction fra = new Fraction();
		fra.reset(numRange);
		boolean isbrackets = rand.nextBoolean();
		String [] s = new String[fra.numOfFraction];
		//������ɵķ����Ǵ��ڻ���������������ĸ�Ĵ����������
		for(int i=0;i<fra.numOfFraction;i++)
			s[i]=fra.toString(fra.numerator[i], fra.denominator[i]);
		
		switch(fra.numOfFraction){
		
		//����������������Ҫ������
		case 2: 
			  return s[0]+" "+op[rand.nextInt(4)]+" "+s[1];
			  
		//���������������������   
		case 3:
			 
			 if(isbrackets){
			  switch(new Random().nextInt(2)){
			  case 0:   //(a?b)?c
				  return "("+" "+s[0]+" "+op[rand.nextInt(4)]+" "+s[1]+" "+")"+" "+op[rand.nextInt(4)]+" "+s[2];
			  case 1:   //a?(b?c)
				  return s[0]+" "+op[rand.nextInt(4)]+" "+"("+" "+s[1]+" "+op[rand.nextInt(4)]+" "+s[2]+" "+")";
			  }
		
			}
			  // a?b?c
              return s[0]+" "+op[rand.nextInt(4)]+" "+s[1]+" "+op[rand.nextInt(4)]+" "+s[2];
              
       //�ĸ������������������
		case 4:
			
			if(isbrackets){
				switch(new Random().nextInt(10)){
				case 0:  //(a?b)?c?d
					return "("+" "+s[0]+" "+op[rand.nextInt(4)]+" "+s[1]+" "+")"+" "+op[rand.nextInt(4)]+" "+s[2] 
							+" "+op[rand.nextInt(4)]+" "+s[3];
					
				case 1:   //(a?b?c)?d
					return "(" +" "+s[0]+" "+op[rand.nextInt(4)]+" "+s[1]+" "+op[rand.nextInt(4)]+" "+s[2]+" "+")" 
							+" "+op[rand.nextInt(4)]+" "+s[3];
				
				case 2:   //a?(b?c)?d
					return s[0]+" "+op[rand.nextInt(4)]+" "+"("+" "+s[1]+" "+op[rand.nextInt(4)]+" "+s[2]+" "+")" 
					        +" "+op[rand.nextInt(4)]+" "+s[3];
					
				case 3:   //a?(b?c?d)
					return s[0]+" "+op[rand.nextInt(4)]+" "+"("+" "+s[1]+" "+op[rand.nextInt(4)]+" "+s[2] 
					+" "+op[rand.nextInt(4)]+" "+s[3]+" "+ ")" ;
					
				case 4:    //a?b?(c?d)
					return s[0]+" "+op[rand.nextInt(4)]+" "+s[1]+" "+op[rand.nextInt(4)]+" "+"("+" "+s[2] 
							+" "+op[rand.nextInt(4)]+" "+s[3]+" "+")" ;
					
				case 5:    //(a?b)?(c?d)
					return "("+" "+s[0]+" "+op[rand.nextInt(4)]+" "+s[1]+" "+")"+" "+op[rand.nextInt(4)]+" "+"("
				            +" "+s[2]+" "+op[rand.nextInt(4)]+" "+s[3]+" "+")" ;
					
				case 6:  //((a?b)?c)?d
					return "("+" "+"("+" "+s[0]+" "+op[rand.nextInt(4)]+" "+s[1]+" "+")"+" "+op[rand.nextInt(4)]
							+" "+s[2]+" "+")"+" "+op[rand.nextInt(4)]+" "+s[3];
					
				case 7:  //(a?(b?c))?d
					return "("+" "+s[0]+" "+op[rand.nextInt(4)]+" "+"("+" "+s[1]+" "+op[rand.nextInt(4)]+" "+s[2]
							+" "+")"+" "+")"+" "+op[rand.nextInt(4)]+" "+s[3];
					
				case 8:  //a?((b?c)?d)
					return s[0]+" "+op[rand.nextInt(4)]+" "+"("+" "+"("+" "+s[1]+" "+op[rand.nextInt(4)]+" "+s[2]
							+" "+")"+" "+op[rand.nextInt(4)]+" "+s[3]+" "+")";
					
				case 9:  //a?(b?(c?d))
					return s[0]+" "+op[rand.nextInt(4)]+" "+"("+" "+s[1]+" "+op[rand.nextInt(4)]+" "+"("+" "+s[2]
							+" "+op[rand.nextInt(4)]+" "+s[3]+" "+")"+" "+")";
								
				}
			}
			// a?b?c?d
			return s[0]+" "+op[rand.nextInt(4)]+" "+s[1]+" "+op[rand.nextInt(4)]+" "+s[2]+" "+op[rand.nextInt(4)]+" "+s[3]; 
	
		}
		return generateExp(numRange);
	
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Expression e = new Expression();
		//System.out.println(e.generateExp());
		 String[] s = {"1","2","3","4"};
	     e.setnumofexp(Integer.valueOf(s[1]));
		 System.out.println(e.defaultnumofexp);
	}

}




