package com.andy.androidcat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter {

    private ArrayList<Message> messageArrayList;
    private Context context;

    public MessageAdapter(ArrayList<Message> messageArrayList, Context context) {
        this.messageArrayList = messageArrayList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
//        switch layout to viewholder
        switch (viewType){
            case 0:
//                inflating user messageview
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_message, parent, false);
                return new User(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_message, parent, false);
                return new Bot(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // this method is use to set data to our layout file.
        Message message = messageArrayList.get(position);
        switch (message.getSender()) {
            case "user":
                // below line is to set the text to our text view of user layout
                ((User) holder).user.setText(message.getMessage());
                break;
            case "bot":
                // below line is to set the text to our text view of bot layout
                ((Bot) holder).bot.setText(message.getMessage());
                break;
        }
    }

    @Override
    public int getItemCount() {
// return the size of array list
        return messageArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        // below line of code is to set position.
        switch (messageArrayList.get(position).getSender()) {
            case "user":
                return 0;
            case "bot":
                return 1;
            default:
                return -1;
        }
    }

    public static class User extends RecyclerView.ViewHolder {

        // creating a variable
        // for our text view.
        TextView user;

        public User(@NonNull View itemView) {
            super(itemView);
            // initializing with id.
            user= itemView.findViewById(R.id.botMessage);
        }
    }

    public static class Bot extends RecyclerView.ViewHolder {

        // creating a variable
        // for our text view.
        TextView bot;

        public Bot (@NonNull View itemView) {
            super(itemView);
            // initializing with id.
            bot = itemView.findViewById(R.id.userMessage);
        }
    }
}
