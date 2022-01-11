package lucas.robs.entrega2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class MarkerInfoAdapter implements GoogleMap.InfoWindowAdapter {
    Context context;

    MarkerInfoAdapter(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        String tag =marker.getTag().toString();
        if(tag == null) return null;
        View view = LayoutInflater.from(context).inflate(R.layout.marker_info, null);
        TextView a = view.findViewById(R.id.textView2);
        a.setText(tag);

        return view;
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        return null;
    }
}
