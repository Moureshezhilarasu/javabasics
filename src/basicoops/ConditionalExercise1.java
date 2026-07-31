package basicoops;
import java.util.Scanner;
public class ConditionalExercise1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Grade Evaluation Program");
        System.out.print("Enter the Mark (0-100): ");
        int m=sc.nextInt();
        char Grade='x';
        sc.close();
        if(m>=0 && m<=100){
            if(m>=90 && m<=100){
            System.out.println("Grade: A");
            Grade='A';
        } else if(m>=80 && m<90){
            System.out.println("Grade: B");
            Grade='B';
        } else if(m>=70 && m<80){
            System.out.println("Grade: C");
            Grade='C';
        } else if(m>=60 && m<70){
            System.out.println("Grade: D");
            Grade='D';
        } else if(m>=0 && m<60){
            System.out.println("Grade: F");
            Grade='F';
        }
        }else{
            System.out.println("Invalid mark entered. Please enter a value between 0 and 100.");
        }

        switch(Grade){
            case 'A':
                System.out.println("Excellent work!");
                break;
            case 'B':
                System.out.println("Good job!");
                break;
            case 'C':
                System.out.println("Satisfactory performance.");
                break;
            case 'D':
                System.out.println("Needs improvement.");
                break;
            case 'F':
                System.out.println("Failed. Please study more.");
                break;
            default:    
                System.out.println("No feedback available.");
                break;

        
        }
    }
}
