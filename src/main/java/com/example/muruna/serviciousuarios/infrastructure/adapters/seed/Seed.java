package com.example.muruna.serviciousuarios.infrastructure.adapters.seed;

import com.example.muruna.serviciousuarios.infrastructure.adapters.output.entities.Phone;
import com.example.muruna.serviciousuarios.infrastructure.adapters.output.entities.User;
import com.example.muruna.serviciousuarios.infrastructure.adapters.output.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Seed {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    private void createSeed(){
        for(int i=1; i <= 3; i++) {
            User user = new User();
            user.setId(UUID.randomUUID());
            user.setCreated(new Date());
            user.setName("usuario-"+i);
            user.setEmail("email"+i+"@ejemplo.com");
            user.setPassword("12345");
            user.setActive(true);

            List<Phone> phoneList = new ArrayList<>();
            Phone phone = new Phone();
            phone.setId(UUID.randomUUID());
            phone.setNumber(i+"-1-9876543210");
            phone.setCityCode("101");
            phone.setCountryCode("57");

            phoneList.add(phone);

            if(i==1) {
                Phone phone2 = new Phone();
                phone2.setId(UUID.randomUUID());
                phone2.setNumber(i + "-2-9876543210");
                phone2.setCityCode("101");
                phone2.setCountryCode("57");
                phoneList.add(phone2);
            }

            user.setPhones(phoneList);

            userRepository.save(user);
        }
    }
}
