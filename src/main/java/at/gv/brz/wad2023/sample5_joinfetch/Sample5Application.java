package at.gv.brz.wad2023.sample5_joinfetch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Sample5Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Sample5Application.class, args);

        UserService service = ctx.getBean(UserService.class);
        User user = new User("gregor", "gregor.lucny@brz.gv.at", LocalDate.now());
        user.getDevices().add(new Device("PUSH_TOKEN_GREGOR_1"));
        user.getDevices().add(new Device("PUSH_TOKEN_GREGOR_2"));
        user.getSearchQueries().add(new SearchQuery("Software-Engineer", LocalDateTime.now()));
        user.getSearchQueries().add(new SearchQuery("Software-Architect", LocalDateTime.now()));
        service.insertUser(user);

        user = new User("philipp", "philipp.frauenthaler@brz.gv.at", LocalDate.now());
        user.getDevices().add(new Device("PUSH_TOKEN_PHILIPP_1"));
        user.getSearchQueries().add(new SearchQuery("Software-Developer", LocalDateTime.now()));
        user.getSearchQueries().add(new SearchQuery("Software-Architect", LocalDateTime.now()));
        service.insertUser(user);

        user = new User("bob", "bob.sample@brz.gv.at", LocalDate.now());
        user.getSearchQueries().add(new SearchQuery("Software-Architect", LocalDateTime.now()));
        service.insertUser(user);

        service.notifyUsersFetchAfterwards();

        service.notifyUsersFetchImmediately();
/*
        initData(service);

        for (int i = 0; i < 10; i++) {
            performanceTest(service);
        }*/
    }

    private static void initData(UserService service) {
        int counter = 0;
        while (counter < 100000) {
            List<User> users = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                User user = new User("gregor" + ++counter, "gregor.lucny@brz.gv.at" + counter, LocalDate.now());
                user.getDevices().add(new Device("PUSH_TOKEN_GREGOR_1" + counter));
                user.getDevices().add(new Device("PUSH_TOKEN_GREGOR_2" + counter));
                user.getSearchQueries().add(new SearchQuery("Software-Engineer" + counter, LocalDateTime.now()));
                user.getSearchQueries().add(new SearchQuery("Software-Engineer" + counter, LocalDateTime.now()));
                users.add(user);
            }
            service.insertUsers(users);
            if (counter % 10000 == 0) {
                System.out.println("counter is " + counter);
            }
        }
    }

    private static void performanceTest(UserService service) {
        long testStart = System.currentTimeMillis();
        service.notifyUsersFetchImmediately();
        System.out.println("test took " + (System.currentTimeMillis() - testStart) + " millis");
    }
}
