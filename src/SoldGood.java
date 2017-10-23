public class SoldGood extends Good {
    SoldGood(String id, int number) {
        super(id, number);
    }

    SoldGood(String name, String id, int number, double price) {
        super(id, name, number, price);
    }
}
