package com.codegym.model;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private LocalDate birthDate;
    private String address;
    private String avatar;
    private float salary;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public Employee() {
    }
    public Employee(String name, LocalDate birthDate, String address, String avatar, float salary, Department department) {
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
        this.avatar = avatar;
        this.salary = salary;
        this.department = department;
    }
    public Employee(Long id,String name, LocalDate birthDate, String address, String avatar, float salary, Department department) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
        this.avatar = avatar;
        this.salary = salary;
        this.department = department;
    }


    @Override
    public String toString(){
        return String.format("Employee[id=%d,name=%s,birthDate=%s,address=%s,avatar=%s,salary=%s]",
                                id,name,birthDate,address,avatar,salary);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
