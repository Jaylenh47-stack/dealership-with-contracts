package com.pluralsight;

import java.io.*;

public class DealershipFileManager {

    public Dealership getDealership() {
        Dealership dealership = null;
        try {
            FileReader fileReader = new FileReader("inventory.csv");
            BufferedReader bufReader = new BufferedReader(fileReader);

            //read from file and make a dealership
            String fileLine = bufReader.readLine();
            String[] getDealershipParts = fileLine.split("\\|");
            String name = getDealershipParts[0];
            String address = getDealershipParts[1];
            String phone = getDealershipParts[2];
            dealership = new Dealership(name, address, phone);

            while((fileLine = bufReader.readLine()) != null){
                //create vehicle and add it to dealership
                String[] getVehicleParts = fileLine.split("\\|");
                int vin = Integer.parseInt(getVehicleParts[0]);
                int year = Integer.parseInt(getVehicleParts[1]);
                String make = getVehicleParts[2];
                String model = getVehicleParts[3];
                String vehicleType = getVehicleParts[4];
                String color = getVehicleParts[5];
                int odometer = Integer.parseInt(getVehicleParts[6]);
                Double price = Double.parseDouble(getVehicleParts[7]);

                Vehicle vehicle = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);
                dealership.addVehicle(vehicle);

            }

        } catch (IOException e) {
            System.out.println("There was a file error");
            e.getStackTrace();
        }

        return dealership;


    }

    public static void saveDealership(Dealership dealership){
        try {
            FileWriter fileWriter = new FileWriter("inventory.csv");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            //Write the dealership to the file
            bufferedWriter.write(dealership.toString());
            bufferedWriter.newLine();

            //Loop through the inventory and write every vehicle to the file
            for (Vehicle v : dealership.getAllVehicles()){
                bufferedWriter.write(v.toString());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();


        }
        catch (IOException e) {
            System.out.println("There was a file error");
        }




    }






















}
