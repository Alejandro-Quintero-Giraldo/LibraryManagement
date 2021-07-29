package co.com.library.management.service;
import co.com.library.management.dto.UserDTO;
import co.com.library.management.mapper.UserMapper;
import co.com.library.management.model.User;
import co.com.library.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    UserMapper mapper = new UserMapper();

    public UserDTO createUser(UserDTO userDTO){
        User user = mapper.toCollection(userDTO);
        return mapper.toDTO(userRepository.save(user));
    }

    public UserDTO updateUser(UserDTO userDTO){
        User user = mapper.toCollection(userDTO);
        userRepository.findById(userDTO.getId()).orElseThrow(()-> new RuntimeException("The user doesn't exists"));
        return mapper.toDTO(userRepository.save(user));
    }

    public UserDTO findById(String id){
        User user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("The user doesn't exists"));
        return mapper.toDTO(user);
    }

    public void delete(String id){
        userRepository.deleteById(id);
    }
}
