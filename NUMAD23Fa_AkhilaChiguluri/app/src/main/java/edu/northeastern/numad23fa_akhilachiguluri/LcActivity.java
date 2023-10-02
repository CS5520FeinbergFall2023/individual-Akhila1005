package edu.northeastern.numad23fa_akhilachiguluri;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class LcActivity extends AppCompatActivity {
    private ArrayList<LcItem> linkCollectorUnitList;
    private AlertDialog inputDialogAlert;
    private EditText inputLinkName;
    private RecyclerView recyclerView;
    private LcAdapter lcAdapter;
    private EditText inputLinkUrl;
    private static final String KEY_LINK_ITEM_LIST = "link_unit_list";
    private static final String KEY_ADD_BUTTON_CLICKED = "add_button_clicked";
    private boolean addButtonClicked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lc);
        FloatingActionButton addLinkButton = findViewById(R.id.floatingButton);

        if (savedInstanceState != null) {
            addButtonClicked = savedInstanceState.getBoolean(KEY_ADD_BUTTON_CLICKED, false);
        }

        addLinkButton.setOnClickListener(v -> {
            addButtonClicked = true;
            showInput();
        });

        linkCollectorUnitList = new ArrayList<>();
        createInputAlertDialog();
        createRecyclerView();
        lcAdapter.setOnItemClickListener(position -> linkCollectorUnitList.get(position).onLinkUnitClicked(this));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getLayoutPosition();
                linkCollectorUnitList.remove(position);

            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);

        if (savedInstanceState != null) {
            linkCollectorUnitList = savedInstanceState.getParcelableArrayList(KEY_LINK_ITEM_LIST);
            lcAdapter.setDataList(linkCollectorUnitList);
        }

        lcAdapter.setOnItemLongClickListener(this::showEditDialog);
    }

    public void createInputAlertDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.activity_lc_input, null);

        inputLinkName = view.findViewById(R.id.link_name_input);
        inputLinkUrl = view.findViewById(R.id.link_url_input);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(view);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(getString(R.string.Add),
                        (dialog, id) -> {
                            LcItem linkCollectorUnit = new LcItem(inputLinkName.getText().toString(), inputLinkUrl.getText().toString());
                            if (linkCollectorUnit.isValid()) {
                                linkCollectorUnitList.add(0, linkCollectorUnit);
                                Snackbar.make(recyclerView, getString(R.string.link_add_success), Snackbar.LENGTH_LONG).show();
                            } else {
                                Snackbar.make(recyclerView, getString(R.string.link_invalid), Snackbar.LENGTH_LONG).show();
                            }
                        })
                .setNegativeButton(getString(R.string.Cancel),
                        (dialog, id) -> dialog.cancel());

        inputDialogAlert = alertDialogBuilder.create();
    }

    public void createRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        lcAdapter = new LcAdapter(this, linkCollectorUnitList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(lcAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void showInput() {
        LcInput inputFragment = new LcInput();
        inputFragment.show(getSupportFragmentManager(), "input_dialog");
    }

    public void addLink(LcItem linkCollectorUnit) {
        linkCollectorUnitList.add(0, linkCollectorUnit);
        lcAdapter.notifyDataSetChanged();
        showSnackbar(getString(R.string.link_add_success));
    }

    public void showSnackbar(String message) {
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_LONG).show();
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_LINK_ITEM_LIST, linkCollectorUnitList);
        outState.putBoolean(KEY_ADD_BUTTON_CLICKED, addButtonClicked);
    }

    private void showEditDialog(int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.activity_lc_input, null);
        EditText editLinkNameInput = view.findViewById(R.id.link_name_input);
        EditText editLinkUrlInput = view.findViewById(R.id.link_url_input);
        LcItem currentLink = linkCollectorUnitList.get(position);
        editLinkNameInput.setText(currentLink.getItemName());
        editLinkUrlInput.setText(currentLink.getItemUrl());

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(view);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(getString(R.string.Save),
                        (dialog, id) -> {
                            String updatedName = editLinkNameInput.getText().toString();
                            String updatedUrl = editLinkUrlInput.getText().toString();
                            if (updatedName.isEmpty() || updatedUrl.isEmpty() ||  !validLink(updatedUrl)) {
                                Snackbar.make(recyclerView, getString(R.string.link_invalid), Snackbar.LENGTH_LONG).show();
                            } else {
                                LcItem updatedLinkCollectorUnit = new LcItem(updatedName, updatedUrl);
                                linkCollectorUnitList.set(position, updatedLinkCollectorUnit);
                                lcAdapter.notifyDataSetChanged();
                                Snackbar.make(recyclerView, getString(R.string.link_updated_success), Snackbar.LENGTH_LONG).show();
                            }
                        })
                .setNegativeButton(getString(R.string.Cancel),
                        (dialog, id) -> dialog.cancel());

        AlertDialog editAlertDialog = alertDialogBuilder.create();
        editAlertDialog.show();
    }

    private boolean validLink(String linkUrl) {
        try {
            new URL(linkUrl).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
        return Patterns.WEB_URL.matcher(linkUrl).matches();
    }
}
