package com.example.tugasreadwrite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnNew;
    Button btnOpen;
    Button btnSave;
    Button btnCalculate;
    EditText editWeight, editHeight;
    EditText editTitle, editContent;
    TextView result;
    File path;
    private float hasil, weight, height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnNew = (Button) findViewById(R.id.button_new);
        btnOpen = (Button) findViewById(R.id.button_open);
        btnSave = (Button) findViewById(R.id.button_save);
        btnCalculate = (Button) findViewById(R.id.btnCalculate);
        editWeight = (EditText) findViewById(R.id.weight);
        editHeight = (EditText) findViewById(R.id.height);
        result = (TextView) findViewById(R.id.hasilnya);
        editTitle = (EditText) findViewById(R.id.edit_title);
        editContent = (EditText) findViewById(R.id.inputText);

        btnCalculate.setOnClickListener(this);
        btnNew.setOnClickListener(this);
        btnOpen.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        path = getFilesDir();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.button_new:
                newFile();
                break;
            case R.id.button_open:
                openFile();
                break;
            case R.id.button_save:
                saveFile();
                break;
            case R.id.btnCalculate:
                hasilAkhir(editWeight,editHeight);
                break;
        }
    }

    public void hasilAkhir(EditText inputW, EditText inputH) {
        String weightStr = inputW.getText().toString();
        String heightStr = inputH.getText().toString();
        if ((weightStr != null && !"".equals(weightStr) || (heightStr != null && !"".equals(heightStr)))) {
            float weightValue = Float.parseFloat(weightStr);
            float heightValue = Float.parseFloat(heightStr);
            float hasilhitung = weightValue / (heightValue * heightValue);
            result.setText("Nilai BMI kamu adalah " + (Float.toString(hasilhitung)));
        } else {
            result.setText("Masukkan nilai tinggi dan berat badan!");
        };
    }

    public void newFile() {
        editTitle.setText("");
        editContent.setText("");
        editWeight.setText("");
        editHeight.setText("");
        result.setText("");
        Toast.makeText(this, "new note", Toast.LENGTH_SHORT).show();
    }

    private void loadData(String title) {
        String text = FileHelper.readFromFile(this, title);
        editTitle.setText(title);
        result.setText(text);
        Toast.makeText(this, "view " + title + " data", Toast.LENGTH_SHORT).show();
    }

    public void openFile() {

        showList();
    }

    private void showList() {
        final ArrayList<String> arrayList = new ArrayList<String>();
        for (String file : path.list()) {
            arrayList.add(file);
        }
        final CharSequence[] items = arrayList.toArray(new CharSequence[arrayList.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose your file:");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                loadData(items[item].toString());
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void saveFile(){
        if (editTitle.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please give the file title!", Toast.LENGTH_SHORT).show();
        } else {
            String title = editTitle.getText().toString();
            String text1 = editWeight.getText().toString();
            String text2 = editHeight.getText().toString();
            String text3 = result.getText().toString();
            String text4 = editContent.getText().toString();
            String textTotal = "Berat Badan:  " + text1 + " kg" +
                    "\t Tinggi Badan: " + text2 + " m" +
                    "\t"  + text3 +
                    "\t Note: " + text4;

            FileHelper.writeToFile(title, textTotal, this);
            Toast.makeText(this, "Saving " + editTitle.getText().toString() + " file", Toast.LENGTH_SHORT).show();
        }
    }

}