import java.util.Scanner;
import java.util.ArrayList;

/**
 * 
 *  Mariah Jackson
 *  
 *  Test 2
 *  
 *  Description of Program: 
 *  
 *  Created to help staff with payroll
 *  
 *  @author Mariah Jackson
 *  @version 3/27/2024
 * 
 */

public class PaystubMJ{
	
/**	
	*  Description of Method: 
    *  Creating the variables and establishing the Main routine 
*/
	
	/**
	 *@param args   for class to work
	
	*/
	
	
	
	
	
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Double> grossPayList = new ArrayList<>();
        String employeeName;
        double hourlyWage, hoursWorked, withholdingExemptions;
        String maritalStatus;
        double grossIncome, medicareTax, socialSecurityTax, ficaTax, federalIncomeTaxWithheld, netIncome;
        double totalPayroll = 0;

        System.out.println("Paystub Calculator");
        System.out.println("-------------------------------------------------------");

        do {
            System.out.print("Employee Name:(type 'Quit' to end program) ");
            employeeName = ReadStringFromUser(scanner);
            if (employeeName.equalsIgnoreCase("Quit")) {
                break;
            }

            System.out.print("Hourly Wage: ");
            hourlyWage = ReadFloatFromUser(scanner);
            System.out.print("Hours Worked: ");
            hoursWorked = ReadFloatFromUser(scanner);
            System.out.print("Withholding Exemptions: ");
            withholdingExemptions = ReadFloatFromUser(scanner);
            System.out.print("Marital Status (S = Single, M = Married): ");
            maritalStatus = ReadStringFromUser(scanner);

            grossIncome = calculateGrossIncome(hourlyWage, hoursWorked);
            medicareTax = grossIncome * 0.0145;
            socialSecurityTax = grossIncome * 0.062;
            ficaTax = medicareTax + socialSecurityTax;
            federalIncomeTaxWithheld = calculateFederalTax(grossIncome, withholdingExemptions, maritalStatus);
            netIncome = grossIncome - ficaTax - federalIncomeTaxWithheld;

            grossPayList.add(grossIncome);

            System.out.printf("Paycheck for %s\n", employeeName);
            System.out.printf("Gross Income:                           $%12.2f\n", grossIncome);
            System.out.printf(" less Medicare Tax:                     $%12.2f\n", medicareTax);
            System.out.printf(" less Social Security Tax:              $%12.2f\n", socialSecurityTax);
            System.out.printf(" less FICA Tax:                         $%12.2f\n", ficaTax);
            System.out.printf(" less Federal Income Tax Withheld:      $%12.2f\n", federalIncomeTaxWithheld);
            System.out.printf("Net Income:                             $%12.2f\n", netIncome);
            System.out.println("-----------------------------------------------------");

        } while (true);

        for (Double grossPay : grossPayList) {
            totalPayroll += grossPay;
        }

        System.out.printf("Total Payroll: $%.2f\n", totalPayroll);
    }

    private static String ReadStringFromUser(Scanner scanner) {
        return scanner.nextLine();
    }

    private static double ReadFloatFromUser(Scanner scanner) {
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next();
        }
        return scanner.nextDouble();
    }

    private static double calculateGrossIncome(double hourlyWage, double hoursWorked) {
        if (hoursWorked <= 40) {
            return hourlyWage * hoursWorked;
        } else {
            return (40 * hourlyWage) + ((hoursWorked - 40) * hourlyWage * 1.5);
        }
    }

    private static double calculateFederalTax(double grossIncome, double exemptions, String maritalStatus) {
        double adjustedGrossIncome = grossIncome - (55.77 * exemptions);
        double tax = 0;

        if (adjustedGrossIncome <= 50.99) {
            tax = 0;
        } else if (adjustedGrossIncome <= 500.99) {
            tax = (maritalStatus.equalsIgnoreCase("S") ? 0.10 : 0.05) * (adjustedGrossIncome - 51);
        } else if (adjustedGrossIncome <= 2500.99) {
            tax = (maritalStatus.equalsIgnoreCase("S") ? 45 + 0.15 * (adjustedGrossIncome - 500) : 22.5 + 0.10 * (adjustedGrossIncome - 500));
        } else if (adjustedGrossIncome <= 5000) {
            tax = (maritalStatus.equalsIgnoreCase("S") ? 345 + 0.20 * (adjustedGrossIncome - 2500) : 225.5 + 0.15 * (adjustedGrossIncome - 2500));
        } else {
            tax = (maritalStatus.equalsIgnoreCase("S") ? 845 + 0.25 * (adjustedGrossIncome - 5000) : 600.5 + 0.20 * (adjustedGrossIncome - 5000));
        }

        return tax;
    }
}
