package com.expastudios.blogweb.services;

import com.expastudios.blogweb.Util.PasswordEncryptor;
import com.expastudios.blogweb.Util.Zone;
import com.expastudios.blogweb.entity.Role;
import com.expastudios.blogweb.entity.User;
import com.expastudios.blogweb.model.ProfileDTO;
import com.expastudios.blogweb.model.RegisterDTO;
import com.expastudios.blogweb.repository.RoleRepository;
import com.expastudios.blogweb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Override
    public ResponseEntity<ProfileDTO> getUser(String email) {
    
        Optional<User> user       = userRepository.findByEmail(email);
        ProfileDTO     profileDTO = new ProfileDTO();
        profileDTO.setFulname(String.join(" ", user.get().getFirst_name(), user.get().getLast_name()));
        profileDTO.setEmail(user.get().getEmail());
        profileDTO.setGender(user.get().getGender());
        profileDTO.setProfileImage(user.get().getProfile_image());
        return new ResponseEntity<ProfileDTO>(profileDTO, HttpStatus.OK);
    }
    
    @Override
    public ResponseEntity<User> saveUser(
      RegisterDTO register, HttpServletRequest request, HttpServletResponse response
                                        ) throws InterruptedException {
        
        User user = new User();
        
        Thread registerThread = new Thread(() -> {
            user.setEmail(register.getEmail());
            user.setPassword_hash(PasswordEncryptor.Encrypt(register.getPassword()));
            user.setFirst_name(register.getFirstName());
            user.setLast_name(register.getLastName());
            user.setGender(register.getGender());
            user.setProfile_image(register.getProfileImage());
            user.setRegistered_at(Zone.getCurrentTime());
            userRepository.save(user);
        });
        registerThread.start();
        registerThread.join();
        if(registerThread.getState() == Thread.State.TERMINATED) {
            addRoleToUser(register.getEmail(), register.getRoleName());
        }
        
        return new ResponseEntity<User>(user, HttpStatus.CREATED).ok().build();
    }
    
    @Override
    public ResponseEntity<Role> getRole(String roleName) {
        
        Optional<Role> role = roleRepository.findOneByRoleName(roleName);
        return new ResponseEntity<Role>(role.get(), HttpStatus.OK);
    }
    
    @Override
    public ResponseEntity<Role> saveRole(String name) {
        
        Role roleEntity = new Role();
        roleEntity.setRoleName(name);
        roleRepository.save(roleEntity);
        return new ResponseEntity<Role>(roleEntity, HttpStatus.CREATED);
    }
    
    @Override
    public ResponseEntity<Boolean> addRoleToUser(String email, String roleName) {
        
        Optional<User> user       = userRepository.findByEmail(email);
        Optional<Role> roleEntity = roleRepository.findOneByRoleName(roleName).stream().findFirst();
        
        roleEntity.get().getUsers().add(user.get());
        user.get().getRoles().add(roleEntity.get());
        
        userRepository.save(user.get());
        roleRepository.save(roleEntity.get());
        
        log.info("New Role {} added to {}!", roleName, email);
        return new ResponseEntity<Boolean>(HttpStatus.CREATED);
    }
    
    @Override
    public ResponseEntity<Boolean> removeRoleFromUser(String email, String roleName) {
        
        Optional<User> user       = userRepository.findByEmail(email);
        Optional<Role> roleEntity = roleRepository.findOneByRoleName(roleName).stream().findFirst();
        
        roleEntity.get().getUsers().remove(user.get());
        user.get().getRoles().remove(roleEntity.get());
        
        userRepository.save(user.get());
        roleRepository.save(roleEntity.get());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
}
