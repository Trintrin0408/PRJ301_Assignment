/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.ArrayList;

/**
 *
 * @author sonnt-local
 */
public class PlanCampain {

    private int id;
    private Plan plan;
    private Product product;
    private int quantity;
    private float cost;
    private ArrayList<SchedualCampain> schedualCampains = new ArrayList<>();

    public ArrayList<SchedualCampain> getSchedualCampains() {
        return schedualCampains;
    }

    public void setSchedualCampains(ArrayList<SchedualCampain> schedualCampains) {
        this.schedualCampains = schedualCampains;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

}
