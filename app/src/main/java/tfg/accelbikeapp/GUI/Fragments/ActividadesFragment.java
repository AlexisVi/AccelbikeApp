package tfg.accelbikeapp.GUI.Fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by alexis on 11/04/16.
 */
public class ActividadesFragment extends Fragment {

    ListView actividades;
    /*private String[] sistemas = {"Dia 20/03/2016", "Dia 01/04/2016", "Dia 04/04/2016", "Dia 05/04/2016"};
    ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getContext(), R.layout.row_layout, sistemas){
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView;
            LayoutInflater inflater =
                    (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            rowView = inflater.inflate(R.layout.row_layout, parent, false);

            TextView textView = (TextView) rowView.findViewById(R.id.label);
            textView.setText(sistemas[position]);

            return rowView;
        }
    };*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.actividades_layout,null);
       // View v = inflater.inflate(R.layout.actividades_layout,null);
        //initUI(v);
        return null;
    }

    public void initUI(View v){

       //actividades = (ListView) v.findViewById(R.id.listble);
       // actividades.setAdapter(adaptador);

       /*actividades.add(0,"Dia 20/03/2016");
        actividades.add(1,"Dia 01/04/2016");
        actividades.add(2,"Dia 04/04/2016");
        actividades.add(3,"Dia 05/04/2016");*/
    }
}
