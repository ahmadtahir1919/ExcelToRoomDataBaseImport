package com.android.exceltoroom;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ExcelParser extends AsyncTask<Void, Integer, Boolean> {
    Context context;
    int file;
    List<Med> meds;
    ExcelFileParserListener listener;
    
    public ExcelParser(Context context) {
        this.context = context;
    }
    
    @Override
    protected Boolean doInBackground(Void... voids) {
        AppDatabase medDatabase = AppDatabase.getDatabase(context);
        try {
            CSVReader reader = new CSVReaderBuilder(new InputStreamReader(context.getResources().openRawResource(R.raw.med)))
                    .withSkipLines(medDatabase.medDao().getCount())
                    .build();
            Log.d("test", "Skipping "+reader.getSkipLines()+" lines");
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                Med med = new Med();
                med.setName(nextLine[0]);
                medDatabase.medDao().insertAll(med);
            }
            
        } catch (IOException e) {
            listener.onComplete(null);
            Log.d("test", "onCreate: file not found " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        
        meds = medDatabase.medDao().getAll();
        return true;
    }
    
    @Override
    protected void onPostExecute(Boolean result) {
        listener.onComplete(meds);
    }
    
    
    public void parseFile(int file, ExcelFileParserListener listener) {
        this.file = file;
        this.listener = listener;
        execute();
    }
    
    
    public interface ExcelFileParserListener {
        void onComplete(List<Med> meds);
        
    }
    
}
