package com.kelasandroidappsirhafizee.steelfit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.TrainerViewHolder> {

    private List<BookingModel> trainerList;

    public BookingAdapter(List<BookingModel> trainerList) {
        this.trainerList = trainerList;
    }


    @NonNull
    public TrainerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking, parent, false);
        return new TrainerViewHolder(view);
    }


    public void onBindViewHolder(@NonNull TrainerViewHolder holder, int position) {
        BookingModel trainer = trainerList.get(position);
        holder.trainerName.setText(trainer.getName());
        holder.trainerAge.setText(String.valueOf(trainer.getAge()));
        holder.trainerSpecialization.setText(trainer.getSpecialization());
        holder.trainerExperiences.setText(String.valueOf(trainer.getExperiences()));

        if (trainer.getProfileImage() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(trainer.getProfileImage(), 0, trainer.getProfileImage().length);
            holder.trainerProfileimg.setImageBitmap(bitmap);
        }

        holder.btnBooking.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, BookingActivity.class);


            intent.putExtra("trainer_id", trainer.getId());
            intent.putExtra("trainer_name", trainer.getName());
            context.startActivity(intent);
            /// bookingActivity boleh baca data yang dihantar

        });
    }

    public int getItemCount(){return  trainerList.size();}

    public static class TrainerViewHolder extends RecyclerView.ViewHolder{

        TextView trainerName, trainerAge, trainerSpecialization, trainerExperiences;

        ImageView trainerProfileimg;
        Button btnBooking;

        public TrainerViewHolder (@NonNull View itemView){
            super(itemView);
            trainerName = itemView.findViewById(R.id.trainerName1);
            trainerAge = itemView.findViewById(R.id.trainerAge1);
            trainerSpecialization = itemView.findViewById(R.id.trainerSpecial1);
            trainerExperiences = itemView.findViewById(R.id.trainerExp1);
            trainerProfileimg = itemView.findViewById(R.id.trainerProfileimg1);
            btnBooking = itemView.findViewById(R.id.btnBooking);
        }
    }
}

