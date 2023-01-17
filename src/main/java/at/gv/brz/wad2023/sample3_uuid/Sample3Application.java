package at.gv.brz.wad2023.sample3_uuid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.util.UUID;

@SpringBootApplication
public class Sample3Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Sample3Application.class, args);

        UserService service = ctx.getBean(UserService.class);
        User user = new User(UUID.randomUUID(), "gregor", "gregor.lucny@brz.gv.at", LocalDate.now());
        System.out.println("The generated user-id of the new user is " + user.getId());
        service.insertUser(user);
        System.out.println("The generated user-id of the new user is " + user.getId());

        User loadedUser = service.findUserById(user.getId()).get();
        System.out.println("The generated user-id of the new user is " + loadedUser.getId());
    }
}
