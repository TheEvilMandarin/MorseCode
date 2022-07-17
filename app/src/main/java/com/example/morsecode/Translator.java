package com.example.morsecode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Translator extends AppCompatActivity {

    private ToggleButton toggleButton;
    private EditText editTextTextMultiLine, editTextTextMultiLine2;
    private ImageButton imageButton;
    BottomFragment f;
    int language = 0, morse = 0;

    private static final Map<String, Character> morseCode, morseCodeEng;
    static {
        morseCode = new HashMap<>();
        morseCodeEng = new HashMap<>();
        morseCode.put(".-", 'а'); morseCode.put("-...", 'б'); morseCode.put(".--", 'в'); morseCode.put("--.", 'г'); morseCode.put("-..", 'д'); morseCode.put(".", 'е');
        morseCode.put("...-", 'ж'); morseCode.put("--..", 'з'); morseCode.put("..", 'и'); morseCode.put(".---", 'й'); morseCode.put("-.-", 'к'); morseCode.put(".-..", 'л');
        morseCode.put("--", 'м'); morseCode.put("-.", 'н'); morseCode.put("---", 'о'); morseCode.put(".--.", 'п'); morseCode.put(".-.", 'р'); morseCode.put("...", 'с');
        morseCode.put("-", 'т'); morseCode.put("..-", 'у'); morseCode.put("..-.", 'ф'); morseCode.put("....", 'х'); morseCode.put("-.-.", 'ц'); morseCode.put("---.", 'ч');
        morseCode.put("----", 'ш'); morseCode.put("--.-", 'щ'); morseCode.put("--.--", 'ъ'); morseCode.put("-.--", 'ы'); morseCode.put("-..-", 'ь'); morseCode.put("..-..", 'э');
        morseCode.put("..--", 'ю'); morseCode.put(".-.-", 'я'); morseCode.put(".----", '1'); morseCode.put("..---", '2'); morseCode.put("...--", '3'); morseCode.put("....-", '4');
        morseCode.put(".....", '5'); morseCode.put("-....", '6'); morseCode.put("--...", '7'); morseCode.put("---..", '8'); morseCode.put("----.", '9'); morseCode.put("-----", '0');
        morseCode.put("--..--", ','); morseCode.put(".-.-.-", '.'); morseCode.put("..--..", '?'); morseCode.put("-.-.--", '!'); morseCode.put("-..-.", '/');morseCode.put(".-...", '&');
        morseCode.put("---...", ':'); morseCode.put("-.-.-.", ';'); morseCode.put("-...-", '='); morseCode.put(".-.-.", '+'); morseCode.put("-....-", '-'); morseCode.put(".-..-.", '"');
        morseCode.put(".--.-.", '@'); morseCode.put(".----.", '\''); morseCode.put("-.--.", ')'); morseCode.put("", ' ');
        morseCodeEng.put(".-", 'a'); morseCodeEng.put("-...", 'b'); morseCodeEng.put("-.-.", 'c'); morseCodeEng.put("-..", 'd'); morseCodeEng.put(".", 'e'); morseCodeEng.put("..-.", 'f');
        morseCodeEng.put("--.", 'g'); morseCodeEng.put("....", 'h'); morseCodeEng.put("..", 'i'); morseCodeEng.put(".---", 'j'); morseCodeEng.put("-.-", 'k'); morseCodeEng.put(".-..", 'l');
        morseCodeEng.put("--", 'm'); morseCodeEng.put("-.", 'n'); morseCodeEng.put("---", 'o'); morseCodeEng.put(".---.", 'p'); morseCodeEng.put("--.-", 'q'); morseCodeEng.put(".-.", 'r');
        morseCodeEng.put("...", 's'); morseCodeEng.put("-", 't'); morseCodeEng.put("..-", 'u'); morseCodeEng.put("...-", 'v'); morseCodeEng.put(".--", 'w'); morseCodeEng.put("-..-", 'x');
        morseCodeEng.put("-.--", 'y'); morseCodeEng.put("--..", 'z'); morseCodeEng.put(".----", '1'); morseCodeEng.put("..---", '2'); morseCodeEng.put("...--", '3'); morseCodeEng.put("....-", '4');
        morseCodeEng.put(".....", '5'); morseCodeEng.put("-....", '6'); morseCodeEng.put("--...", '7'); morseCodeEng.put("---..", '8'); morseCodeEng.put("----.", '9'); morseCodeEng.put("-----", '0');
        morseCodeEng.put("--..--", ','); morseCodeEng.put(".-.-.-", '.'); morseCodeEng.put("..--..", '?'); morseCodeEng.put(".----.", '\''); morseCodeEng.put("-.-.--", '!'); morseCodeEng.put("-..-.", '/');
        morseCodeEng.put(".-...", '&'); morseCodeEng.put("---...", ':'); morseCodeEng.put("-.-.-.", ';'); morseCodeEng.put("-...-", '='); morseCodeEng.put(".-.-.", '+'); morseCodeEng.put("-....-", '-');
        morseCodeEng.put(".-..-.", '"'); morseCodeEng.put(".--.-.", '@'); morseCodeEng.put("-.--.", ')'); morseCodeEng.put("", ' ');
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translator);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addListenerOnToggleButton();
        addListenerOnImageButton();

        f = new BottomFragment();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fr_bottom, f, "SOMETAG").commit();
        }
        getSupportFragmentManager().beginTransaction().hide(f).commit();
        editTextTextMultiLine.setInputType(InputType.TYPE_CLASS_TEXT);
        editTextTextMultiLine.setFilters(new InputFilter[]{
                new InputFilter() {
                    public CharSequence filter(CharSequence src, int start,
                                               int end, Spanned dst, int dstart, int dend) {
                        if (src.equals("")) {
                            return src;
                        }
                        if (src.toString().matches("[-абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ1234567890.,!+=)?':;@&/ ]+")) {
                            return src;
                        }
                        return "";
                    }
                }
        });
        editTextTextMultiLine.addTextChangedListener(new TextWatcher() {

                                                         @Override
                                                         public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {

                                                         }

                                                         @Override
                                                         public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
                                                             String content = editTextTextMultiLine.getText().toString();
                                                             if(morse == 0) {
                                                                 content = content.toLowerCase();
                                                                 char[] chars = content.toCharArray();
                                                                 StringBuilder morseText = new StringBuilder();
                                                                 if (language == 0)
                                                                    for (char letter : chars) {
                                                                        for(Map.Entry<String, Character> entry: morseCode.entrySet()) {
                                                                            if (entry.getValue().equals(letter))
                                                                                morseText.append(entry.getKey() + " ");
                                                                        }
                                                                    }
                                                                 else
                                                                     for (char letter : chars) {
                                                                         for (Map.Entry<String, Character> entry : morseCodeEng.entrySet()) {
                                                                             if (entry.getValue().equals(letter))
                                                                                 morseText.append(entry.getKey() + " ");
                                                                         }
                                                                     }
                                                                 editTextTextMultiLine2.setText(morseText);
                                                             }
                                                             else {
                                                                 try {
                                                                     StringBuilder decodedText = new StringBuilder();
                                                                     String[] words = content.split("  ");
                                                                     for (String word : words) {
                                                                         String[] splitLetters = word.split(" ");
                                                                         for (String letter : splitLetters) {
                                                                             if (language == 0) {
                                                                                 if (morseCode.get(letter) != null)
                                                                                     decodedText.append(morseCode.get(letter));
                                                                             }
                                                                             else
                                                                                 if(morseCodeEng.get(letter) != null)
                                                                                    decodedText.append(morseCodeEng.get(letter));
                                                                         }
                                                                         decodedText.append(" ");
                                                                     }
                                                                     editTextTextMultiLine2.setText(decodedText);
                                                                 }
                                                                 catch(Exception e) {
                                                                     editTextTextMultiLine2.setText(null);
                                                                 }
                                                             }

                                                         }

                                                         @Override
                                                         public void afterTextChanged(final Editable s) {

                                                         }
                                                     }
        );
    }
    private void addListenerOnToggleButton() {
        toggleButton = (ToggleButton)findViewById(R.id.toggleButton);
        editTextTextMultiLine = (EditText)findViewById(R.id.editTextTextMultiLine);
        editTextTextMultiLine2 = (EditText)findViewById(R.id.editTextTextMultiLine2);
        toggleButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                            // включена ли кнопка
                            boolean on = ((ToggleButton) view).isChecked();
                            if (on) {
                                // действия если включена
                                //Toast.makeText(getApplicationContext() , "Свет вкл", Toast.LENGTH_LONG).show();
                                editTextTextMultiLine.setCursorVisible(false);
                                editTextTextMultiLine.setFocusable(false);
                                editTextTextMultiLine.setLongClickable(false);
                                editTextTextMultiLine.setText(null);
                                editTextTextMultiLine2.setText(null);
                                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                editTextTextMultiLine.setHint(R.string.third_hint);
                                editTextTextMultiLine2.setHint(R.string.fourth_hint);
                                morse = 1;
                                getSupportFragmentManager().beginTransaction().show(f).commit();
                            } else {
                                // действия, если выключена
                                //Toast.makeText(getApplicationContext() , "Свет выкл", Toast.LENGTH_LONG).show();
                                editTextTextMultiLine.setCursorVisible(true);
                                editTextTextMultiLine.setFocusableInTouchMode(true);
                                editTextTextMultiLine.setFocusable(true);
                                editTextTextMultiLine.setLongClickable(true);
                                try {
                                    editTextTextMultiLine.setText(null);
                                    editTextTextMultiLine2.setText(null);
                                }
                                catch (Exception e2) {}
                                if(language == 0)
                                    editTextTextMultiLine.setHint(R.string.first_hint);
                                else
                                    editTextTextMultiLine.setHint(R.string.first_hint_eng);
                                editTextTextMultiLine2.setHint(R.string.second_hint);
                                morse = 0;
                                getSupportFragmentManager().beginTransaction().hide(f).commit();
                            }
                        }

                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_translator, menu);
        return true;
    }

    public void showAlertDialogButtonClicked() {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(Translator.this);
        builder.setTitle("Выберите язык");
        // add a list
        final String[] languages = {"Русский", "English"};
        builder.setSingleChoiceItems(languages, language,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int item) {
                        Toast.makeText(
                                Translator.this,
                                "Language changed to: "
                                        + languages[item],
                                Toast.LENGTH_SHORT).show();
                        editTextTextMultiLine.setText(null);
                        if(item == 0) {
                            imageButton.setImageResource(R.drawable.russia_flag48);
                            language = 0;
                            if(morse == 0)
                                editTextTextMultiLine.setHint(R.string.first_hint);
                            editTextTextMultiLine.setInputType(InputType.TYPE_CLASS_TEXT);
                            editTextTextMultiLine.setFilters(new InputFilter[]{
                                    new InputFilter() {
                                        public CharSequence filter(CharSequence src, int start,
                                                                   int end, Spanned dst, int dstart, int dend) {
                                            if (src.equals("")) {
                                                return src;
                                            }
                                            if (src.toString().matches("[-абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ1234567890.,!+=)?':;@&/ ]+")) {
                                                return src;
                                            }
                                            return "";
                                        }
                                    }
                            });
                        }
                        else {
                            imageButton.setImageResource(R.drawable.english_flag);
                            language = 1;
                            if(morse == 0)
                                editTextTextMultiLine.setHint(R.string.first_hint_eng);
                            editTextTextMultiLine.setInputType(InputType.TYPE_CLASS_TEXT);
                            editTextTextMultiLine.setFilters(new InputFilter[]{
                                    new InputFilter() {
                                        public CharSequence filter(CharSequence src, int start,
                                                                   int end, Spanned dst, int dstart, int dend) {
                                            if (src.equals("")) {
                                                return src;
                                            }
                                            if (src.toString().matches("[-abcdefjhigklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890.,!+=)?':;@&/ ]+")) {
                                                return src;
                                            }
                                            return "";
                                        }
                                    }
                            });
                        }
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog

                    }
                });
                /*.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });*/
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void addListenerOnImageButton() {
        imageButton = (ImageButton)findViewById(R.id.imageButton);
        imageButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialogButtonClicked();
                    }
                }
        );
    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                finish();
                return true;
            case KeyEvent.KEYCODE_DEL:
                //editTextTextMultiLine2.setText("");
                return true;
        }
        return false;
    }*/
    /*@Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //Log.i("key passed", String.valueOf(event.getKeyCode()));
        editTextTextMultiLine2.setText( String.valueOf(event.getKeyCode()));
        return super.dispatchKeyEvent(event);
    }*/
}