public abstract class Employee {
    private String id;
    private String name;

    /**
     * Construct an Employee Object.
     * The employee is not really be created, but its subclass as an Employee need this method.
     *
     * @param name the name of employee
     * @param id the id of employee that was the unique ID of the Employee
     * */
    Employee(String name, String id){
        this.name = name;
        this.id = id;
    }

    /**
     * Return an employee's id.
     *
     * @return an employee Object's id
     * */
    public String getId() {
        return id;
    }

    /**
     * Return an employee's name.
     *
     * @return an employee Object's name
     * */
    public String getName(){
        return name;
    }
}
