package business;
import java.lang.Math;

public interface IcalculatorEMI {

    //Interface declared with a static function since it acts as a function only with no instance
    static double calculateEMI(double price,double downP, double interest,double loanT){
        //year of loan term
        double year=loanT/12;

        //money left to pay
        double leftToPay=(price - downP);

        //interest on money left to pay
        double totalAmount = leftToPay*Math.pow((1+(interest/100)),year);

        //total monthly amount to pay
        double monthlyP = totalAmount/loanT;

        return monthlyP;
    }//calculateEMI

}//iCalculatorEMI