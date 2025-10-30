package com.pluralsight;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class ContractDataManager {

    public static void saveContract(Contract contract){
        try{
            FileWriter fileWriter = new FileWriter("Contracts.csv", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            if (contract instanceof SalesContract s){
               // bufferedWriter.write("Sale|" + s.getDate() + "")
               // bufferedWriter.write(String.format("aasfsdfsd", a, a, a));
                bufferedWriter.write(s.toEncodedString());
            }

        }
        catch (Exception e) {
            System.out.println("there was an error");
        }
    }
}
