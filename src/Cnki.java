import java.util.*;
public class Cnki {
	/*
	 * ʵ�ֲ���˼�룺
	 * �ж��������ʽ�ظ��Ĺؼ����ڼ������������ʽʱ�Ĺ����Ƿ�һ�£�
	 * ������������Ҳֻ���мӷ��ͳ˷��������н������Ҳ�����������ظ��Ŀ���
	 * ����ʦ������������3+(2+1)��1+2+3��������Ŀ���ظ��ģ�
	 * ���ǵĺ�׺���ʽ�ֱ���321++��12+3+�����������һ�µģ�������12��ӣ��ٺ�3��ӣ�
	 * Ϊ��ֱ�۵����ּ�������Ƿ�һ�£����������˲��ر��ʽ���Ǻ�׺���ʽ��һ�ֱ��Σ��������ȣ�����������������Ĳ�������
	 * ������Ϊ��3+(2+1)�Ĳ��ر��ʽ��Ϊ�� +21+3
	 * ������ʽ�ĺ�����ǵ�һ��������Ǽӷ����ӷ��Ĳ�������2 ��1
	 * �ڶ��������Ǽӷ����ӷ��Ĳ�������ǰһ������Ľ����3��
	 * ��1+2+3�Ĳ��ر��ʽΪ��+12+3��
	 * ��������������ֻ���жϲ��ر��ʽ�Ƿ�һ�»����ڲ��ر��ʽ�е�һ���ַ�Ϊ��+�� ���ߡ�*���������
	 * ��������������������λ�ú��Ƿ�һ�¼��ɣ����������ӿ�ͨ������������λ��ʹ��һ�����ʽ��ǰһ�����ʽ�Ĳ��ر��ʽ��ȫһ�£�
	 * ���Կ��Եó������������ʽ�ĺ�׺���ʽ��ͬ�������ر��ʽһ�£��������������ʽ���ظ���
	 * ������ʵ�ֲ��ط������ͨ��ת�����ʽ�Ĳ��ر��ʽ���ж�����ʽ���Ƿ����ظ��ġ�
	 * 
	 */
	Stack<String> numstack = new Stack<String>();//��ջ����������ں�׺���ʽת���ɲ��ر��ʽ�Ĺ����к�׺���ʽ����ֵ��
	Set<String> set = new HashSet<String>();//set��һ����Ų��ر��ʽ����������������������ظ��Ĳ��ر��ʽ��
	Calculation c = new Calculation();
	String [] postfixstr;
	String regex = "^\\d$";
	
	//����һ�����ʽ�ĺ�׺���ʽ���ж����Ƿ���Ѿ����ɵı��ʽ�ظ�
	public boolean isRepeat(String postfixExp){
		String s = toCnkiExp(postfixExp);
		if(set.add(s))
			return false;
		else
			return true;
	
	}
	
	//��һ����׺���ʽת���ɱ�׼�Ĳ��ر��ʽ
	public String toCnkiExp(String postfixExp){
		String formatpostfix = "";//���ڴ�Ų��ر��ʽ���ַ�����
		String x="",y ="";//x��y���Ǵ�ջ�е�����������������
		int [] fraction1,fraction2;//fraction1�������������fraction1�ķ��Ӻͷ�ĸ��fraction2�������������fraction2�ķ��ӷ�ĸ
		int numerator,denominator;
		int flag = 0;//flag��������Ƿ��һ����һ��������'+'����'x'�Ĳ��ر��ʽ�ĺ����������������˽�����
		postfixstr = postfixExp.trim().split(" ");
		for(String s:postfixstr){
			//s�ǲ�����
			if(s.length()>1 || s.matches(regex))
				numstack.push(s);
			else{ //s�ǲ�����
				if((s.equals("+") || s.equals("��")) && (flag==0)){
					flag = 1;
					y = numstack.pop();
					x = numstack.pop();
					fraction1 = c.toFigure(x);
				    fraction2 = c.toFigure(y);
				    numerator = fraction1[0]*fraction2[1]-fraction1[1]*fraction2[0];
				    denominator = fraction1[1]*fraction2[1];
				    /*
				     * �����ر��ʽ�еĵ�һ��������Ϊ'+'����'x'�ĺ������������������Ĵ�С��С��������
				     * ͨ������������һ������������������ʽ�ĺ�׺���ʽ��ͬ��
				     * ��������ر��ʽ����ֱ����ͬ�����ǽ���ֻ��Ҫ�ٽ���һ�β�����λ�ú�����жϳ����������ظ��ģ�����λ�õ�Ԫ�ؾ���ͬ��
				     * ���ݲ��ر��ʽ�Ķ��壬���Ե�һ��������Ϊ'+'��'x'֮��������������ڽ���set����ǰ������
				     * �ڶ�ÿһ����׺���ʽת���ɲ��ر��ʽ֮ǰ��ͨ����ԭ����Ҫ����λ�ò�����ͬһ�����ر��ʽ������������������
				     * �Ϳ���ʹset�������ϳ����ظ��ı��ʽ���Ӷ�����ŵ�set�����С�
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




