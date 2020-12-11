package com.aditya.news.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aditya.news.R;
import com.aditya.news.adapter.NotificationAdapter;
import com.aditya.news.models.notificationmodel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Notification extends Fragment {
    RecyclerView recyclerView;
    List<notificationmodel> articles;
    NotificationAdapter adapter;
    ImageView bitmapimage;

    public Notification() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        recyclerView = view.findViewById(R.id.notificationrecyclerview);
        // Inflate the layout for this fragment
        String filename = "tempdata";


        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);


        bitmapimage = view.findViewById(R.id.notificationimageview);
        File folder = new File(getActivity().getFilesDir().getAbsolutePath());
        System.out.println(folder);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles.length > 0) {
            articles = new ArrayList<>();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    FileReader fileReader = null;
                    try {
                        System.out.println(folder + "/" + listOfFiles[i].getName());
                        fileReader = new FileReader(folder + "/" + listOfFiles[i].getName());
                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                        StringBuilder stringBuilder = new StringBuilder();
                        String line = bufferedReader.readLine();
                        while (line != null) {
                            stringBuilder.append(line).append("\n");
                            line = bufferedReader.readLine();
                        }
                        bufferedReader.close();
                        // This responce will have Json Format String
                        String responce = stringBuilder.toString();
                        try {
                            JSONObject jsonObject = new JSONObject(responce);

                            articles.add(new notificationmodel(jsonObject.get("position").toString(), jsonObject.get("title").toString(), jsonObject.get("headline").toString(), jsonObject.get("time").toString(), jsonObject.get("image").toString(), jsonObject.get("url").toString()));
                            System.out.println(jsonObject.get("title").toString());
                            System.out.println(jsonObject.get("image").toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
            adapter = new NotificationAdapter(articles, getContext());
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
//        articles= new ArrayList<>();
//        articles.add(new notificationmodel(jsonObject.get("title").toString(),jsonObject.get("headline").toString(),jsonObject.get("time").toString(),jsonObject.get("image").toString()));
//        System.out.println(jsonObject.get("image").toString());
//        adapter=new NotificationAdapter(articles,getContext());
//        recyclerView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//            File file = new File(getActivity().getFilesDir().getAbsolutePath(),filename);
//            FileReader fileReader = null;
//            try {
//                fileReader = new FileReader(file);
//                BufferedReader bufferedReader = new BufferedReader(fileReader);
//                StringBuilder stringBuilder = new StringBuilder();
//                String line = bufferedReader.readLine();
//                while (line != null){
//                    stringBuilder.append(line).append("\n");
//                    line = bufferedReader.readLine();
//                }
//                bufferedReader.close();
//                // This responce will have Json Format String
//                String responce = stringBuilder.toString();
//                try {
//                    JSONObject jsonObject  = new JSONObject(responce);
////                jsonObject.get("title").toString();
//                    System.out.println(jsonObject.get("title").toString());
//                    System.out.println(jsonObject.get("image").toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//

        return view;
    }
}