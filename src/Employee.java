public abstract class Employee {
    private String id;
    private String name;
    Employee(String name, String id){
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
