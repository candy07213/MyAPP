import java.util.*;
public class Fraction {
	Random random=new Random();
	int numOfFraction = random.nextInt(3)+2; //随机生成的分数的个数
	int defaultRange = 10;
	int[] numerator = new int[numOfFraction];//分子
	int[] denominator = new int[numOfFraction];//分母

	
	//设置数值的范围
	public void setRange(int a){
		this.defaultRange = a;
	}
	
	public int getRange(){
		return this.defaultRange;
	}
	
	
	public Fraction(){
		for(int i=0;i<numOfFraction;i++){
			int a,b;
			a = random.nextInt(defaultRange);
			b = random.nextInt(defaultRange-1)+1;
			int c = maxcomdivisor(a,b);
			numerator[i] = a/c;
			denominator[i] = b/c;
		}
	}
	
	/*
	 * 
	 * 重新设置自然数，真分数和真分数分母数值范围，
	 * 其中包括调用最大公约数来化简分数并修改之前赋初值的成员变量numerator和denominator数组
	 */
	public void reset(int numRange){
		for(int i=0;i<numOfFraction;i++){
			int a,b;
			a = random.nextInt(numRange);
			b = random.nextInt(numRange-1)+1;
			int c = maxcomdivisor(a,b);
			numerator[i] = a/c;
			denominator[i] = b/c;
		}
	}
	//numerator1：分数1的分子，denomintor1：分数1的分母，numerator2：分数2的分子，denomintor2：分数2的分母。
	//加
	public String add(int numerator1,int denominator1,int numerator2,int denominator2 ){
		int numerator = numerator1*denominator2+denominator1*numerator2;
	    int denominator = denominator1*denominator2;
	    return toString(numerator,denominator);
	}
	//减
	public String sub(int numerator1,int denominator1,int numerator2,int denominator2 ){
		int numerator = numerator1*denominator2-denominator1*numerator2;
	    int denominator = denominator1*denominator2;
	   return toString(numerator,denominator);
	}
	//乘
	public String mul(int numerator1,int denominator1,int numerator2,int denominator2 ){
		int numerator = numerator1*numerator2;
	    int denominator = denominator1*denominator2;
	   return toString(numerator,denominator);
	}
	//除
	//当除数的分子为零时，结果的分母就为零，此情况在函数toString有相应的处理
	public String dev(int numerator1,int denominator1,int numerator2,int denominator2 ){
		int numerator = numerator1*denominator2;
	    int denominator = denominator1*numerator2;
	   return toString(numerator,denominator);
	}
	

	//求m和n的最大公约数
	 public static int maxcomdivisor(int m, int n) {
		 if(n==0) return -1;
		 else{
		  while (true) {
		   if ((m = m % n) == 0)	   
		    return n;	  
		   
		   if ((n = n % m) == 0) 
		    return m;		   
		  }
		 }
	 }
	 
	 //将单独的一个分子和分母转换成一个字符串表示的分数
	 //其中分母不能为零，分子或分母不能为负
	 /*
	  * 考虑到特殊情况，如果分子为负数，并且绝对值大于分母，那么根据下面的算法，就不会转换成想得到的带分数形式，
	  * 如果传入的denominator为0，则根据下面的算法，就会出现除数为0的情况，
	  * 所以在方法体的一开始就对分子分母做了判断，保证结果的正确性，
	  * 同时当计算表达式结果的过程中调用此方法时就能根据返回的N判断到出现了负数和除数为0的情况，并重新生成一个表达式计算
	  */
	 
	 public String toString(int numerator,int denominator) {
		 
		 if(denominator==0 || numerator<0 || denominator<0)
			 return "N";
		 
		 int c = maxcomdivisor(numerator,denominator);
		 numerator = numerator/c;
		 denominator = denominator/c;
		 /*
		  * 如果分母为1，就将分子转换成字符串返回，否则判断分子和分母之间的大小，
		  * 当分子大于分母时，将假分数转换成真分数字符串返回，当分子小于分母时，直接以真分数的形式返回
		  */
		 
		 if(denominator==1) {
	         return String.valueOf(numerator);
	     }else
	         if(numerator>denominator) {
	             return String.format("%d'%d/%d", numerator/denominator,numerator%denominator,denominator);
	         }else {
	             return String.format("%d/%d", numerator,denominator);
	         }
	    }
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	  Fraction f = new Fraction();/*
	  for(int i=0;i<f.numerator.length;i++){
		  System.out.println(f.numerator[i]);
		  System.out.println(f.denominator[i]);
		  }*/
	  //System.out.println(f.dev( 4,2,0 ,3));
	  f.setRange(20);
	  System.out.println(f.defaultRange);
	  
	}
}
