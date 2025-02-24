package com.project.farmeasy;

import com.project.farmeasy.dao.BankDao;
import com.project.farmeasy.dao.RoleDao;
import com.project.farmeasy.dao.UserDao;
import com.project.farmeasy.entities.Bank;
import com.project.farmeasy.entities.Role;
import com.project.farmeasy.entities.User;
import com.project.farmeasy.services.BankService;
import com.project.farmeasy.services.impl.BankServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class FarmeasyApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(FarmeasyApplication.class, args);
    }

    RoleDao roleDao;
    UserDao userDao;
    BankDao bankDao;
    PasswordEncoder passwordEncoder;
    BankService bankService;

    @Autowired
    public FarmeasyApplication(RoleDao roleDao, UserDao userDao, BankDao bankDao, PasswordEncoder passwordEncoder, BankService bankService) {
        this.roleDao = roleDao;
        this.userDao = userDao;
        this.bankDao = bankDao;
        this.passwordEncoder = passwordEncoder;
        this.bankService = bankService;
    }

    public void addRole() {
        roleDao.save(new Role(1, "ROLE_GOV"));
        roleDao.save(new Role(2, "ROLE_BANK"));
        roleDao.save(new Role(3, "ROLE_FARMER"));
    }

    public void addStaticBanks() {
        if (bankDao.count() == 0) { // Prevent duplicate banks
            Bank bank1 = new Bank();
            bank1.setBankName("State Bank of India");
            bank1.setBankAddress("Connaught Place");
            bank1.setBankCity("Delhi");
            bank1.setBankState("Delhi");
            bank1.setBankZip("110001");
            bank1.setEmail("sbi@bank.com");
            bank1.setBankPhone("9876543210");

            Bank bank2 = new Bank();
            bank2.setBankName("Bank of Baroda");
            bank2.setBankAddress("Khajuri Khas");
            bank2.setBankCity("Delhi");
            bank2.setBankState("Delhi");
            bank2.setBankZip("110095");
            bank2.setEmail("bob@bank.com");
            bank2.setBankPhone("6359874589");

            bankDao.saveAll(List.of(bank1, bank2));
            List<Bank> savedBanks = bankDao.findAll();
            System.out.println("Banks found: " + savedBanks.size());

            for (Bank bank : savedBanks) {
                User bankUser = new User();
                bankUser.setEmail(bank.getEmail());
                bankUser.setPassword(passwordEncoder.encode("12345"));
                bankUser.setRole("ROLE_BANK");

                System.out.println("Saving user: " + bankUser.getEmail());
                userDao.save(bankUser);
            }
        }
    }

    /*public void addGov() {
        User govUser = new User();
        govUser.setEmail("central@gov.com");
        govUser.setPassword(passwordEncoder.encode("12345"));
        govUser.setRole("ROLE_GOV");
        userDao.save(govUser);
    }*/

    @Override
    public void run(String... args) throws Exception {
       addRole();
       addStaticBanks();
       //addGov();
    }
}
