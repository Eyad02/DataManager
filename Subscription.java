import java.util.List;
import java.util.ArrayList;

public class Subscription {
    private double cost;
    private String billingCycle;
    private int startMonth;
    private int startDay;
    private int startYear;
   
    public Subscription(double cost, String billingCycle, int month, int day, int year){
        this.cost = cost;
        this.billingCycle = billingCycle;
        startMonth = month;
        startDay = day;
        startYear = year;
    }
   
    //Returns cost of subscription per billing cycle
    public double getCost(){
        return cost;
    }
   
    //Sets cost of subscription per billing cycle
    public void setCost(double cost){
        this.cost = cost;
    }
   
    //Returns frequency of cost to be paid
    public String getBillingCycle(){
        return billingCycle;
    }
   
    //Sets frequency of cost to be paid
    public void setBillingCycle(String cycle){
        this.billingCycle = cycle;
    }
   
    //Returns month of subscription effective date
    public int getMonth(){
        return startMonth;
    }
   
    //Returns day of subscription effective date
    public int getDay(){
        return startDay;
    }
   
    //Returns year of subscription effective date
    public int getYear(){
        return startYear;
    }
}
