package com.pluralsight;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class ContractDataManager {

    public static void saveContract(Contract contract){
        try{
            FileWriter fileWriter = new FileWriter("Contracts.csv", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            if (contract instanceof SalesContract s){
                bufferedWriter.newLine();
                bufferedWriter.write(s.toEncodedString());
                bufferedWriter.close();
            }

            if (contract instanceof LeaseContract l){
               // bufferedWriter.write(l.toEncodedString());
                bufferedWriter.close();
            }

        }
        catch (Exception e) {
            System.out.println("there was an error");
        }
    }
}
