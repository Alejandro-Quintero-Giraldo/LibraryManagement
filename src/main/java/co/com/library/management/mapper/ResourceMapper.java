package co.com.library.management.mapper;

import co.com.library.management.dto.ResourcesDTO;
import co.com.library.management.model.Resources;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ResourceMapper {

    public ResourcesDTO toDTO(Resources resources){
        ResourcesDTO resourcesDTO = new ResourcesDTO();
        resourcesDTO.setId(resources.getId());
        resourcesDTO.setName(resources.getName());
        resourcesDTO.setType(resources.getType());
        resourcesDTO.setTheme(resources.getTheme());
        resourcesDTO.setDate(resources.getDate());
        resourcesDTO.setAvailable(resources.getAvailable());
        resourcesDTO.setUid(resources.getUid());

        return resourcesDTO;
    }

    public Resources toCollection(ResourcesDTO resourcesDTO){
        Resources resources = new Resources();
        resources.setId(resourcesDTO.getId());
        resources.setName(resourcesDTO.getName());
        resources.setType(resourcesDTO.getType());
        resources.setTheme(resourcesDTO.getTheme());
        resources.setDate(resourcesDTO.getDate());
        resources.setAvailable(resourcesDTO.getAvailable());
        resources.setUid(resourcesDTO.getUid());

        return  resources;
    }

    public List<ResourcesDTO> listToDTO(Iterable<Resources> listCollection){
        if (listCollection == null){
            return null;
        }
        List<ResourcesDTO> listDTO = new ArrayList();
        Iterator listTracks = listCollection.iterator();

        while(listTracks.hasNext()){
            Resources resources = (Resources) listTracks.next();
            listDTO.add(toDTO(resources));
        }
        return listDTO;
    }
}
