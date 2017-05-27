package net.crevion.simplerealm;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import net.crevion.simplerealm.model.Person;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class PersonActivity extends AppCompatActivity {

    private Realm realm;
    private PersonAdapter personAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);


        realm = Realm.getDefaultInstance();

        deleteAllPerson();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Person person = new Person();
                person.setName("Yusuf Aji Wibowo");
                person.setAge(24);
                addPerson(person);

                refreshListPerson();
            }
        });

        personAdapter = new PersonAdapter();

        RecyclerView recyclerViewPerson = (RecyclerView) findViewById(R.id.recycler_person);
        recyclerViewPerson.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPerson.setAdapter(personAdapter);

        refreshListPerson();
    }

    private void addPerson(final Person p) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Person person = realm.createObject(Person.class);
                person.setId(1);
                person.setName(p.getName());
                person.setAge(p.getAge());

            }
        });
    }

    private List<Person> getAllPerson(){
        return realm.where(Person.class).findAll();
    }

    private void deleteAllPerson(){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Person.class);
            }
        });
    }

    private void refreshListPerson(){
        personAdapter.updateListPerson(getAllPerson());
        personAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}