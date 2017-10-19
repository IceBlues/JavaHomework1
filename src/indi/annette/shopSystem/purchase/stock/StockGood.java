package indi.annette.shopSystem.purchase.stock;

import indi.annette.shopSystem.purchase.Good;

public class StockGood extends Good {
    StockGood(String id, String name, int stockNum, double price) {
        super(id, name, stockNum, price);
    }
}
