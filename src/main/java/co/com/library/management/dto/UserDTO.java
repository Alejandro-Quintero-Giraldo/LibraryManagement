package co.com.library.management.dto;

import java.time.LocalDateTime;

public class UserDTO {

    private String id;
    private String name;
    private String idResource;

    private String nameResource;
    private LocalDateTime dateResource;

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

    public LocalDateTime getDateResource() {
        return dateResource;
    }

    public void setDateResource(LocalDateTime dateResource) {
        this.dateResource = dateResource;
    }
}
