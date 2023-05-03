package com.example.textilemart;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class padapter extends FirebaseRecyclerAdapter<model, padapter.myviewholder> {

    Context context;
    public padapter(@NonNull FirebaseRecyclerOptions<model> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull padapter.myviewholder holder, int position, @NonNull model model) {

        holder.ProductName.setText(model.getProductName());
        holder.Price.setText(model.getPrice());
        holder.Quantity.setText(model.getQuantity());
        Glide.with(holder.ppurl.getContext()).load(model.getPpurl()).into(holder.ppurl);

        holder.ProductName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, product_owner_details.class);
                intent.putExtra("ProductName", model.getProductName());
                intent.putExtra("Price", model.getPrice());
                intent.putExtra("Quantity", model.getQuantity());
                intent.putExtra("ppurl",model.getPpurl());
                intent.putExtra("userid",model.getUserid());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Toast.makeText(holder.ProductName.getContext(),"In padapter",Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public padapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{
        TextView ProductName,Price,Quantity;
        ImageView ppurl;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            ProductName = itemView.findViewById(R.id.pname);
            Price = itemView.findViewById(R.id.pprice);
            Quantity = itemView.findViewById(R.id.pqty);
            ppurl=itemView.findViewById(R.id.i);
            //status = itemView.findViewById(R.id.t4);
        }
    }
}

