package model;

import model.entity.Actor;
import model.repository.DBActorRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class ActorService {

    @Inject
    private DBActorRepository dbActorRepository;

    public void add(Actor actor) {
        dbActorRepository.add(actor);
    }

    public List<Actor> getAll() {
        return dbActorRepository.getAll();
    }

    public void update(String ID, Actor new_actor) {
        dbActorRepository.update(ID, new_actor);
    }

    public void delete(String ID) {
        dbActorRepository.delete(ID);
    }

    public List<Actor> findByName(String name) {
        List<Actor> filteredActors = new ArrayList<>();
        for (Actor actor: dbActorRepository.getAll()) {
            if (name.toLowerCase().equals(actor.getHuman().getName().toLowerCase())) {
                filteredActors.add(actor);
            }
        }
        return filteredActors;
    }

    public List<Actor> findBySurname(String surname) {
        List<Actor> filteredActors = new ArrayList<>();
        for (Actor actor: dbActorRepository.getAll()) {
            if (surname.toLowerCase().equals(actor.getHuman().getSurname().toLowerCase())) {
                filteredActors.add(actor);
            }
        }
        return filteredActors;
    }

    public List<Actor> findByName(String name, String surname) {
        List<Actor> filteredActors = new ArrayList<>();
        for (Actor actor: dbActorRepository.getAll()) {
            if (name.toLowerCase().equals(actor.getHuman().getName().toLowerCase()) & surname.toLowerCase().equals(actor.getHuman().getSurname().toLowerCase())) {
                filteredActors.add(actor);
            }
        }
        return filteredActors;
    }


}
