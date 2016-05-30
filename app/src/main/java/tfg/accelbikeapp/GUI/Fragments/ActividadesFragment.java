package tfg.accelbikeapp.GUI.Fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import tfg.accelbikeapp.Controlador;
import tfg.accelbikeapp.Evento;
import tfg.accelbikeapp.MainActivity;
import tfg.accelbikeapp.R;

/**
 * Created by alexis on 11/04/16.
 */
public class ActividadesFragment extends Fragment {

    ListView actividades;
    ArrayAdapter<String> adaptador;

    private ArrayList<String> sistemas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.actividades_layout, null);
        initUI(v);
        return v;

    }

    public void initUI(View v){

        actividades = (ListView) v.findViewById(R.id.listSesiones);

        sistemas = new ArrayList<>();

        adaptador = new ArrayAdapter<String>(getContext(), R.layout.row_layout, sistemas){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View rowView;
                LayoutInflater inflater =
                        (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                rowView = inflater.inflate(R.layout.row_layout, parent, false);

                TextView textView = (TextView) rowView.findViewById(R.id.label);
                textView.setText(sistemas.get(position));

                return rowView;

            }
        };

        actividades.setAdapter(adaptador);

        final Controlador controlador = ((MainActivity)getActivity()).getControlador();
        actividades.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                controlador.accion(Evento.CARGAR_SESION, sistemas.get(position));

            }
        });

    }

    public void actualizarActividades(String sesion){

        if (!sistemas.contains(sesion))
            sistemas.add(sesion);
        adaptador.notifyDataSetChanged();

    }
}