package co.com.library.management.controller;

import co.com.library.management.dto.ResourcesDTO;
import co.com.library.management.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/management")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping("/getResourceId/{id}")
    public ResponseEntity<ResourcesDTO> findById(@PathVariable("id") String id){
        return new ResponseEntity(resourceService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ResourcesDTO>> findAll(){
        return new ResponseEntity(resourceService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/createResource")
    public ResponseEntity<ResourcesDTO> createResource(@RequestBody ResourcesDTO resourcesDTO){
        return new ResponseEntity(resourceService.createResource(resourcesDTO), HttpStatus.OK);
    }

    @PutMapping("/updateResource")
    public ResponseEntity<ResourcesDTO> updateResource(@RequestBody ResourcesDTO resourcesDTO){
        return new ResponseEntity(resourceService.updateResource(resourcesDTO), HttpStatus.OK);
    }

    @DeleteMapping("/deleteResource/{id}")
    public ResponseEntity deleteResource(@PathVariable("id") String id){
        try {
            resourceService.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getResourcesType/{type}")
    public ResponseEntity<List<ResourcesDTO>> findByType(@PathVariable("type") String type){
        return new ResponseEntity(resourceService.findByType(type), HttpStatus.OK);
    }

    @GetMapping("/getResourcesTheme/{theme}")
    public ResponseEntity<List<ResourcesDTO>> findByTheme(@PathVariable("theme") String theme){
        return new ResponseEntity(resourceService.findByTheme(theme), HttpStatus.OK);
    }

    @GetMapping("/checkAvailability/{name}")
    public ResponseEntity<String> checkAvailability(@PathVariable("name") String name){
        return  new ResponseEntity(resourceService.checkAvailability(name), HttpStatus.OK);
    }

    @GetMapping("/realizeLoan/{id}/{uid}")
    public ResponseEntity<String> realizeLoan(@PathVariable("id") String id,
                                              @PathVariable("uid") String uid){
        return  new ResponseEntity(resourceService.realizeLoan(id, uid), HttpStatus.OK);
    }

    @GetMapping("/returnLoan/{id}/{uid}")
    public ResponseEntity<String> returnLoan(@PathVariable("id") String id,
                                             @PathVariable("uid") String uid){
        return new ResponseEntity(resourceService.returnLoan(id, uid), HttpStatus.OK);
    }
}
