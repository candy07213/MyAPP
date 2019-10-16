import java.util.*;

public class Calculation {
	Fraction f = new Fraction();
	Stack<String> stack = new Stack<String>();//��ջ���ڻ�ú�׺���ʽ�Ĺ�����װ�������
	Stack<String> stack2 = new Stack<String>();//stack2���ڼ�����ʽ�𰸵Ĺ�����װ�м������ֵ�ͽ����
	String reg = "^\\d$";//��������ʽ���������沨�����ʽ�㷨ʱ�ж϶����ַ����Ƿ��ǵ��������ַ���
	
	//�����Ŀ���ʽ�ʹ�
	public ArrayList<String> getExp_Ans(Expression e,int numRange){
		ArrayList<String> alist = new ArrayList<>();//����װ���Ϲ淶����Ŀ�Ͷ�Ӧ�Ĵ�
		String [] exp; //�Ե���Expression�����ɱ��ʽ�ķ������ɵı��ʽ�����ݿո���жԱ��ʽ�Ĳ�֡�
		String postfixExp;//��õĺ�׺���ʽ�ַ�����
		String answer;//���ʽ�Ĵ𰸡�
		Cnki cnki = new Cnki();//�����жϱ��ʽ�Ƿ��ظ�����
		int count = 0;
		/*
		 * whileѭ���Ƕ����ɵı��ʽ���й�������֤�õ��ı��ʽ�淶��
		 * ������Ҫ�ǶԼ�������г��ָ����ͳ���Ϊ0�Լ��ظ��ı��ʽ�����޳���
		 * ������count������ֻ���ڱ��ʽ�淶ʱcount���м�һ����������;���ָ����Լ�����Ϊ0���ظ�ʱ��count���ֲ��䣬���˳���ǰѭ����������һ��ѭ��
		 * ֱ��count���ӵ�Ҫ�����ɵı��ʽ��Ŀ���ͽ���ѭ����
		 */
		while(count < e.getnumofexp()){//����Expression���еĻ��Ĭ�ϵı��ʽ�������û�Ҫ��ı��ʽ��
			exp  = e.generateExp(numRange).split(" ");
			postfixExp = getpostfixExp(exp);
			answer = getresult(postfixExp);
			/*
			 * �˴����ڼ�����̣�getresult�������е����ָ����ͳ���Ϊ0�����ʱ���ͷ���N��
			 * cnki���жϱ��ʽ�Ƿ��ظ�����������������κ�һ���������ʱ��������ǰѭ��������ִ������������
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
	
	
	//�����沨�����ʽ�㷨��ú�׺���ʽ
	public String getpostfixExp(String[] exp){
		String postfixExp = "";
		for(String s:exp){
			//s����ֵ,�����׺���ʽ
			if(s.length()>1 || s.matches(reg))
				postfixExp += s + " ";
			//s�� '(',��ջ
			else if(s.equals("("))
				stack.push(s);
			//s�� ')',  ���ν�ջ��Ԫ�س�ջ�����뵽��׺���ʽ, ֱ������ '(' �������ջ��ɾ��
			else if(s.equals(")")){
				String popstr;
				do{
					popstr = stack.pop();
					if(!popstr.equals("("))
						postfixExp += popstr + " ";
						
				}while(!popstr.equals("("));
			}else{ //s�������op
	            //ջΪ��  ����  ջ��Ԫ�� '(' ,op��ջ				
				if(stack.isEmpty() || stack.get(stack.size()-1).equals("("))
					stack.push(s);
				else{
					String top = stack.get(stack.size()-1);
					//����ջ����������ȼ�, op��ջ
					if(map(s) > map(top)) 
						stack.push(s);
					/*
					 * ���ν���op���ȼ��ߵĺ���ȵ��������ջ���뵽��׺���ʽ��
					 * ֱ��������op���ȼ��͵�������������ţ������ȼ�������������Ų���ջ����ջ��Ϊֹ
					 * op��ջ
					 * */
					else{    
						String popstr = "";
				        do{
				        	
				        	popstr = stack.pop();
				        	if((map(popstr) >= map(s)) && (map(popstr)!=0))
				        		postfixExp += popstr + " ";
				        	
				        }while((!stack.isEmpty()) && (map(popstr) >= map(s)));
				        //�����ȳ�ջ�������жϣ��������popstr�Ǳ�op���ȼ��͵����������������Ӧ�����ٽ�ջ
				        if(map(popstr) < map(s))
				        	stack.push(popstr);
				        stack.push(s);				        
					}
				}
			}					
		}
		//ɨ����Ϻ�ջ������ʣ�µ��������ջ���뵽��׺���ʽ
		while(!stack.isEmpty())
			postfixExp += stack.pop() + " ";
		
		return postfixExp;
		
	}
	
	
	//�����׺���ʽ��ֵ
	public String getresult(String postfixExp){
		String x="",y="",result="";
		String[] postfix = postfixExp.trim().split(" ");
        int [] figure1,figure2;//�����������ǽ������ַ������ݡ���/���в��֮���ٽ�һ��ת���õ������ķ��ӷ�ĸ���������±�Ϊ0��Ԫ���Ƿ��ӣ��±�Ϊ1��Ϊ��ĸ
        
		for(String s:postfix){
			//s�ǲ���������ջ
			if(s.length()>1 || s.matches(reg))
				stack2.push(s);
			/*
			 * s�ǲ�����op
			 * ������ջ���˳�����������Y��X���ȳ�ջ��ΪY��
			 * �� X<op>Y�Ľ����ջ
			 */
			else if(s.equals("+") || s.equals("-") || s.equals("��") || s.equals("��")){
				
				y = stack2.pop();
				x = stack2.pop();
				
			    figure1 = toFigure(x);
			    figure2 = toFigure(y);
			    /*
			     * ������Fraction��ļӼ��˳��ĸ���������������ʽ�Ľ����������������ͳ����ķ���ֵΪN��˵�������˸��������Ϊ0�������
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
			    case "��":
			    	result = f.mul(figure1[0], figure1[1], figure2[0], figure2[1]);
			    	break;
			    case "��":
			        result = f.dev(figure1[0], figure1[1], figure2[0], figure2[1]);
			        if(result.equals("N"))
			        	return "N";
			        else break;
			    default:
			    	System.out.println("����δ֪����");
			    	break;			    	
			    }
			    stack2.push(result);			    
			}
		
		}		
		return stack2.pop();		
		
	}
	
	/*
	 * map����������getpostfixExp������ú�׺���ʽ�Ĺ����н��������ź������������
	 * �������ǵ����ȼ��ֱ�ӳ�䵽0,1,2
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
	 *����һ���ֱ��Ž������ַ������ݡ���/���в��֮���ٽ�һ��ת���õ��Ķ������ӷ�ĸ����
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



