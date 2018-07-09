package net.alunando.circularufrpe.classes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.alunando.circularufrpe.R;

import java.util.List;

public class ComentarioAdapter extends RecyclerView.Adapter<ComentarioAdapter.ComentarioViewHolder>{

    private Context mCtx;
    private List<Comentario> comentarioList;

    public ComentarioAdapter(Context mCtx, List<Comentario> comentarioList) {
        this.mCtx = mCtx;
        this.comentarioList = comentarioList;
    }

    public List<Comentario> getComentarioList() {
        return comentarioList;
    }

    public void setProductList(List<Comentario> comentarioList) {
        this.comentarioList = comentarioList;
    }

    @NonNull
    @Override
    public ComentarioViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.comentario_item, null);
        return new ComentarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComentarioViewHolder holder, int position) {
        Comentario comentario = comentarioList.get(position);

        holder.nameView.setText(comentario.getUsuario().getNome());
        holder.dataView.setText(comentario.getData() + " às " + comentario.getHora());
        holder.commentView.setText(String.valueOf(comentario.getTexto()));

        //holder.avatarView.setImageDrawable(mCtx.getResources().getDrawable(Integer.getInteger(comentario.getUsuario().getAvatar())));

        Glide.with(mCtx)
                .load(comentario.getUsuario().getAvatar())
                .into(holder.avatarView);
    }

    @Override
    public int getItemCount() {
        return comentarioList.size();
    }

    class ComentarioViewHolder extends RecyclerView.ViewHolder{

        ImageView avatarView;
        TextView nameView, dataView, commentView;

        public ComentarioViewHolder(@NonNull View itemView) {
            super(itemView);

            avatarView = itemView.findViewById(R.id.avatarView);
            nameView = itemView.findViewById(R.id.nameView);
            dataView = itemView.findViewById(R.id.dataView);
            commentView = itemView.findViewById(R.id.commentView);
            avatarView = itemView.findViewById(R.id.avatarView);
        }
    }
}
