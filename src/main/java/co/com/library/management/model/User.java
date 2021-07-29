package co.com.library.management.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "User")
public class User {

    @Id
    private String id;
    private String name;

    private String idResource;
    private String nameResource;
    private LocalDate dateResource;

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
