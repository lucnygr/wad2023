package at.gv.brz.wad2023.sample7_rollback_actions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class Sample7Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Sample7Application.class, args);

        UserService service = ctx.getBean(UserService.class);
        User user = new User("gregor", "gregor.lucny@brz.gv.at", LocalDate.now());
        user.getSearchQueries().add(new SearchQuery("Software-Engineer", LocalDateTime.now()));
        service.insertUser(user);

        service.addDevice(user.getId(), "NEW_PUSH_TOKEN");

        int nrOfDevices = service.findUserById(user.getId()).get().getDevices().size();
        System.out.println("User has " + nrOfDevices + " devices");

        service.addDeviceUseRollbackManager(user.getId(), "NEW_PUSH_TOKEN_ROLLBACK_MANAGER");
        nrOfDevices = service.findUserById(user.getId()).get().getDevices().size();
        System.out.println("User has " + nrOfDevices + " devices");
    }
}
