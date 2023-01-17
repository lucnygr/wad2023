package at.gv.brz.wad2023.sample2_spring_data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;

@SpringBootApplication
public class Sample2Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Sample2Application.class, args);

        UserService service = ctx.getBean(UserService.class);
        User user = new User(null, "gregor", "gregor.lucny@brz.gv.at", LocalDate.now());
        service.insertUser(user);

        service.updateUser("gregor", "lucny");

        User loadedUser = service.findUserByEmail("gregor.lucny@brz.gv.at").get();
        System.out.println("The generated user-id of the new user is " + loadedUser.getId() + ". The username is " + loadedUser.getUsername());

        service.findUserMultipleTimes(user.getId());
    }
}
