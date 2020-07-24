package com.example.mobile_icp6;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AudioAssistant extends AppCompatActivity {

    private static final int REQ_SPEECH_INPUT = 100;
    private TextView VoiceInputTextview;
    private ImageButton SpeechButton;
    TextToSpeech txtToSpeech;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_assistant);

        // Acquiring the text view, speak button by Id's
        VoiceInputTextview = (TextView) findViewById(R.id.voiceInput);
        SpeechButton = (ImageButton) findViewById(R.id.btnSpeak);

        // Initializing the Preferences and Editor
        pref = getSharedPreferences("namePrefs",0);
        editor = pref.edit();

        // The speaker says "Hello" on Page Load
        txtToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    // Setting the Locale.
                    txtToSpeech.setLanguage(Locale.US);
                    txtToSpeech.speak("Hello", TextToSpeech.QUEUE_FLUSH, null);
                    VoiceInputTextview.setText(Html.fromHtml("<p style=\"color:black;\"><b>Siri :</b><i> Hello !!</i></p>"));
                }
            }
        });
        SpeechButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoiceInput();
            }
        });
    }
    private void startVoiceInput() {
        // Starting the Voice Input on click of the Microphone button
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hey! How can I help you?");
        try {
            startActivityForResult(intent, REQ_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            a.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    // Acquiring the result
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    if(result != null && result.size() > 0) {
                        VoiceInputTextview.append(Html.fromHtml("<p style=\"color:#228B22;\"><b>Me :</b><i> "+result.get(0)+"</i></p>"));
                        // If the user says 'hello', Asks for the user's name and show/says the Greetings along with user name.

                        if(result.get(0).equalsIgnoreCase("hello")) {
                            txtToSpeech.speak(" Hey ! What's your name ?", TextToSpeech.QUEUE_FLUSH, null);
                            VoiceInputTextview.append(Html.fromHtml("<p style=\"color:black;\"><b>Siri :</b><i> Hey! What's your name?</i></p>"));
                        }
                        else if(result.get(0).contains("name")){
                            // Setting the Greeting by indexing
                            String name = result.get(0).substring(result.get(0).lastIndexOf(' ') + 1);
                            // Setting into Editor
                            editor.putString("name", name).apply();
                            txtToSpeech.speak("Hello, "+name,
                                    TextToSpeech.QUEUE_FLUSH, null);
                            VoiceInputTextview.append(Html.fromHtml("<p style=\"color:black;\"><b>Siri :</b><i> Hello! "+name+".</i></p>"));
                        }
                        else if(result.get(0).contains("not feeling good")){
                            txtToSpeech.speak("I can understand. Please tell your symptoms in short.",
                                    TextToSpeech.QUEUE_FLUSH, null);
                            VoiceInputTextview.append(Html.fromHtml("<p style=\"color:black;\"><b>Siri :</b><i> I can understand. Please tell your symptoms in short.</i></p>"));
                        }
                        else if(result.get(0).contains("thank you")){
                            txtToSpeech.speak("Thank you too "+ pref.getString("name","")+"! Take care.",
                                    TextToSpeech.QUEUE_FLUSH, null);
                            VoiceInputTextview.append(Html.fromHtml("<p style=\"color:black;\"><b>Siri :</b><i> Thank you too, "+ pref.getString("name","")+"! Take care.</i></p>"));
                        }
                        else if(result.get(0).contains("time")){
                            // Speaking the Time for the User
                            SimpleDateFormat sdfDate =new SimpleDateFormat("HH:mm");//dd/MM/yyyy
                            Date now = new Date();
                            String[] strDate = sdfDate.format(now).split(":");
                            if(strDate[1].contains("00"))strDate[1] = "o'clock";
                            txtToSpeech.speak("The time is : "+sdfDate.format(now), TextToSpeech.QUEUE_FLUSH, null);
                            VoiceInputTextview.append(Html.fromHtml("<p style=\"color:black;\"><b>Siri :</b><i> The time is : "+sdfDate.format(now)+".</i></p>"));
                        }
                        else if(result.get(0).contains("medicine")){
                            txtToSpeech.speak("I think you have fever. Please take this medicine.",
                                    TextToSpeech.QUEUE_FLUSH, null);
                            VoiceInputTextview.append(Html.fromHtml("<p style=\"color:black;\"><b>Siri :</b><i> I think you have fever. Please take this medicine.</i></p>"));
                        }
                        else {
                            txtToSpeech.speak("Sorry! can't help you with that", TextToSpeech.QUEUE_FLUSH, null);
                            VoiceInputTextview.append(Html.fromHtml("<p style=\"color:black;\"><b>Siri :</b><i> Sorry! Can't help you with that.</i></p>"));
                        }
                    }
                }
                break;
            }

        }
    }
}