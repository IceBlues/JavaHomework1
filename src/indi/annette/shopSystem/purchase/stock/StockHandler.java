package indi.annette.shopSystem.purchase.stock;

import indi.annette.shopSystem.purchase.receipt.SoldGood;

import java.io.*;
import java.util.*;

public class StockHandler {
    private static ArrayList<StockGood> goodList = new ArrayList<StockGood>();
    private static File stocks;

    public static void initialize(String stockFile) {
        stocks = new File("src/resources/" + stockFile + ".txt");
        try {
            Scanner in = new Scanner(stocks);
            String[] goodsProperty;
            while (in.hasNext()) {
                goodsProperty = in.nextLine().toUpperCase().split(",");
                try {
                    goodList.add(new StockGood(goodsProperty[0], goodsProperty[1], Integer.parseInt(goodsProperty[2]), Double.parseDouble(goodsProperty[3])));
                }
                catch (NumberFormatException e) {
                    System.out.println("The stock-file constant Error.");
                }
            }

            in.close();
        }
        catch (IOException e){
            System.out.println("Stock-file not found.");
        }
    }

    public static StockGood getGoodByID(String id) {
        StockGood aStockGood = null;
        for(StockGood T : goodList){
            if(T.getId().equals(id)){
                aStockGood = T;
                break;
            }
        }

        return aStockGood;
    }

    public static void freshStockWithSoldGood(SoldGood good) {
        boolean isNotFound = true;
        for (int i = 0; i < goodList.size() && isNotFound; i++) {
            if (good.getId().equals(goodList.get(i).getId())) {
                goodList.get(i).setNumber(goodList.get(i).getNumber() - good.getNumber());
                isNotFound = false;
            }
        }
    }

    public static void freshStockByReturnGood(String goodID, int returnNumber){
        boolean isNotFound = true;
        for (int i = 0; i < goodList.size() && isNotFound; i++) {
            StockGood G = goodList.get(i);
            if (goodID.equals(G.getId())) {
                goodList.get(i).setNumber(G.getNumber() + returnNumber);
                isNotFound = false;
            }
        }

        if(isNotFound){
            System.out.println("The Good not found.");
        }
    }


    public static void freshStockFile(){
        try {
            PrintWriter out = new PrintWriter(stocks);

            for(StockGood good : goodList) {
                out.print(good.getId() + ",");
                out.print(good.getName() + ",");
                out.print(good.getNumber() + ",");
                out.println(good.getPrice());
            }

            out.close();
        }
        catch (IOException e){
            System.out.println("Stock-file not found.");
        }
    }

    public static String checkStock(){
        String result = "";
        for(StockGood G : goodList){
            result += "Code:\t\t\t" + G.getId() + "\n";
            result += "Name:\t\t\t" + G.getName() + "\n";
            result += "Number:\t\t" + G.getNumber() + "\n";
            result += "Price:\t\t\t" + G.getPrice() + "\n\n";
        }

        return result;
    }
}
