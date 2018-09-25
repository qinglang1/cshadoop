package neibulei;
/*
	定义在类中的类称为内部类,外边的类称为外部类.

	内部类:
		成员内部类:
		局部内部类:

*/

import org.junit.Test;

class Outer{
	private int a = 10;
	public int b = 20;
	static int c = 30;



	//非静态的成员内部类
	class Inner{
		//非静态内部类不能定义静态成员(变量,方法)
		// static int num1 = 10;
		int num2 = 20;
        //非静态的成员内部类,可以使用外部类的静态和非静态成员
		public void show(){
			System.out.println(a +","+ b +"," + c);
		}
	}
    //外部类成员方法中调用非静态的成员内部类的方法
			public void testchengyuanfeijingtai(){
				Inner in1 = new Inner();
				in1.show();
    }

	//静态内部类
	static class Inner2{
        //静态内部类:可以定义静态成员
		static int num3 = 30;
		int num4 = 40;

		//只能访问外部类的静态成员
		public void show(){
			System.out.println(c);
		}
	}


	//外部类成员方法中调用静态成员内部类中的方法
	public void testchengyuanjingtai(){
		Inner2 in2 = new Inner2();
		in2.show();

	}


	//成员方法中的非静态局部内部类
	public void testjubuneibulei() {
    //非静态局部内部类,不可以定义非静态成员，可以访问外部类中的静态和非静态成员
   class A {
       int a;

        public void show() {

            System.out.println(a + "," + b + "," + c);
            System.out.println("非静态局部内部类中的方法");
        }
    }

        A a = new A();
   a.show();

    }




}



//
public class InnerClassDemo{
	public static void main(String[] args){
		//Inner为非静态成员内部类
        //创建非静态成员内部类对象 通过创建外部类对象创建非静态内部类对象
//		Outer.Inner in1 = new Outer().new Inner();

//		in1.show();



		//Inner2为静态成员内部类
        //创建静态成员内部类对象  通过外部类类名打点的方式创建静态内部类对象
//		Outer.Inner2 in2 = new Outer.Inner2();
//		in2.show();



		Outer outer1 = new Outer();
 //       outer1.testchengyuanfeijingtai();

		outer1.testchengyuanjingtai();


 //       Outer Outer2 = new Outer();
  //      Outer2.testjubuneibulei();


    }


}