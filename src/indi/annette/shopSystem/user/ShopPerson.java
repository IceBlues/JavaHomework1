package indi.annette.shopSystem.user;

public class ShopPerson {
    private String id;
    private String name;
    ShopPerson(String name, String id){
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName(){
        return name;
    }
}
