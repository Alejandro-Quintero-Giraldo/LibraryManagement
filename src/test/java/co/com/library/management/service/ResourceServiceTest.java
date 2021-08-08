package co.com.library.management.service;

import co.com.library.management.dto.ResourcesDTO;
import co.com.library.management.dto.UserDTO;
import co.com.library.management.model.Resources;
import co.com.library.management.model.User;
import co.com.library.management.repository.ResourceRepository;
import co.com.library.management.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;


@ExtendWith(MockitoExtension.class)
class ResourceServiceTest {

    @Mock
    private ResourceRepository resourceRepository;

    @InjectMocks
    private ResourceService resourceService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private  UserService userService;

    @Test
    void createResource() {

        var resource = new Resources();
        resource.setId("1");
        resource.setName("La evolución de la vida en la tierra");
        resource.setType("Libro");
        resource.setTheme("Ciencias naturales");
        resource.setDate(LocalDateTime.now());
        resource.setUid("123");

        var resourceDTO = new ResourcesDTO();
        resourceDTO.setId("2");
        resourceDTO.setName("El avance de las nuevas tecnologías");
        resourceDTO.setType("Libro");
        resourceDTO.setTheme("Informática");
        resourceDTO.setDate(LocalDateTime.now());
        resourceDTO.setUid("123");

        Mockito.when(resourceRepository.save(any())).thenReturn((resource));

        var response = resourceService.createResource(resourceDTO);
        Assertions.assertEquals(resource.getName(), response.getName());
        Assertions.assertEquals(resource.getType(), response.getType());
        Assertions.assertEquals(resource.getTheme(), response.getTheme());
        Assertions.assertEquals(resource.getDate(), response.getDate());
        Assertions.assertEquals(resource.getUid(), response.getUid());
    }

    @Test
    @DisplayName("Happy Case")
    void findAll() {

        var resource = new Resources();
        resource.setId("1");
        resource.setName("La evolución de la vida en la tierra");
        resource.setType("Libro");
        resource.setTheme("Ciencias naturales");
        resource.setDate(LocalDateTime.now());
        resource.setUid("123");

        var resource2 = new Resources();
        resource.setId("2");
        resource.setName("El avance de las nuevas tecnologías");
        resource.setType("Libro");
        resource.setTheme("Informática");
        resource.setDate(LocalDateTime.now());
        resource.setUid("123");

        var listResources = new ArrayList<Resources>();
        listResources.add(resource);
        listResources.add(resource2);

        Mockito.when(resourceRepository.findAll()).thenReturn(listResources);

        var response = resourceService.findAll();
        Assertions.assertEquals(2, response.size());
        Assertions.assertEquals(resource.getName(), listResources.get(0).getName());
        Assertions.assertEquals(resource2.getName(), listResources.get(1).getName());
    }

    @Test
    void findById(){


        var resource = new Resources();
        resource.setId("1");
        resource.setName("La evolución de la vida en la tierra");
        resource.setType("Libro");
        resource.setTheme("Ciencias naturales");
        resource.setDate(LocalDateTime.now());
        resource.setUid("123");

        Mockito.when(resourceRepository.findById("1")).thenReturn(Optional.of(resource));

        var response = resourceService.findById("1");
        Assertions.assertEquals(resource.getName(),response.getName());
        Assertions.assertEquals(resource.getType(),response.getType());
        Assertions.assertEquals(resource.getTheme(),response.getTheme());
    }

    @Test
    void findByType(){

        var resourceOld = new Resources();
        resourceOld.setId("1");
        resourceOld.setName("La evolución de la vida en la tierra");
        resourceOld.setType("Libro");
        resourceOld.setTheme("Ciencias naturales");
        resourceOld.setDate(LocalDateTime.now());
        resourceOld.setUid(null);

        var resource = new Resources();
        resource.setId("2");
        resource.setName("La evolución del ser humano");
        resource.setType("Libro");
        resource.setTheme("Ciencias naturales");
        resource.setDate(LocalDateTime.now());
        resource.setUid(null);

        var list = new ArrayList<Resources>();
        list.add(resourceOld);
        list.add(resource);

        Mockito.when(resourceRepository.findByType("Libro")).thenReturn(list);

        var response = resourceService.findByType("Libro");
        Assertions.assertEquals(resource.getName(),response.get(1).getName());
        Assertions.assertEquals(resourceOld.getName(), response.get(0).getName());
    }

    @Test
    void findByTheme(){

        var resourceOld = new Resources();
        resourceOld.setId("1");
        resourceOld.setName("La evolución de la vida en la tierra");
        resourceOld.setType("Libro");
        resourceOld.setTheme("Ciencias naturales");
        resourceOld.setDate(LocalDateTime.now());
        resourceOld.setUid(null);

        var resource = new Resources();
        resource.setId("2");
        resource.setName("La evolución del ser humano");
        resource.setType("Libro");
        resource.setTheme("Ciencias naturales");
        resource.setDate(LocalDateTime.now());
        resource.setUid(null);

        var list = new ArrayList<Resources>();
        list.add(resourceOld);
        list.add(resource);

        Mockito.when(resourceRepository.findByTheme("Libro")).thenReturn(list);

        var response = resourceService.findByTheme("Libro");
        Assertions.assertEquals(resource.getTheme(),response.get(1).getTheme());
        Assertions.assertEquals(resourceOld.getTheme(), response.get(0).getTheme());
    }

    @Test
    void checkAvailability(){

        var resource = new Resources();
        resource.setId("2");
        resource.setAvailable(Boolean.TRUE);

        var resource1 = new Resources();
        resource.setId("3");
        resource.setAvailable(Boolean.TRUE);

        var list = new ArrayList<Resources>();
        list.add(resource);
        list.add(resource1);

        Mockito.when(resourceRepository.findByName("La evolución del ser humano")).thenReturn(list);

        var response = resourceService.checkAvailability("La evolución del ser humano");

        Assertions.assertEquals("The resource is available with the id "+resource.getId(),response);
    }

    @Test
    void delete(){

        var resource = new Resources();
        resource.setId("1");
        resource.setAvailable(Boolean.TRUE);

        Mockito.when(resourceRepository.findById("1")).thenReturn(Optional.of(resource));

        Mockito.doNothing().when(resourceRepository).deleteById("1");

        resourceService.delete("1");

        Mockito.verify(resourceRepository).deleteById("1");

        Mockito.verify(resourceRepository).findById("1");

    }

    @Test
    void update(){

        var resource = new Resources();
        resource.setId("2");
        resource.setName("La evolución del ser humano");
        resource.setType("Libro");
        resource.setTheme("Ciencias naturales");
        resource.setDate(null);
        resource.setUid(null);

        var resourceDTO = new ResourcesDTO();
        resourceDTO.setId("2");
        resourceDTO.setName("La evolución del ser humano");
        resourceDTO.setType("Libro");
        resourceDTO.setTheme("Ciencias naturales");
        resourceDTO.setDate(null);
        resourceDTO.setUid(null);

        //Mockito.when(resourceRepository.findById("2")).thenReturn(Optional.of(resource));

        //doThrow().when(resourceRepository.findById("3")).;
        //Mockito.when(resourceRepository.save(resource)).thenReturn(resource);

      //  var response = resourceService.updateResource(resourceDTO);

        //Assertions.assertEquals(resource.getName(), response.getName());

    }

    @Test
    void realizeLoan(){
        var resource = new Resources();
        resource.setId("2");
        resource.setAvailable(Boolean.TRUE);
        resource.setUid(null);

        var user = new UserDTO();
        user.setId("123");

        //Mockito.when(resourceRepository.findById("2")).thenReturn(Optional.of(resource));

        //Mockito.when(userService.findById("123")).thenReturn(user);

        //var response = resourceService.realizeLoan(resource.getId(),resource.getUid());

        //Assertions.assertEquals("The loan has been approved at "+LocalDateTime.now(),response);
    }

    @Test
    void returnLoan(){
        var resource = new Resources();
        resource.setId("2");
        resource.setAvailable(Boolean.TRUE);
        resource.setUid(null);

        var user = new UserDTO();
        user.setId("123");

        var userDTO = new User();
        user.setId("123");

        //Mockito.when(resourceRepository.findById("2")).thenReturn(Optional.of(resource));

       //Mockito.when(userService.findById("123")).thenReturn(user);

        //Mockito.when(resourceRepository.save(resource)).thenReturn(resource);
    }
}