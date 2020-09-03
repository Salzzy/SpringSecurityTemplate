package de.format.massenimport.service;

import de.format.massenimport.entity.Role;
import de.format.massenimport.entity.User;
import de.format.massenimport.entity.UserInfo;
import de.format.massenimport.repository.RoleRepository;
import de.format.massenimport.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;

    }



    @Override
    public void save(User user) {
        System.out.println("Tessst");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        // USER Rolle in das HashSet und den User speichern
        HashSet<Role> roles = new HashSet<Role>();
        Optional<Role> role = roleRepository.findById(1l);
        if(role.isPresent()) {
            roles.add(role.get());
        }

        user.setUserInfo(new UserInfo());

        user.setRoles(roles);
        userRepository.save(user);
    }



    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }



    @Override
    public void saveUserInfo(User user, UserInfo userinfo) {

        User userAktuell = userRepository.findByUsername(user.getUsername());

        UserInfo aktuell = userAktuell.getUserInfo();
        UserInfo neu = userinfo;

        System.out.println(aktuell.getTelefonnr());
        System.out.println(neu.getTelefonnr());

        // Prüfe userDetails ab
        // Signatur muss getrennt von Daten gespeichert werden
        if(aktuell.getAbteilung() != neu.getAbteilung() && (neu.getAbteilung() != null || !neu.getAbteilung().isEmpty())) {
            aktuell.setAbteilung(neu.getAbteilung());
        }
        if(aktuell.getEmail() != neu.getEmail() && (neu.getEmail() != null || !neu.getEmail().isEmpty())) {
            aktuell.setEmail(neu.getEmail());
        }
        if(aktuell.getTelefonnr() != neu.getTelefonnr() && (neu.getTelefonnr() != null || !neu.getTelefonnr().isEmpty())) {
            aktuell.setTelefonnr(neu.getTelefonnr());
        }

        userAktuell.setUserInfo(aktuell);
        userRepository.save(userAktuell);


    }



    @Override
    public void saveNormal(User user) {
        userRepository.save(user);
    }



    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

}
