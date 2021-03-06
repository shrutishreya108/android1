/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;
import  com.example.android.miwok.Word;
import org.w3c.dom.Text;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager;
        CategoryAdapter categoryAdapter;
        viewPager = (ViewPager)findViewById(R.id.vpager);
        categoryAdapter = new CategoryAdapter(getSupportFragmentManager());
        viewPager.setAdapter(categoryAdapter);

        // Set the content of the activity to use the activity_main.xml layout file

//        TextView num = (TextView)findViewById(R.id.numbers);
//        num.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(MainActivity.this,NumbersActivity.class);
//                startActivity(i);
//            }
//        });
//        TextView fam = (TextView)findViewById(R.id.family);
//        fam.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(MainActivity.this,FamilyMembers.class);
//                startActivity(i);
//            }
//        });
//        TextView phrase = (TextView)findViewById(R.id.phrases);
//        phrase.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(MainActivity.this,PhrasesActivity.class);
//                startActivity(i);
//            }
//        });
//        TextView col = (TextView)findViewById(R.id.colors);
//        col.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(MainActivity.this,ColorsActivity.class);
//                startActivity(i);
//            }
//        });

    }

}
