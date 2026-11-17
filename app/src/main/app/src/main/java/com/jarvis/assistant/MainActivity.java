
package com.jarvis.assistant;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setBackgroundColor(Color.BLACK);
        layout.setPadding(30, 30, 30, 30);

        TextView title = new TextView(this);
        title.setText("JARVIS");
        title.setTextSize(36);
        title.setTextColor(Color.WHITE);
        title.setGravity(Gravity.CENTER);

        TextView message = new TextView(this);
        message.setText("नमस्ते राहुल सर!\nJarvis सफलतापूर्वक शुरू हो गया है।");
        message.setTextSize(20);
        message.setTextColor(Color.WHITE);
        message.setGravity(Gravity.CENTER);
        message.setPadding(10, 40, 10, 10);

        layout.addView(title);
        layout.addView(message);

        setContentView(layout);
    }
}
