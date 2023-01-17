package at.gv.brz.wad2023.sample1_jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;

@SpringBootApplication
public class Sample1Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Sample1Application.class, args);

        UserService service = ctx.getBean(UserService.class);
        User user = new User(null, "gregor", "gregor.lucny@brz.gv.at", LocalDate.now());
        service.insertUser(user);

        User loadedUser = service.findUserById(user.getId()).get();
        System.out.println("The generated user-id of the new user is " + loadedUser.getId());
    }
}
