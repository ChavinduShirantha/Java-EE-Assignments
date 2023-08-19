package lk.ijse.jsp.dto;

/**
 * @author : Chavindu
 * created : 8/19/2023-8:30 AM
 **/

public class CustomerDTO {
    private String id;
    private String name;
    private String address;
    private double Salary;

    public CustomerDTO() {
    }

    public CustomerDTO(String id, String name, String address, double salary) {
        this.id = id;
        this.name = name;
        this.address = address;
        Salary = salary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getSalary() {
        return Salary;
    }

    public void setSalary(double salary) {
        Salary = salary;
    }
}
