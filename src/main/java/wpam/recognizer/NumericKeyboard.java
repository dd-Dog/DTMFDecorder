package wpam.recognizer;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;
import android.widget.Button;

public class NumericKeyboard {
    private static final String TAG = "NumericKeyboard";
    private Map<Character, Button> buttons;

    public NumericKeyboard() {
        buttons = new HashMap<Character, Button>();
    }

    public void add(char c, Button button) {
        buttons.put(c, button);
    }

    public void setEnabled(boolean enabled) {
        for (Map.Entry<Character, Button> e : buttons.entrySet()) {
            e.getValue().setEnabled(enabled);
        }
    }

    public void setActive(Character key) {
        Log.d(TAG, "setActive,key=" + key);
        for (Map.Entry<Character, Button> e : buttons.entrySet()) {
            if (e.getKey().equals(key))
                e.getValue().setPressed(true);
            else
                e.getValue().setPressed(false);
        }
    }

}
