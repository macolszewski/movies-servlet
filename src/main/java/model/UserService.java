package model;

import com.google.common.hash.Hashing;
import model.entity.Actor;
import model.entity.User;
import model.repository.DBUserRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class UserService {

    @Inject
    private DBUserRepository dbUserRepository;

    public void add(User user) {
        dbUserRepository.add(user);
    }

    public List<User> getAll() {
        return dbUserRepository.getAll();
    }

    public void update(String ID, User new_user) {
        dbUserRepository.update(ID, new_user);
    }

    public void delete(String ID) {
        dbUserRepository.delete(ID);
    }


    public User getUserByToken(String token) {
        List<User> filteredActors = getAll();
        for (User user: dbUserRepository.getAll()) {
            String user_hash = Hashing.sha512()
                    .hashString((user.getLogin()+user.getPassword()), StandardCharsets.UTF_8)
                    .toString();
            if(user_hash.equals(token)) {
                return user;
            }
        }
        return null;
    }




}
