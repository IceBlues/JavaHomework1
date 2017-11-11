public abstract class Product {
    private String id;
    private String name;
    private int number;

    public Product(String id, int number){
        this.id = id;
        this.number = number;
    }

    public Product(String id, String name, int number){
        this.id = id;
        this.name = name;
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

    public void setNumber(int number) {
        this.number = number;
    }
}
