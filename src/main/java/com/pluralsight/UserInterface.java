package com.pluralsight;

import java.rmi.dgc.Lease;
import java.util.ArrayList;

public class UserInterface {
    private Dealership dealership;

    private void init(){
        //Create dealership from DealershipFileManager
        DealershipFileManager dealershipFileManager = new DealershipFileManager();
        dealership = dealershipFileManager.getDealership();

    }

    public UserInterface() {
        init();
    }

    public int displayMenu(){
        int userInput = ConsoleHelper.promptForInt("""
                Dealership
                --------------
                1) Search By Price
                2) Search By Make And Model
                3) Search By Year
                4) Search By Color
                5) Search By Mileage
                6) Search By Vehicle Type
                7) Display All Vehicles
                8) Add Vehicle
                9) Remove Vehicle
                10) Sell/Lease a vehicle
                0) Exit
                """);

        return userInput;
    }

    //call displayMenu, read user input and call corresponding method
    public void display(){
        //while loop
        while(true) {
            switch (displayMenu()) {
                case 1:
                    processGetByPriceRequest();
                    break;
                case 2:
                    processGetByMakeModelRequest();
                    break;
                case 3:
                    processGetByYearRequest();
                    break;
                case 4:
                    processGetByColorRequest();
                    break;
                case 5:
                    processGetByMileageRequest();
                    break;
                case 6:
                    processGetByVehicleTypeRequest();
                    break;
                case 7:
                    processGetAllVehiclesRequest();
                    break;
                case 8:
                    processAddVehicleRequest();
                    break;
                case 9:
                    processRemoveVehicleRequest();
                    break;
                case 10:
                    processSellOrLeaseVehicleRequest();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid input, please enter a whole number 0-9");
            }
        }
    }

    private void displayVehicles(ArrayList<Vehicle> vehicles){
        for (Vehicle v : vehicles){
            System.out.println(v);
        }
    }

    public void processGetByPriceRequest(){
        double minPrice = ConsoleHelper.promptForDouble("Minimum price of Vehicle");
        double maxPrice = ConsoleHelper.promptForDouble("Maximum price of Vehicle");
        displayVehicles(dealership.getVehiclesByPrice(minPrice, maxPrice));
        //for (Vehicle v : dealership.getVehiclesByPrice(minPrice, maxPrice)) {
           // System.out.println(v);
           // System.out.println(this.dealership.getVehiclesByPrice(minPrice, maxPrice));
        //}

    }

    public void processGetByMakeModelRequest(){
        String make = ConsoleHelper.promptForString("Enter the make of the vehicle");
        String model = ConsoleHelper.promptForString("Enter the model of the vehicle");
        displayVehicles(dealership.getVehiclesByMakeModel(make, model));


    }

    public void processGetByYearRequest(){
        int minYear = ConsoleHelper.promptForInt("Enter the earliest year of the vehicles you want to see");
        int maxYear = ConsoleHelper.promptForInt("Enter the latest year of the vehicles you want to see");
        displayVehicles(dealership.getVehiclesByYear(minYear, maxYear));


    }

    public void processGetByColorRequest(){
        String color = ConsoleHelper.promptForString("Enter the color of the vehicle you want to see");
        displayVehicles(dealership.getVehiclesByColor(color));
    }

    public void processGetByMileageRequest(){
        int minMileage = ConsoleHelper.promptForInt("Enter the minimum miles of the vehicles you want to see");
        int maxMileage = ConsoleHelper.promptForInt("Enter the maximum miles of the vehicles you want to see");
        displayVehicles(dealership.getVehiclesByMileage(minMileage, maxMileage));
    }

    public void processGetByVehicleTypeRequest(){
        String type = ConsoleHelper.promptForString("Enter the type of the vehicles you want to see");
        displayVehicles(dealership.getVehiclesByType(type));
    }

    public void processGetAllVehiclesRequest(){

        displayVehicles(dealership.getAllVehicles());

    }



    public void processAddVehicleRequest(){

        int vin = ConsoleHelper.promptForInt("Enter the vin number");
        int year = ConsoleHelper.promptForInt("Enter the year of the vehicle");
        String make = ConsoleHelper.promptForString("Enter the make of the vehicle");
        String model = ConsoleHelper.promptForString("Enter the model of the vehicle");
        String type = ConsoleHelper.promptForString("Enter the type of the vehicle");
        String color = ConsoleHelper.promptForString("Enter the color of the vehicle");
        int odometer = ConsoleHelper.promptForInt("Enter the mileage of the vehicle");
        double price = ConsoleHelper.promptForDouble("Enter the price of the vehicle");

        Vehicle v = new Vehicle(vin, year, make, model, type, color, odometer, price);
        dealership.addVehicle(v);

        DealershipFileManager.saveDealership(dealership);
        System.out.println("Vehicle added successfully");




    }

    public void processRemoveVehicleRequest(){

        int vin = ConsoleHelper.promptForInt("Enter the vin number of the vehicle you want to remove");

        Vehicle v = dealership.getVehicleByVIN(vin);

        if(v != null){
            dealership.removeVehicle(v);
            DealershipFileManager.saveDealership(dealership);
        }
        else{
            System.out.println("Vehicle not found");
        }
    }

    public Vehicle getVehicleByVinPrompt(){
       boolean isFound = false;
        do {
            int vin = ConsoleHelper.promptForInt("Enter the vin number of the vehicle you would like to purchase");
            Vehicle vehicleSold = dealership.getVehicleByVIN(vin);
            if (vehicleSold != null) {
                isFound = true;
                return vehicleSold;
            }
            else{
                System.out.println("Vehicle not found");
                return null;
            }
        }while(!isFound);

    }

    public SalesContract createSalesContractWithCustomer(){
        //Make a sales contract from user input and return
       Vehicle v = getVehicleByVinPrompt();
        String date = ConsoleHelper.promptForString("Contract date (YYYYMMDD)");
        String name = ConsoleHelper.promptForString("Customer name");
        String email = ConsoleHelper.promptForString("Customer email") ;
        String financeOption = ConsoleHelper.promptForString("Would you like to finance the vehicle (yes/no");
        //todo add console helper promptForYesNo

        boolean isFinanced = financeOption.equalsIgnoreCase("yes") ? true : false;

         SalesContract salesContract = new SalesContract(date, name, email, v, isFinanced);
         return salesContract;




    }

    public LeaseContract createLeaseContractWithCustomer(){
        return null;
    }

    public int sellOrLeasePrompt(){
        //Print sell or lease screen and return user input
        int sellOrLease = ConsoleHelper.promptForInt("Sell or Lease\n" +
                "1) Sell \n" +
                "2) Lease");
        return sellOrLease;
    }

    public void processSellOrLeaseVehicleRequest(){



        switch(sellOrLeasePrompt()){
            case 1:
                Contract c = createSalesContractWithCustomer();
               ContractDataManager.saveContract(c);

               //remove the vehicle sold from csv
                dealership.removeVehicle(dealership.getVehicleByVIN(c.getVehicleSold().getVin()));
               DealershipFileManager.saveDealership(dealership);

                break;
            case 2:
                createLeaseContractWithCustomer();
                //dealership.removeVehicle(v);
              //  ContractDataManager.saveContract();
                break;
        }



        //Append the data to contracts file
    }







}
