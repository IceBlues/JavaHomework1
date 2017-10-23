public class Good {
    private String id;
    private String name;
    private int number;
    private double price;

    public Good(String id, String name, int number, double price){
        this.id = id;
        this.name = name;
        this.number = number;
        this.price = price;
    }

    public Good(String id, int number){
        this.id = id;
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public double getPrice() {
        return price;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
