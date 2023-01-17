package at.gv.brz.wad2023.sample4_relations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class Sample4Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Sample4Application.class, args);

        UserService service = ctx.getBean(UserService.class);
        User user = new User("gregor", "gregor.lucny@brz.gv.at", LocalDate.now());
        user.getDevices().add(new Device("PUSH_TOKEN_GREGOR_1"));
        user.getDevices().add(new Device("PUSH_TOKEN_GREGOR_2"));
        user.getSearchQueries().add(new SearchQuery("Software-Engineer", LocalDateTime.now()));
        service.insertUser(user);

        user = new User("philipp", "philipp.frauenthaler@brz.gv.at", LocalDate.now());
        user.getDevices().add(new Device("PUSH_TOKEN_PHILIPP_1"));
        user.getSearchQueries().add(new SearchQuery("Software-Developer", LocalDateTime.now()));
        user.getSearchQueries().add(new SearchQuery("Software-Architect", LocalDateTime.now()));
        service.insertUser(user);

        service.notifyUsers();
    }
}
