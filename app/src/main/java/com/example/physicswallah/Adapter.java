/*
 * Copyright 2020 Pratyush Tiwari
 *  Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Created by Pratyush Tiwari on 31/1/21 7:35 PM
 *  Last modified 31/1/21 7:21 PM
 *
 *
 */

package com.example.physicswallah;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private final LayoutInflater layoutInflater;
    private final Activity activity;
    private final List<PhysicsWallah> data;

    final Context appContext;
    public Adapter(Context context, Activity activity, List<PhysicsWallah> data){
        this.layoutInflater=LayoutInflater.from(context);
        this.data =data;
        this.activity=activity;

        appContext=context;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.card_design,parent,false);
        return new Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Adapter.ViewHolder holder, final int position) {

        PhysicsWallah physicsWallah=data.get(position);

        holder.name.setText(physicsWallah.name);
       StringBuilder sb=new StringBuilder();
       for(String str : physicsWallah.subjects)
           sb.append(str).append(" ");
       sb.append("• ");
        for(String str : physicsWallah.qualifications)
            sb.append(str).append(" ");
        holder.sub.setText(sb.toString());

        Uri imageUri=Uri.parse(physicsWallah.profileImageUrl);

        if (imageUri!=null && activity!=null &&!activity.isDestroyed() ) {
            // progressBar.setVisibility(View.VISIBLE);

            Glide.with(appContext)
                    .load(imageUri)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }


                    }).placeholder(R.drawable.ic_baseline_person_24)
                    .into(holder.imageView);
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
       TextView name,sub;
       Button btn;
       ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            sub=itemView.findViewById(R.id.sub);
            btn=itemView.findViewById(R.id.btn);
            imageView=itemView.findViewById(R.id.img);
            btn.setText("View More");
        }
    }

}