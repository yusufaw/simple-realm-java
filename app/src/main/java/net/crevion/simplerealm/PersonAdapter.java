package net.crevion.simplerealm;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.crevion.simplerealm.model.Person;

import java.util.List;

/**
 * Created by yusuf on 5/27/17.
 */

class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {

    private static final String TAG = "xxx";
    private List<Person> listPerson;

    @Override
    public PersonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item, null);

        return (new PersonAdapter.ViewHolder(view));
    }

    @Override
    public void onBindViewHolder(PersonAdapter.ViewHolder holder, int position) {
        holder.textViewPersonName.setText(listPerson.get(position).getName());
        holder.textViewPersonAge.setText(Integer.toString(listPerson.get(position).getAge()));
    }

    @Override
    public int getItemCount() {
        return listPerson.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewPersonName;
        TextView textViewPersonAge;

        ViewHolder(View itemView) {
            super(itemView);
            textViewPersonName = (TextView) itemView.findViewById(R.id.text_person_name);
            textViewPersonAge= (TextView) itemView.findViewById(R.id.text_person_age);
        }
    }

    PersonAdapter(List<Person> listPerson){
        Log.d(TAG, "PersonAdapter: ");
        this.listPerson = listPerson;
    }

    PersonAdapter(){

    }

    void updateListPerson(List<Person> listPerson){
        this.listPerson = listPerson;
    }
}
