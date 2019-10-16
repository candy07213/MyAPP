import java.util.*;
public class Cnki {
	/*
	 * 实现查重思想：
	 * 判定两个表达式重复的关键在于计算这两个表达式时的过程是否一致，
	 * 在四则运算中也只会有加法和乘法在运算中交换左右操作数会出现重复的可能
	 * 在老师给出的例子中3+(2+1)和1+2+3这两个题目是重复的，
	 * 它们的后缀表达式分别是321++和12+3+，计算过程是一致的，都是先12相加，再和3相加，
	 * 为了直观的体现计算过程是否一致，我们引入了查重表达式，是后缀表达式的一种变形：运算在先，后面跟着这个运算符的操作数，
	 * 以上述为例3+(2+1)的查重表达式就为： +21+3
	 * 这个表达式的含义就是第一个运算的是加法，加法的操作数是2 和1
	 * 第二个运算是加法，加法的操作数是前一步计算的结果和3，
	 * 而1+2+3的查重表达式为：+12+3，
	 * 这样在做查重是只需判断查重表达式是否一致或者在查重表达式中第一个字符为‘+’ 或者‘*’的情况下
	 * 后续的两个操作数互换位置后是否一致即可，即上述例子可通过操作数互换位置使后一个表达式和前一个表达式的查重表达式完全一致，
	 * 所以可以得出尽管两个表达式的后缀表达式不同，但查重表达式一致，所以这两个表达式是重复的
	 * 所以在实现查重方面就是通过转换表达式的查重表达式来判断两个式子是否是重复的。
	 * 
	 */
	Stack<String> numstack = new Stack<String>();//该栈是用来存放在后缀表达式转换成查重表达式的过程中后缀表达式的数值。
	Set<String> set = new HashSet<String>();//set是一个存放查重表达式的容器，并且它不能添加重复的查重表达式。
	Calculation c = new Calculation();
	String [] postfixstr;
	String regex = "^\\d$";
	
	//给定一个表达式的后缀表达式，判断其是否和已经生成的表达式重复
	public boolean isRepeat(String postfixExp){
		String s = toCnkiExp(postfixExp);
		if(set.add(s))
			return false;
		else
			return true;
	
	}
	
	//将一个后缀表达式转换成标准的查重表达式
	public String toCnkiExp(String postfixExp){
		String formatpostfix = "";//用于存放查重表达式的字符串。
		String x="",y ="";//x，y均是从栈中弹出的两个操作数。
		int [] fraction1,fraction2;//fraction1数组是用来存放fraction1的分子和分母，fraction2数组是用来存放fraction2的分子分母
		int numerator,denominator;
		int flag = 0;//flag是来标记是否对一个第一操作符是'+'或者'x'的查重表达式的后两个操作数进行了交换。
		postfixstr = postfixExp.trim().split(" ");
		for(String s:postfixstr){
			//s是操作数
			if(s.length()>1 || s.matches(regex))
				numstack.push(s);
			else{ //s是操作符
				if((s.equals("+") || s.equals("×")) && (flag==0)){
					flag = 1;
					y = numstack.pop();
					x = numstack.pop();
					fraction1 = c.toFigure(x);
				    fraction2 = c.toFigure(y);
				    numerator = fraction1[0]*fraction2[1]-fraction1[1]*fraction2[0];
				    denominator = fraction1[1]*fraction2[1];
				    /*
				     * 将查重表达式中的第一个操作符为'+'或者'x'的后两个操作数按照数的大小从小到大排序。
				     * 通过这样来处理一种特殊情况：两个表达式的后缀表达式不同，
				     * 并且其查重表达式不是直接相同，而是仅仅只需要再交换一次操作数位置后就能判断出这两个是重复的，其余位置的元素均相同，
				     * 根据查重表达式的定义，当对第一个操作符为'+'和'x'之后的两个操作数在进入set容器前就排序，
				     * 在对每一个后缀表达式转换成查重表达式之前，通过让原本需要交换位置才能是同一个查重表达式的两个操作数先排序，
				     * 就可以使set容器辨认出是重复的表达式，从而不会放到set容器中。
				    */
				    if(numerator*denominator<0)
				    	formatpostfix += s + x + y;			    	
				    else
				    	formatpostfix += s + y + x;
					
				}
				else{	
				    formatpostfix += s;
				    if(!numstack.isEmpty())
				    	formatpostfix += numstack.pop();
				}
			}
				
		}			
		return formatpostfix;
						
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        Cnki cnki = new Cnki();
        String str1 =  "1"+" "+"2"+" "+"+"+" "+"3"+" "+"+";
        String str2 =  "3"+" "+"2"+" "+"1"+" "+"+"+" "+"+";
        String str3 =  "3"+" "+"2"+" "+"1"+" "+"+"+" "+"*"+" "+"5"+" "+"+";
        String str4 =  "1"+" "+"2"+" "+"+"+" "+"3"+" "+"*"+" "+"5"+" "+"+";
        System.out.println(cnki.isRepeat(str1));
        System.out.println(cnki.isRepeat(str2));
        
        
	}

}




