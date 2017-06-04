package net.crevion.simplerealm;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import net.crevion.simplerealm.model.Person;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class PersonActivity extends AppCompatActivity {

    private Realm realmPersonA, realmPersonB, currentRealm;
    private PersonAdapter personAdapter;

    private TextView textViewEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        textViewEmpty = (TextView) findViewById(R.id.empty);
        
        RealmConfiguration personAConfig = new RealmConfiguration.Builder()
                .name("personA.currentRealm")
                .schemaVersion(1)
                .build();

        RealmConfiguration personBConfig = new RealmConfiguration.Builder()
                .name("personB.currentRealm")
                .schemaVersion(1)
                .build();

        realmPersonA = Realm.getInstance(personAConfig);
        realmPersonB = Realm.getInstance(personBConfig);
        currentRealm = realmPersonA;


//        deleteAllPerson();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PersonActivity.this);
                LayoutInflater inflater = LayoutInflater.from(getBaseContext());

                final View viewDialog = inflater.inflate(R.layout.form_input, null);
                builder.setView(viewDialog)
                        .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Person person = new Person();
                                person.setName(((EditText)viewDialog.findViewById(R.id.name)).getText().toString());
                                person.setAge(Integer.parseInt(((EditText)viewDialog.findViewById(R.id.age)).getText().toString()));
                                addPerson(person);
                                refreshListPerson();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        personAdapter = new PersonAdapter();

        RecyclerView recyclerViewPerson = (RecyclerView) findViewById(R.id.recycler_person);
        recyclerViewPerson.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPerson.setAdapter(personAdapter);

        refreshListPerson();
    }

    private void addPerson(final Person p) {
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

    private List<Person> getAllPerson(){
        return currentRealm.where(Person.class).findAll();
    }

    private void deleteAllPerson(){
        currentRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Person.class);
            }
        });
    }

    private void refreshListPerson(){
        textViewEmpty.setVisibility(getAllPerson().size() > 0 ? View.GONE : View.VISIBLE);
        personAdapter.updateListPerson(getAllPerson());
        personAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        currentRealm.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.change_list_a) {
            currentRealm = realmPersonA;
            refreshListPerson();
            return true;
        } else if (id == R.id.change_list_b) {
            currentRealm = realmPersonB;
            refreshListPerson();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}