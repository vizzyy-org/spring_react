package vizzyy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vizzyy.domain.User;
import vizzyy.domain.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User createUser(String CN, String role){
        int size = getUsers().size() + 1;
        User newUser = new User((long) size, CN, role);
        return userRepository.save(newUser);
    }

    public void deleteUser(String CN){


        User user = getUser(CN);
        if(user != null && !user.getRole().equals("owner"))
            userRepository.delete(user);
        else
            throw new RuntimeException("Could not delete applicant.");
    }

    public User getUser(String CN){
        List<User> results = userRepository.findByCommonName(CN);
        return results.size() > 0 ? results.get(0) : null;
    }

}
