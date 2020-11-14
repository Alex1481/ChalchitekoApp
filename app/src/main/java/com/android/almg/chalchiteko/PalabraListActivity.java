package com.android.almg.chalchiteko;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.android.almg.chalchiteko.dummy.DummyContent;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * An activity representing a list of Palabras. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link PalabraDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class PalabraListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String PATH_WORD = "word";

    @BindView(R.id.etSpanish)
    EditText etSpanish;
    @BindView(R.id.etWord)
    EditText etWord;
    @BindView(R.id.btnSave)
    Button btnSave;
    @BindView(R.id.spSpanish)
    Spinner spSpanish;
    @BindView(R.id.btnRefreshSpinner)
    Button btnRefreshSpinner;

    List<DummyContent.Palabra> palabras;
    DummyContent.Palabra palabraUpdate;
    ArrayAdapter<String> aaPalabras;


    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palabra_list);
        ButterKnife.bind(this);

        //Codigo para icono en el Action Bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (findViewById(R.id.palabra_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        configSpinner();


        View recyclerView = findViewById(R.id.palabra_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void configSpinner() {
        spSpanish.setOnItemSelectedListener(this);

        aaPalabras = new ArrayAdapter<>(this, R.layout.spinner_item_spanish);
        aaPalabras.setDropDownViewResource(R.layout.spinner_item_spanish);

        spSpanish.setAdapter(aaPalabras);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        palabraUpdate = palabras.get(position);
        etSpanish.setText(palabraUpdate.getSpanish());
        etWord.setText(palabraUpdate.getChalchiteko());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, mTwoPane));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(PATH_WORD);

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DummyContent.Palabra palabra = dataSnapshot.getValue(DummyContent.Palabra.class);
                palabra.setId(dataSnapshot.getKey());

                if (!DummyContent.ITEMS.contains(palabra)) {
                    DummyContent.addItem(palabra);
                }

                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                DummyContent.Palabra palabra = dataSnapshot.getValue(DummyContent.Palabra.class);
                palabra.setId(dataSnapshot.getKey());

                if (DummyContent.ITEMS.contains(palabra)) {
                    DummyContent.updateItem(palabra);
                }

                recyclerView.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                DummyContent.Palabra palabra = dataSnapshot.getValue(DummyContent.Palabra.class);
                palabra.setId(dataSnapshot.getKey());

                if (DummyContent.ITEMS.contains(palabra)) {
                    DummyContent.deleteItem(palabra);
                }

                recyclerView.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(PalabraListActivity.this, "Moved", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PalabraListActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.btnSave)
    public void onViewClicked() {
        DummyContent.Palabra palabra = new DummyContent.Palabra(etSpanish.getText().toString().trim(),
                etWord.getText().toString().trim());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(PATH_WORD);

        DummyContent.Palabra palabraUpdate = DummyContent.getPalabra(palabra.getSpanish());
        if (palabraUpdate != null) {
            reference.child(palabraUpdate.getId()).setValue(palabra);
        } else {
            reference.push().setValue(palabra);
        }

        etSpanish.setText("");
        etWord.setText("");
    }

    @OnClick(R.id.btnRefreshSpinner)
    public void onRefreshSpinnerClicked() {
        palabras = new ArrayList<>();
        aaPalabras.clear();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(PATH_WORD);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    DummyContent.Palabra palabra = snapshot.getValue(DummyContent.Palabra.class);
                    palabra.setId(dataSnapshot.getKey());
                    palabras.add(palabra);
                    aaPalabras.add(palabra.getSpanish());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PalabraListActivity.this, "Error al consultar la base de datos",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final PalabraListActivity mParentActivity;
        private final List<DummyContent.Palabra> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DummyContent.Palabra item = (DummyContent.Palabra) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(PalabraDetailFragment.ARG_ITEM_ID, item.getId());
                    PalabraDetailFragment fragment = new PalabraDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.palabra_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, PalabraDetailActivity.class);
                    intent.putExtra(PalabraDetailFragment.ARG_ITEM_ID, item.getId());

                    context.startActivity(intent);
                }
            }
        };


        SimpleItemRecyclerViewAdapter(PalabraListActivity parent,
                                      List<DummyContent.Palabra> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.palabra_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mContentView.setText(mValues.get(position).getSpanish());
            holder.mIdView.setText(mValues.get(position).getChalchiteko());

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);

            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference(PATH_WORD);
                    reference.child(mValues.get(position).getId()).removeValue();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;
            @BindView(R.id.btnDelete)
            Button btnDelete;

            ViewHolder(View view) {
                super(view);

                ButterKnife.bind(this, view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }

    //URL con conexion a API de WhatsApp
    public void irMessage (View view){
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.maestrosdelweb.com/que-son-las-bases-de-datos/"));
    }
}