package co.com.library.management.service;

import co.com.library.management.dto.ResourcesDTO;
import co.com.library.management.dto.UserDTO;
import co.com.library.management.mapper.ResourceMapper;
import co.com.library.management.model.Resources;
import co.com.library.management.model.User;
import co.com.library.management.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        List<Resources> resourcesList =  resourceRepository.findAll();
        return  mapper.listToDTO(resourcesList);
    }

    public void delete(String id){
        ResourcesDTO resourcesDTO = findById(id);
        if(resourcesDTO.getAvailable() == Boolean.FALSE) {
            UserDTO user = userService.findById(resourcesDTO.getUid());
            unsetterUser(user);
        }
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

    public String checkAvailability(String name){
        Iterable<Resources> resourcesList = resourceRepository.findByName(name);
        List<ResourcesDTO> resourcesNames = mapper.listToDTO(resourcesList);

        for (ResourcesDTO resources: resourcesNames){
            if(resources.getAvailable() == Boolean.TRUE){
                String idResource = resources.getId();

                return " resources. The resource is available with the id "+idResource;
            }
        }

        List<Resources> resourcesOrdened = checkUltimateLoan(resourcesNames);



        //if(resourcesOrdened.get(0).getAvailable().equals(Boolean.FALSE)){
        return "The resource isn't available. The latest was loan at "+resourcesOrdened.get(0).getDate();
    }
    public List<Resources> checkUltimateLoan(List<ResourcesDTO> resourcesNames){
        LocalDateTime dateMajor = LocalDateTime.MIN;
        List<Resources> resourcesOrdened = new ArrayList<>();
        for(ResourcesDTO resourcesDTO: resourcesNames){
            if(resourcesDTO.getDate().getYear() > dateMajor.getYear()){
                dateMajor = resourcesDTO.getDate();
                resourcesOrdened.add(0,mapper.toCollection(resourcesDTO));
            }else if (resourcesDTO.getDate().getMonthValue() > dateMajor.getMonthValue()){
                dateMajor = resourcesDTO.getDate();
                resourcesOrdened.add(0,mapper.toCollection(resourcesDTO));
            }else if(resourcesDTO.getDate().getDayOfMonth() > dateMajor.getDayOfMonth()){
                dateMajor = resourcesDTO.getDate();
                resourcesOrdened.add(0,mapper.toCollection(resourcesDTO));
            }else if (resourcesDTO.getDate().getHour() > dateMajor.getHour()){
                dateMajor = resourcesDTO.getDate();
                resourcesOrdened.add(0,mapper.toCollection(resourcesDTO));
            }else if(resourcesDTO.getDate().getMinute() > dateMajor.getMinute()){
                dateMajor = resourcesDTO.getDate();
                resourcesOrdened.add(0,mapper.toCollection(resourcesDTO));
            }else if(resourcesDTO.getDate().getSecond() > dateMajor.getSecond()){
                dateMajor = resourcesDTO.getDate();
                resourcesOrdened.add(0,mapper.toCollection(resourcesDTO));
            }
        }
        return resourcesOrdened;
    }

    public String realizeLoan(String idResource, String uid){
        ResourcesDTO resourcesDTO = findById(idResource);
        if(resourcesDTO.getAvailable().equals(Boolean.FALSE) ){
            return "The resource isn't available. Please try again";
        }else{
            UserDTO userDTO = userService.findById(uid);
            if( userDTO.getIdResource() != null ){
                return "The user can't loan two resources. First return the resource";
            }
            resourcesDTO.setAvailable(Boolean.FALSE);
            resourcesDTO.setDate(LocalDateTime.now());
            resourcesDTO.setUid(uid);
            userDTO.setIdResource(resourcesDTO.getId());
            userDTO.setNameResource(resourcesDTO.getName());
            userDTO.setDateResource(resourcesDTO.getDate());
            userService.createUser(userDTO);
            resourceRepository.save(mapper.toCollection(resourcesDTO));
            return "The loan has been approved at "+LocalDateTime.now();
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
            unsetterUser(userDTO);
            resourceRepository.save(mapper.toCollection(resourcesDTO));
            return "Thank you for returning the resource";
        }
    }

    public void unsetterUser(UserDTO userDTO){
        userDTO.setIdResource(null);
        userDTO.setNameResource(null);
        userDTO.setDateResource(null);
        userService.updateUser(userDTO);
    }
}