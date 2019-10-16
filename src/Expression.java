import java.util.*;
public class Expression {
	String[] op = new String[]{"+","-","×","÷"};
	Random rand = new Random();
	int defaultnumofexp = 10;
	
	//设置数值的范围
	public void setnumofexp(int a){
		this.defaultnumofexp= a;
	}
	
	public int getnumofexp(){
		return this.defaultnumofexp;
	}
	/*
	 * 该方法是实现生成表达式的功能，利用Fraction类的分子分母数组，
	 * 对应转化成分数形式，存到字符串s数组，
	 * 再随机生成运算符，对于括号的处理是把所有的括号可能的位置都进行了列举，
	 * 再和之前形成的分数与运算符一起拼接成表达式
	 */
	public String generateExp(int numRange){
		Fraction fra = new Fraction();
		fra.reset(numRange);
		boolean isbrackets = rand.nextBoolean();
		String [] s = new String[fra.numOfFraction];
		//随机生成的分子是大于或等于零的整数，分母的大于零的整数
		for(int i=0;i<fra.numOfFraction;i++)
			s[i]=fra.toString(fra.numerator[i], fra.denominator[i]);
		
		switch(fra.numOfFraction){
		
		//两个操作数，不需要加括号
		case 2: 
			  return s[0]+" "+op[rand.nextInt(4)]+" "+s[1];
			  
		//三个操作数，有三种情况   
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
              
       //四个操作数，有七种情况
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




