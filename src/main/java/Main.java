import model.entity.Actor;
import model.entity.Human;
import model.repository.DBActorRepository;

public class Main {

    public static void main(String[] args) {
        DBActorRepository dbActorRepository = new DBActorRepository();
        Human human = new Human("Jan","Kowalski", "123", "Czech");
        System.out.println(new Actor(human));
//        dbActorRepository.add(new Actor(human));
//        dbActorRepository.getAll();

    }
}
