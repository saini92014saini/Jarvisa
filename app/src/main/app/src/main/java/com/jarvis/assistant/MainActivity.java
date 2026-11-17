package com.jarvis.assistant;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.RecognitionListener;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.view.Gravity;
import android.graphics.Color;
import java.util.Locale;
import java.util.ArrayList;

public class MainActivity extends Activity {

    TextView status;
    TextToSpeech tts;
    SpeechRecognizer recognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(30, 30, 30, 30);
        layout.setBackgroundColor(Color.BLACK);

        TextView title = new TextView(this);
        title.setText("JARVIS");
        title.setTextSize(36);
        title.setTextColor(Color.WHITE);
        title.setGravity(Gravity.CENTER);

        status = new TextView(this);
        status.setText("नमस्ते राहुल सर");
        status.setTextSize(20);
        status.setTextColor(Color.WHITE);
        status.setGravity(Gravity.CENTER);

        Button listen = new Button(this);
        listen.setText("बोलने के लिए दबाएँ");

        layout.addView(title);
        layout.addView(status);
        layout.addView(listen);

        setContentView(layout);

        tts = new TextToSpeech(this, result -> {
            if (result == TextToSpeech.SUCCESS) {
                tts.setLanguage(new Locale("hi", "IN"));
                speak("नमस्ते राहुल सर। मैं आपका जार्विस हूँ।");
            }
        });

        listen.setOnClickListener(v -> startListening());
    }

    private void startListening() {
        if (!SpeechRecognizer.isRecognitionAvailable(this)) {
            status.setText("Voice recognition उपलब्ध नहीं है");
            return;
        }

        if (recognizer != null) recognizer.destroy();

        recognizer = SpeechRecognizer.createSpeechRecognizer(this);

        recognizer.setRecognitionListener(new RecognitionListener() {
            public void onReadyForSpeech(Bundle b) {
                status.setText("सुन रहा हूँ...");
            }

            public void onBeginningOfSpeech() {}
            public void onRmsChanged(float rms) {}
            public void onBufferReceived(byte[] b) {}

            public void onEndOfSpeech() {
                status.setText("समझ रहा हूँ...");
            }

            public void onError(int error) {
                status.setText("दोबारा कोशिश करें");
            }

            public void onResults(Bundle results) {
                ArrayList<String> data =
                    results.getStringArrayList(
                        SpeechRecognizer.RESULTS_RECOGNITION);

                if (data != null && !data.isEmpty()) {
                    String spoken = data.get(0);
                    status.setText("आपने कहा: " + spoken);
                    speak("राहुल सर, आपने कहा " + spoken);
                }
            }

            public void onPartialResults(Bundle b) {}
            public void onEvent(int event, Bundle b) {}
        });

        Intent intent = new Intent(
            RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE,
            "hi-IN");

        recognizer.startListening(intent);
    }

    private void speak(String message) {
        if (tts != null) {
            tts.speak(message, TextToSpeech.QUEUE_FLUSH, null, "jarvis");
        }
    }

    @Override
    protected void onDestroy() {
        if (recognizer != null) recognizer.destroy();
        if (tts != null) tts.shutdown();
        super.onDestroy();
    }
}
