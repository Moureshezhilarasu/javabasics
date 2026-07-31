package basicoops;
public class Inheritanceexamples {
public static void main(String[] args) {
        Parent parent = new Parent("John");
        parent.display();

        Child child = new Child("Jane", "Doe");
        child.display();

        
    }

}

class Parent {
    String name;
    Parent(String N){
        this.name = N;
    }
    void display() {
        System.out.println("Parent Name: " + this.name);
    }
}

class Child extends Parent {
    String name ;

    Child(String N, String S) {
        super(S);
        this.name = N;
    }           
    void display() {
        System.out.print("Child Name: " + this.name+"\t");
        super.display();
    }
}

clasş Person{
    String name;
    char gender;
    person(){
        
    }
}



