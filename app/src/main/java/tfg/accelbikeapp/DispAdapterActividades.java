package tfg.accelbikeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by alexis on 26/04/16.
 */
public class DispAdapterActividades extends ArrayAdapter<String> {

    public final Context context;
    public final ArrayList<String> values;

    public DispAdapterActividades(Context ctx, ArrayList<String> val) {
        super(ctx, R.layout.row_layout, val);
        this.context = ctx;
        this.values = val;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView;
        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        rowView = inflater.inflate(R.layout.row_layout, parent, false);

        String dispositivo = values.get(position);

        TextView textView = (TextView) rowView.findViewById(R.id.label);
        textView.setText(dispositivo);

        return rowView;
    }
}