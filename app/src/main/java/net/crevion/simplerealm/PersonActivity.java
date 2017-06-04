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
import net.crevion.simplerealm.service.PersonService;

public class PersonActivity extends AppCompatActivity {

    private PersonAdapter personAdapter;
    private PersonService personService;
    private TextView textViewEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        textViewEmpty = (TextView) findViewById(R.id.empty);

        personService = new PersonService();

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
                                personService.addPerson(person);
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

    private void refreshListPerson(){
        textViewEmpty.setVisibility(personService.getAllPerson().size() > 0 ? View.GONE : View.VISIBLE);
        personAdapter.updateListPerson(personService.getAllPerson());
        personAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        personService.close();
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
            personService.setCurrentRealm(personService.getRealmPersonA());
            refreshListPerson();
            return true;
        } else if (id == R.id.change_list_b) {
            personService.setCurrentRealm(personService.getRealmPersonB());
            refreshListPerson();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}