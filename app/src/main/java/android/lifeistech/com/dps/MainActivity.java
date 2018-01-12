package android.lifeistech.com.dps;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    TextView res;
    TextView res2;
    TextView k_conv;
    TextView save;
    TextView save2;
    EditText dpb;
    EditText rpm;
    EditText acc;
    Spinner spinner;
    int a0;
    int a1;
    int a0_s;
    int a1_s;
    int a0_s2;
    int a1_s2;
    float a2;
    float pc;
    float pc_s;
    float pc_s2;
    float mc;
    float dm;
    float k_count;
    String spinnerItems[] = {"Custom","BF4:M416", "BF4:Scar-H", "BF4:P90", "BF4:sg553", "Save.1", "Save.2"};
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        res = (TextView)findViewById(R.id.res);
        res2 = (TextView)findViewById(R.id.res2);
        k_conv = (TextView)findViewById(R.id.k_conv);
        save = (TextView)findViewById(R.id.save);
        save2 = (TextView)findViewById(R.id.save2);
        dpb = (EditText)findViewById(R.id.dpb);
        rpm = (EditText) findViewById(R.id.rpm);
        acc = (EditText) findViewById(R.id.acc);
        spinner = (Spinner)findViewById(R.id.spinner);

        pref = getSharedPreferences("pref_save",MODE_PRIVATE);

        a0_s = pref.getInt("a0_s",0);
        a1_s = pref.getInt("a1_s",0);
        pc_s = pref.getFloat("pc_s",0);
        a0_s2 = pref.getInt("a0_s2",0);
        a1_s2 = pref.getInt("a1_s2",0);
        pc_s2 = pref.getFloat("pc_s2",0);

        ArrayAdapter<String> adapter
                = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinnerItems);
        spinner.setAdapter(adapter);

        dpb.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // ... 処理 ...
                cal();
                return false;
            }
        });
        rpm.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // ... 処理 ...
                cal();
                return false;
            }
        });
        acc.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // ... 処理 ...
                cal();
                return false;
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //　アイテムが選択された時
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
                Spinner spinner = (Spinner)parent;
                String item = (String)spinner.getSelectedItem();
                if(item.contains("BF4:M416")){
                    a0 = 24;
                    a1 = 750;
                    cal_temp();
                }else if(item.contains("BF4:Scar-H")){
                    a0 = 33;
                    a1 = 620;
                    cal_temp();
                }else if(item.contains("BF4:P90")){
                    a0 = 21;
                    a1 = 900;
                    cal_temp();
                }else if(item.contains("BF4:sg553")){
                    a0 = 24;
                    a1 = 830;
                    cal_temp();
                }else if(item.contains("Save.1")){
                    a0 = a0_s;
                    a1 = a1_s;
                    pc = pc_s;
                    cal_temp();
                }else if(item.contains("Save.2")){
                    a0 = a0_s2;
                    a1 = a1_s2;
                    pc = pc_s2;
                    cal_temp();
                }
            }
            //　アイテムが選択されなかった
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        }
        public void cal(){
            String str1 = dpb.getText().toString();
            String str2 = rpm.getText().toString();
            String str3 = acc.getText().toString();
            if((dpb.getText().toString().trim().length() > 0)  &&  (rpm.getText().toString().trim().length() > 0) && (acc.getText().toString().trim().length() > 0)
                    && (Pattern.matches("^-?[0-9]*.?[0-9]+$", str1))  && (Pattern.matches("^-?[0-9]*.?[0-9]+$", str2))   && (Pattern.matches("^-?[0-9]*.?[0-9]+$", str3)))
            {
                a0 = Integer.parseInt(str1);
                a1 = Integer.parseInt(str2);
                pc = Integer.parseInt(str3);
                mc = pc / 100;
                a2 = a0 * a1;
                dm = a2 * mc;
                res.setText(a2 + " DPM");
                res2.setText(dm + " DPM");
                k_count = dm / 100;
                k_conv.setText("HP:100の人間を1分間に" + k_count + "人裁くことができる");
            }
        }
    public void cal_temp(){
            dpb.setText(String.valueOf(a0));
            rpm.setText(String.valueOf(a1));
            acc.setText(String.valueOf(pc));
            mc = pc / 100;
            a2 = a0 * a1;
            dm = a2 * mc;
            res.setText(a2 + " DPM");
            res2.setText(dm + " DPM");
            k_count = dm / 100;
            k_conv.setText("HP:100の人間を1分間に" + k_count + "人裁くことができる");
        }

    public void save_c(View v){
        a0_s = a0;
        a1_s = a1;
        pc_s = pc;
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("a0_s",a0_s);
        editor.putInt("a1_s",a1_s);
        editor.putFloat("pc_s",pc_s);
        editor.apply();
    }

    public void save_c2(View v){
        a0_s2 = a0;
        a1_s2 = a1;
        pc_s2 = pc;
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("a0_s2",a0_s2);
        editor.putInt("a1_s2",a1_s2);
        editor.putFloat("pc_s2",pc_s2);
        editor.apply();
    }
}
