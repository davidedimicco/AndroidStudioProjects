package it.unimi.dimicco.davide.mypersonalvocabulary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArchiveLoaderFragment extends Fragment {

    MainActivity activity;
    EditText toLoadEditText;
    Button homeBtn6;
    Button loadArchiveBtn;

    public ArchiveLoaderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_archiveloader, container, false);

        activity=(MainActivity)getActivity();

        toLoadEditText=(EditText)rootView.findViewById(R.id.toLoadEditText);

        homeBtn6=(Button)rootView.findViewById(R.id.homeBtn6);
        homeBtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Here I want to open the home fragment
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new HomeFragment());
                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        loadArchiveBtn=(Button)rootView.findViewById(R.id.loadArchiveBtn);
        loadArchiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lines[] = toLoadEditText.getText().toString().split("\\r?\\n");
                try{
                    for(String line:lines){
                        lineParser(line);
                    }
                    //Here I sort archive and categories
                    Collections.sort(activity.categories);
                    Collections.sort(activity.archive);

                    //Here I have to save archive and categories
                    activity.serializeArchive();
                    activity.serializeCategories();

                    //Here I clear the load edit text
                    toLoadEditText.setText("");

                }catch(IllegalArgumentException e){
                    Toast toast = Toast.makeText(activity, "Sorry, I'm not able to understand your text.", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        return rootView;
    }


    public void lineParser(String line) throws IllegalArgumentException{
        if(line.equals("")){
            return;
        }
        if(!line.contains("=")||!line.contains("{")||!line.contains("}")){
            throw new IllegalArgumentException();
        }

        String categoriesNames=line.substring(line.indexOf("{")+1,line.indexOf("}"));
        ArrayList<Category> cats=new ArrayList<Category>();
        if(!categoriesNames.equals("")){
            while(categoriesNames.contains(",")){
                cats.add(new Category((categoriesNames.substring(0,categoriesNames.indexOf(","))).trim()));
                categoriesNames=categoriesNames.substring(categoriesNames.indexOf(",")+1);
            }
            cats.add(new Category(categoriesNames));
        }
        for(Category cat:cats){
            if(!activity.categories.contains(cat)){
                activity.categories.add(cat);
            }
        }

        for (Entry entry:Entry.parse(line.substring(0,line.indexOf("<")).trim(),line.substring(line.indexOf(">")+1,line.indexOf("=")).trim(),line.substring(line.indexOf("=")+1,line.indexOf("{")).trim())) {
            if(!activity.archive.contains(entry)){
                activity.archive.add(entry);
            }
            //aggiungo ad ogni categoria selezionata la parola aggiunta all'archivio, e nel frattempo deseleziono la categoria
            for(Category cat:cats){
                if(!activity.categories.get(activity.categories.indexOf(cat)).containsEntry(entry)){
                    activity.categories.get(activity.categories.indexOf(cat)).addEntry(entry);
                }
            }
        }

    }

}
