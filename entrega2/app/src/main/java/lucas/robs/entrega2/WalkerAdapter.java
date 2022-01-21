package lucas.robs.entrega2;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WalkerAdapter extends RecyclerView.Adapter<WalkerAdapter.ViewHolderWalker> {
    @NonNull
    @Override
    public WalkerAdapter.ViewHolderWalker onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull WalkerAdapter.ViewHolderWalker holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolderWalker extends RecyclerView.ViewHolder {
        public TextView textIcon;
        public ImageView dotsButton;

        public ViewHolderWalker(@NonNull View itemView) {
            super(itemView);
        }
    }
}
