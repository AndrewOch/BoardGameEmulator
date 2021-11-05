package ru.kpfu.itis.listeners;

import ru.kpfu.itis.repositories.*;
import ru.kpfu.itis.services.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ComponentsListener implements ServletContextListener {

    private final String URL = "jdbc:postgresql://localhost:5432/board-game-emulator-db";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "12345678";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            UsersRepository usersRepository = new UsersRepositoryImpl(connection);
            AuthRepository authRepository = new AuthRepositoryImpl(connection);
            UsersService usersService = new UsersServicesImpl(usersRepository, authRepository);
            servletContextEvent.getServletContext().setAttribute("usersService", usersService);


            GamesRepository gamesRepository = new GamesRepositoryImpl(connection);
            CurrencyRepository currencyRepository = new CurrencyRepositoryImpl(connection);
            DecksRepository decksRepository = new DecksRepositoryImpl(connection);
            CardsRepository cardsRepository = new CardsRepositoryImpl(connection);
            GamesService gamesService = new GamesServiceImpl(gamesRepository, decksRepository, cardsRepository, currencyRepository);
            servletContextEvent.getServletContext().setAttribute("gamesService", gamesService);

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Unavailable");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
