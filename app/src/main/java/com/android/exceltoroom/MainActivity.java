package com.android.exceltoroom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.exceltoroom.databinding.ActivityMainBinding;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding layout;
    ExcelParser excelParser;
    List<Med> meds=new ArrayList<>();
    ArrayAdapter<Med> adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(layout.getRoot());
    
        adapter = new ArrayAdapter<Med>(this,
                android.R.layout.simple_dropdown_item_1line, meds);
        layout.autoCompleteTextView.setAdapter(adapter);
    
        excelParser=new ExcelParser(this);
        excelParser.parseFile(R.raw.med,onListUpdatedListener);
        
    }
    
    
    public ExcelParser.ExcelFileParserListener onListUpdatedListener=new ExcelParser.ExcelFileParserListener() {
        @Override
        public void onComplete(List<Med> result) {
            layout.llLoading.setVisibility(View.GONE);
            if (result==null){
                Toast.makeText(MainActivity.this, "Error, see logs with \"test\" tag for details", Toast.LENGTH_SHORT).show();
                return;
            }
            meds.clear();
            meds.addAll(result);
            adapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, meds.size()+" total medicines", Toast.LENGTH_SHORT).show();
            Log.d("test", "onComplete: "+meds.size());
        }
    };
    
}
