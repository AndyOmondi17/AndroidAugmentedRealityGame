package com.andy.androidcat;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
//import com.google.ar.sceneform.ux.ArFragment;

public class MainActivity extends AppCompatActivity  {
    private RecyclerView chats;
    private ImageButton sendMsg;
    private EditText userMsgEdt;
    private final String USER_KEY = "user";
    private final String BOT_KEY = "bot";

    // creating a variable for
    // our volley request queue.
    private RequestQueue mRequestQueue;
//    creating a variable for arraylist and adapter class
    private ArrayList<Message> messageArrayList;
    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chats = findViewById(R.id.iChats);
        sendMsg = findViewById(R.id.idSend);
        userMsgEdt =findViewById(R.id.idEdtMessage);

//        volley request queue
        mRequestQueue = Volley.newRequestQueue(MainActivity.this);
        mRequestQueue.getCache().clear();

        messageArrayList = new ArrayList<>();


    }

    private void sendMessage(String userMsg) {
        // below line is to pass message to our
        // array list which is entered by the user.
        messageArrayList.add(new Message(userMsg, USER_KEY));
        messageAdapter.notifyDataSetChanged();

        // url for our brain
        // make sure to add mshape for uid.
        // make sure to add your url.
        String url = "Enter you API URL here" + userMsg;

        // creating a variable for our request queue.
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        // on below line we are making a json object request for a get request and passing our url .
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // in on response method we are extracting data
                    // from json response and adding this response to our array list.
                    String botResponse = response.getString("cnt");
                    messageArrayList.add(new Message(botResponse, BOT_KEY));

                    // notifying our adapter as data changed.
                    messageAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();

                    // handling error response from bot.
                    messageArrayList.add(new Message("No response", BOT_KEY));
                    messageAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error handling.
                messageArrayList.add(new Message("Sorry no response found", BOT_KEY));
                Toast.makeText(MainActivity.this, "No response from the bot..", Toast.LENGTH_SHORT).show();
            }
        });

        // at last adding json object
        // request to our queue.
        queue.add(jsonObjectRequest);
    }


    public void sendMessage(View view) {
        // checking if the message entered
        // by user is empty or not.
        if (userMsgEdt.getText().toString().isEmpty()) {
            // if the edit text is empty display a toast message.
            Toast.makeText(MainActivity.this, "Please enter your message..", Toast.LENGTH_SHORT).show();
            return;
        }

        // calling a method to send message
        // to our bot to get response.
        sendMessage(userMsgEdt.getText().toString());

        // below line we are setting text in our edit text as empty
        userMsgEdt.setText("");
    }
}