package hawk.service;

import hawk.entity.User;
import hawk.form.Search;
import hawk.repos.UserRepo;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UserSearchService {

    private static final Logger LOGGER = Logger.getLogger(UserSearchService.class.getName());

    @Autowired
    public
    EntityManager entityManager;

    public List<User> search(Search search) {
        final Session session = entityManager.unwrap(Session.class);
        return session.doReturningWork(connection -> {
            List<User> users1 = new ArrayList<>();

            String query = "select id, name, description from ITEM where description like ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + search.getSearchText() + "%");
            LOGGER.log(Level.INFO, "SQL Query {0}",  statement);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                users1.add(new User(rs.getLong("id"), rs.getString("name"), rs.getString("description"), rs.getString("tenant_id")));
            }
            rs.close();
            return users1;
        });
    }
}
