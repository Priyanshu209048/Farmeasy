package com.project.farmeasy;

import com.project.farmeasy.dao.RoleDao;
import com.project.farmeasy.entities.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FarmeasyApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(FarmeasyApplication.class, args);
    }

    RoleDao roleDao;

    @Autowired
    public FarmeasyApplication(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public void addRole() {
        roleDao.save(new Role(1, "ROLE_GOV"));
        roleDao.save(new Role(2, "ROLE_BANK"));
        roleDao.save(new Role(3, "ROLE_FARMER"));
    }

    @Override
    public void run(String... args) throws Exception {
       addRole();
    }
}
