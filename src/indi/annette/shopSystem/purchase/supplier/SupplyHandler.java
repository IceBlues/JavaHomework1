package indi.annette.shopSystem.purchase.supplier;

import indi.annette.shopSystem.purchase.stock.StockGood;
import java.io.*;
import java.util.*;

public class SupplyHandler {
    private static ArrayList<SupplyGood> goodList = new ArrayList<SupplyGood>();
    private static File supplier;

    public static void initialize(String supplierFile){
        supplier = new File("src/resources/" + supplierFile + ".txt");

        try {
            Scanner in = new Scanner(supplier);
            while(in.hasNext()){
                String subString[] = in.nextLine().split(",");
                goodList.add(new SupplyGood(subString[0], Integer.parseInt(subString[1])));
            }
        }
        catch (IOException e){
            System.out.println("File not found");
        }
    }

    public static void addSupplyGood(String id, int number){
        boolean isContain = false;

        for(SupplyGood G : goodList){
            if(id.equals(G.getId())){
                G.setNumber(G.getNumber() + number);
                isContain = true;
                break;
            }
        }

        if(!isContain){
            goodList.add(new SupplyGood(id, number));
        }
    }

    public static void freshSupplierFile(){
        try {
            PrintWriter out = new PrintWriter(supplier);
            for(SupplyGood G : goodList){
                out.println(G.getId() + "," + G.getNumber());
            }

            out.close();
        }
        catch (IOException e){
            System.out.println("File not found");
        }
    }
}
