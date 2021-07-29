package co.com.library.management.service;

import co.com.library.management.dto.ResourcesDTO;
import co.com.library.management.dto.UserDTO;
import co.com.library.management.mapper.ResourceMapper;
import co.com.library.management.model.Resources;
import co.com.library.management.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
     private UserService userService;

    ResourceMapper mapper = new ResourceMapper();

    public ResourcesDTO createResource(ResourcesDTO resourcesDTO){
        resourcesDTO.setAvailable(Boolean.TRUE);
        Resources resources = mapper.toCollection(resourcesDTO);
        return mapper.toDTO(resourceRepository.save(resources));
    }

    public ResourcesDTO updateResource(ResourcesDTO resourcesDTO){
        Resources resources = mapper.toCollection(resourcesDTO);
        resourceRepository.findById(resources.getId()).orElseThrow(()-> new RuntimeException("The resource isn't exists"));
        return mapper.toDTO(resourceRepository.save(resources));
    }

    public ResourcesDTO findById(String id){
        Resources resources = resourceRepository.findById(id).orElseThrow(()-> new RuntimeException("The resource doesn't exists"));
        return mapper.toDTO(resources);
    }

    public List<ResourcesDTO> findAll(){
        List<Resources> resourcesList = (List<Resources>) resourceRepository.findAll();
        return  mapper.listToDTO(resourcesList);
    }

    public void delete(String id){
        resourceRepository.deleteById(id);
    }

    public List<ResourcesDTO> findByType(String type){
        Iterable<Resources> listResources = resourceRepository.findByType(type);
        return mapper.listToDTO(listResources);
    }

    public List<ResourcesDTO> findByTheme(String theme){
        Iterable<Resources> listResources = resourceRepository.findByTheme(theme);
        return mapper.listToDTO(listResources);
    }

    public String checkAvailability(String idResource){
        ResourcesDTO resourcesDTO = findById(idResource);
        if(resourcesDTO.getAvailable().equals(Boolean.FALSE)){

            return "The resource isn't available. It was loaned at "+resourcesDTO.getDate();
        }else{
            return "The resource is available";
        }
    }

    public String realizeLoan(String idResource, String uid){
        ResourcesDTO resourcesDTO = findById(idResource);
        if(resourcesDTO.getAvailable().equals(Boolean.FALSE)){
            return "The resource isn't available. Please try again";
        }else{
            UserDTO userDTO = userService.findById(uid);
            resourcesDTO.setAvailable(Boolean.FALSE);
            resourcesDTO.setDate(LocalDate.now());
            resourcesDTO.setUid(uid);
            userDTO.setIdResource(resourcesDTO.getId());
            userDTO.setNameResource(resourcesDTO.getName());
            userDTO.setDateResource(resourcesDTO.getDate());
            userService.createUser(userDTO);
            resourceRepository.save(mapper.toCollection(resourcesDTO));
            return "The loan has been approved";
        }
    }

    public String returnLoan(String idResource, String uid){
        ResourcesDTO resourcesDTO = findById(idResource);
        if(resourcesDTO.getAvailable().equals(Boolean.TRUE)){
            return  "Error: The resource can't be returned because it is available";
        }else{
            UserDTO userDTO = userService.findById(uid);
            resourcesDTO.setAvailable(Boolean.TRUE);
            resourcesDTO.setDate(null);
            resourcesDTO.setUid("");
            userDTO.setIdResource("");
            userDTO.setNameResource("");
            userDTO.setDateResource(null);
            userService.createUser(userDTO);
            resourceRepository.save(mapper.toCollection(resourcesDTO));
            return "Thank you for returning the resource";
        }
    }
}