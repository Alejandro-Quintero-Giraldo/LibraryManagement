package co.com.library.management.dto;

import java.time.LocalDate;

public class UserDTO {

    private String id;
    private String name;
    private String idResource;

    private String nameResource;
    private LocalDate dateResource;

    public UserDTO(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdResource() {
        return idResource;
    }

    public void setIdResource(String idResource) {
        this.idResource = idResource;
    }

    public String getNameResource() {
        return nameResource;
    }

    public void setNameResource(String nameResource) {
        this.nameResource = nameResource;
    }

    public LocalDate getDateResource() {
        return dateResource;
    }

    public void setDateResource(LocalDate dateResource) {
        this.dateResource = dateResource;
    }
}
