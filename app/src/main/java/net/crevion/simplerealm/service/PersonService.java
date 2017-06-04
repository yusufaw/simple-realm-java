package net.crevion.simplerealm.service;

import net.crevion.simplerealm.model.Person;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by yusuf on 6/5/17.
 */

public class PersonService {
    private Realm realmPersonA, realmPersonB, currentRealm;

    public PersonService() {
        RealmConfiguration personAConfig = new RealmConfiguration.Builder()
                .name("personA.currentRealm")
                .schemaVersion(1)
                .build();

        RealmConfiguration personBConfig = new RealmConfiguration.Builder()
                .name("personB.currentRealm")
                .schemaVersion(1)
                .build();
    }

    public Realm getRealmPersonA() {
        return realmPersonA;
    }

    public void setRealmPersonA(Realm realmPersonA) {
        this.realmPersonA = realmPersonA;
    }

    public Realm getRealmPersonB() {
        return realmPersonB;
    }

    public void setRealmPersonB(Realm realmPersonB) {
        this.realmPersonB = realmPersonB;
    }

    public Realm getCurrentRealm() {
        return currentRealm;
    }

    public void setCurrentRealm(Realm currentRealm) {
        this.currentRealm = currentRealm;
    }

    public void addPerson(final Person p) {
        currentRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Person person = realm.createObject(Person.class);
                person.setId(1);
                person.setName(p.getName());
                person.setAge(p.getAge());

            }
        });
    }

    public List<Person> getAllPerson(){
        return currentRealm.where(Person.class).findAll();
    }

    public void deleteAllPerson(){
        currentRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Person.class);
            }
        });
    }

    public void close() {
        currentRealm.close();
    }
}
