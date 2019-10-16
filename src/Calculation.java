import java.util.*;

public class Calculation {
	Fraction f = new Fraction();
	Stack<String> stack = new Stack<String>();//该栈是在获得后缀表达式的过程中装运算符。
	Stack<String> stack2 = new Stack<String>();//stack2是在计算表达式答案的过程中装中间计算数值和结果。
	String reg = "^\\d$";//该正则表达式是在利用逆波兰表达式算法时判断读入字符串是否是单个数字字符。
	
	//获得题目表达式和答案
	public ArrayList<String> getExp_Ans(Expression e,int numRange){
		ArrayList<String> alist = new ArrayList<>();//用来装符合规范的题目和对应的答案
		String [] exp; //对调用Expression类生成表达式的方法生成的表达式，根据空格进行对表达式的拆分。
		String postfixExp;//获得的后缀表达式字符串。
		String answer;//表达式的答案。
		Cnki cnki = new Cnki();//用来判断表达式是否重复的类
		int count = 0;
		/*
		 * while循环是对生成的表达式进行过滤来保证得到的表达式规范，
		 * 其中主要是对计算过程中出现负数和除数为0以及重复的表达式进行剔除，
		 * 设置了count变量，只有在表达式规范时count进行加一操作，若中途出现负数以及除数为0和重复时，count保持不变，且退出当前循环，进入下一次循环
		 * 直到count增加到要求生成的表达式数目，就结束循环。
		 */
		while(count < e.getnumofexp()){//调用Expression类中的获得默认的表达式数或者用户要求的表达式数
			exp  = e.generateExp(numRange).split(" ");
			postfixExp = getpostfixExp(exp);
			answer = getresult(postfixExp);
			/*
			 * 此处是在计算过程（getresult方法）中当出现负数和除数为0的情况时，就返回N，
			 * cnki是判断表达式是否重复，当这两种情况有任何一种情况出现时就跳出当前循环，不再执行其他操作。
			 */
			if(answer.equals("N") || cnki.isRepeat(postfixExp.trim())){
				continue;
			}
			else{
				String exercises = "";
				count++;
				for(int i=0;i<exp.length;i++){
					if(i < exp.length-1){
						if(exp[i].equals("(") || exp[i+1].equals(")"))
							exercises += exp[i];
						else
							exercises += exp[i] + " ";
					}
					else 
						exercises += exp[i];
					
				}
				exercises += " " + "=";
				alist.add(exercises +":"+ answer);				
			}							
		}
		return alist;
		
	}
	
	
	//利用逆波兰表达式算法获得后缀表达式
	public String getpostfixExp(String[] exp){
		String postfixExp = "";
		for(String s:exp){
			//s是数值,加入后缀表达式
			if(s.length()>1 || s.matches(reg))
				postfixExp += s + " ";
			//s是 '(',入栈
			else if(s.equals("("))
				stack.push(s);
			//s是 ')',  依次将栈中元素出栈并加入到后缀表达式, 直到遇到 '(' 并将其从栈中删除
			else if(s.equals(")")){
				String popstr;
				do{
					popstr = stack.pop();
					if(!popstr.equals("("))
						postfixExp += popstr + " ";
						
				}while(!popstr.equals("("));
			}else{ //s是运算符op
	            //栈为空  或者  栈顶元是 '(' ,op入栈				
				if(stack.isEmpty() || stack.get(stack.size()-1).equals("("))
					stack.push(s);
				else{
					String top = stack.get(stack.size()-1);
					//高于栈顶运算符优先级, op入栈
					if(map(s) > map(top)) 
						stack.push(s);
					/*
					 * 依次将比op优先级高的和相等的运算符出栈加入到后缀表达式，
					 * 直到遇到比op优先级低的运算符或左括号（低优先级运算符或左括号不出栈）或栈空为止
					 * op入栈
					 * */
					else{    
						String popstr = "";
				        do{
				        	
				        	popstr = stack.pop();
				        	if((map(popstr) >= map(s)) && (map(popstr)!=0))
				        		postfixExp += popstr + " ";
				        	
				        }while((!stack.isEmpty()) && (map(popstr) >= map(s)));
				        //由于先出栈操作再判断，所以如果popstr是比op优先级低的运算符或左括号则应让它再进栈
				        if(map(popstr) < map(s))
				        	stack.push(popstr);
				        stack.push(s);				        
					}
				}
			}					
		}
		//扫描完毕后栈中所有剩下的运算符出栈加入到后缀表达式
		while(!stack.isEmpty())
			postfixExp += stack.pop() + " ";
		
		return postfixExp;
		
	}
	
	
	//计算后缀表达式的值
	public String getresult(String postfixExp){
		String x="",y="",result="";
		String[] postfix = postfixExp.trim().split(" ");
        int [] figure1,figure2;//这两个数组是将分数字符串根据‘和/进行拆分之后再进一步转换得到独立的分子分母，数组中下标为0的元素是分子，下标为1的为分母
        
		for(String s:postfix){
			//s是操作数，入栈
			if(s.length()>1 || s.matches(reg))
				stack2.push(s);
			/*
			 * s是操作符op
			 * 连续从栈中退出两个操作数Y和X（先出栈的为Y）
			 * 将 X<op>Y的结果入栈
			 */
			else if(s.equals("+") || s.equals("-") || s.equals("×") || s.equals("÷")){
				
				y = stack2.pop();
				x = stack2.pop();
				
			    figure1 = toFigure(x);
			    figure2 = toFigure(y);
			    /*
			     * 调用了Fraction类的加减乘除四个方法，来计算表达式的结果，其中如果减法和除法的返回值为N就说明出现了负数或除数为0的情况。
			     */
			    switch(s){
			    case "+":
			    	result = f.add(figure1[0], figure1[1], figure2[0], figure2[1]);
			    	break;
			    case "-":
			    	result = f.sub(figure1[0], figure1[1], figure2[0], figure2[1]);
			    	if(result.equals("N"))
			    		return "N";
			    	else break;
			    case "×":
			    	result = f.mul(figure1[0], figure1[1], figure2[0], figure2[1]);
			    	break;
			    case "÷":
			        result = f.dev(figure1[0], figure1[1], figure2[0], figure2[1]);
			        if(result.equals("N"))
			        	return "N";
			        else break;
			    default:
			    	System.out.println("出现未知错误！");
			    	break;			    	
			    }
			    stack2.push(result);			    
			}
		
		}		
		return stack2.pop();		
		
	}
	
	/*
	 * map方法是用于getpostfixExp方法获得后缀表达式的过程中将左右括号和四则运算符，
	 * 根据它们的优先级分别映射到0,1,2
	 */
	  
	public int map(String op){
		if(op.equals("(") || op.equals(")"))
		    return 0;
		else if(op.equals("+") || op.equals("-"))
			return 1;
		else 
			return 2;
	}
	
	/*
	 *返回一个分别存放将分数字符串根据‘和/进行拆分之后再进一步转换得到的独立分子分母数组
	 */
	public int[] toFigure(String fraction){
		int[] figure = new int[2];
    	
    	int indexOfpoint=fraction.indexOf("'");
    	int indexOfslash=fraction.indexOf("/");
    	if(indexOfpoint!=-1) {
    		int integer=Integer.valueOf(fraction.substring(0, indexOfpoint));
    		figure[1]=Integer.valueOf(fraction.substring(indexOfslash+1));
    		figure[0]=integer*figure[1]+Integer.valueOf(fraction.substring(indexOfpoint+1, indexOfslash));
    	}else if(indexOfslash!=-1) {
    		figure[1]=Integer.valueOf(fraction.substring(indexOfslash+1));
    		figure[0]=Integer.valueOf(fraction.substring(0,indexOfslash));
    	}else {
    		figure[0]=Integer.valueOf(fraction);
    	    figure[1]=1;
    	}
  	    return figure;
		
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Calculation c = new Calculation();
		//int [] ans;
		//ans = c.toFigure("-20'2/3");
		//System.out.println(ans[0]);
		//System.out.println(ans[1]);
		System.out.println(c.getresult("3 1 3 - +"));
	}

}



