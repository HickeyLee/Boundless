package leetcode;

//public class EmployeeTest {
//    public static void main(String[] args){
//        /* 使用构造器创建两个对象 */
//        Employee empOne = new Employee("RUNOOB1");
//        Employee empTwo = new Employee("RUNOOB2");
//
//        // 调用这两个对象的成员方法
//        empOne.empAge(26);
//        empOne.empDesignation("高级程序员");
//        empOne.empSalary(1000);
//        empOne.printEmployee();
//
//        empTwo.empAge(21);
//        empTwo.empDesignation("菜鸟程序员");
//        empTwo.empSalary(500);
//        empTwo.printEmployee();
//
//
//        String s1 = "Runoob";              // String 直接创建
//        String s2 = "Runoob";              // String 直接创建
//
//        s1 = "dsdf";
//
//        System.out.println(s1);
//        System.out.println(s2);
//
//    }


//}



class Animal{
    public void move(){
        System.out.println("动物可以移动");
    }
}

class Dog extends Animal{
    public void move(){
        System.out.println("狗可以跑和走");
    }
    public void bark(){
        System.out.println("狗可以吠叫");
    }
}

public class EmployeeTest{
    public static void main(String args[]){
        Animal a = new Animal(); // Animal 对象
        Dog b = new Dog(); // Dog 对象

        a.move();// 执行 Animal 类的方法
        int res= Integer.MIN_VALUE;
        System.out.println(res);
//        b.move();//执行 Dog 类的方法
//        b.bark();
    }
}











