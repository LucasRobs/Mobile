package lucas.robs.entrega2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class MarkerInfoAdapter implements GoogleMap.InfoWindowAdapter {
    Context context;

    public MarkerInfoAdapter(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        Walker walker = (Walker) marker.getTag();
        if(walker == null) return null;
        View view = LayoutInflater.from(context).inflate(R.layout.marker_info, null);
        TextView textView = view.findViewById(R.id.textView2);
        TextView button = view.findViewById(R.id.button4);
        textView.setText(walker.name);
        button.setTag(walker);
        return view;
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        return null;
    }
}
