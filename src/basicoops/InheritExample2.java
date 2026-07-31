

public class InheritExample2 {
public static void main(String[] args) {
    Person obj=new Person();
    System.out.println(obj.name);
    System.out.println(obj.gender);
   
}
}

class Person{
    String name;
    char gender;
   Person(){
        this.name=null;
        this.gender='\0';
    }
    Person(String N){
        this.name=N;
        this.gender='\0';
    }
    Person(char G){
        this.name=null;
        this.gender=G;
    }
    Person(String N,char G){
        this.name=N;
        this.gender=G;
    }

}