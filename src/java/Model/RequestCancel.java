/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Acer
 */
public class RequestCancel {

    private int id;
    private AClass AClass;
    private String status;
    private String readed;

    public RequestCancel(int id, AClass AClass, String status, String readed) {
        this.id = id;
        this.AClass = AClass;
        this.status = status;
        this.readed = readed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AClass getAClass() {
        return AClass;
    }

    public void setAClass(AClass AClass) {
        this.AClass = AClass;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReaded() {
        return readed;
    }

    public void setReaded(String readed) {
        this.readed = readed;
    }

    @Override
    public String toString() {
        return "RequestCancel{" + "id=" + id + ", AClass=" + AClass + ", status=" + status + ", readed=" + readed + '}';
    }

}
