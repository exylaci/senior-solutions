package meetingrooms;

import org.mariadb.jdbc.MariaDbDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@PropertySource("classpath:meetingroom.properties")
public class MariaDbMeetingRoomsRepository implements MeetingRoomsRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Environment environment;

    public MariaDbMeetingRoomsRepository() {
        MariaDbDataSource dataSource = new MariaDbDataSource();
        try {
//            dataSource.setUrl(environment.getProperty("jdbc.url"));
//            dataSource.setUser(environment.getProperty("jdbc.username"));
//            dataSource.setPassword(environment.getProperty("jdbc.password"));
            dataSource.setUrl("jdbc:mariadb://localhost:3306/employees?useUnicode=true");
            dataSource.setUser("employees");
            dataSource.setPassword("employees");

        } catch (NullPointerException | SQLException e) {
            System.out.println("Cannot connect to the database!" + e);
        }

        jdbcTemplate = new JdbcTemplate(dataSource);
        init();
    }

    void init() {
        jdbcTemplate.update(
                """
                        CREATE TABLE if NOT EXISTS meetingrooms(
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        room_name VARCHAR(50),
                        room_width INT,
                        room_length INT);
                        """
        );
    }

    public void deleteAll() {
        jdbcTemplate.update("TRUNCATE meetingrooms;");
    }

    @Override
    public void newMeetingRoom(MeetingRoom meetingRoom) {
        jdbcTemplate.update(
                "INSERT INTO meetingrooms(room_name,room_width,room_length) VALUE(?,?,?)",
                meetingRoom.getName(), meetingRoom.getWidth(), meetingRoom.getLength());
    }

    @Override
    public List<String> orderByName() {
        return jdbcTemplate.query(
                "SELECT room_name FROM meetingrooms ORDER BY room_name",
                (resultSet, index) -> resultSet.getString(1));
    }

    @Override
    public List<String> reverseOrder() {
        return jdbcTemplate.query(
                "SELECT room_name FROM meetingrooms ORDER BY room_name DESC",
                (resultSet, index) -> resultSet.getString(1));
    }

    @Override
    public List<MeetingRoom> sizes() {
        return jdbcTemplate.query(
                "SELECT room_name,room_width,room_length,room_width*room_length AS size FROM meetingrooms ORDER BY size DESC",
                (resultSet, index) -> new MeetingRoom(
                        resultSet.getString(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3)));
    }

    @Override
    public Optional<MeetingRoom> findByExactName(String name) {
        return jdbcTemplate.query(
                "SELECT room_name,room_width,room_length FROM meetingrooms WHERE room_name=?",
                (resultSet, index) -> new MeetingRoom(
                        resultSet.getString(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3)),
                name)
                .stream()
                .findAny();
    }

    @Override
    public List<MeetingRoom> findByNamePart(String namePart) {
        return jdbcTemplate.query(
                "SELECT room_name,room_width,room_length FROM meetingrooms WHERE LOWER(room_name) LIKE ? ORDER BY room_name",
                (resultSet, index) -> new MeetingRoom(
                        resultSet.getString(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3)),
                '%' + namePart.toLowerCase() + '%');
    }

    @Override
    public List<MeetingRoom> findBySize(int size) {
        return jdbcTemplate.query(
                "SELECT room_name,room_width,room_length FROM meetingrooms WHERE room_width*room_length > ?",
                (resultSet, index) -> new MeetingRoom(
                        resultSet.getString(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3)),
                size);
    }
}
