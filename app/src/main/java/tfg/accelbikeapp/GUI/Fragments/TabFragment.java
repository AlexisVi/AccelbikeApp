package tfg.accelbikeapp.GUI.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.ResourceBundle;

import tfg.accelbikeapp.Controlador;
import tfg.accelbikeapp.Evento;
import tfg.accelbikeapp.GUI.Fragments.ActividadesFragment;
import tfg.accelbikeapp.GUI.Fragments.PrincipalFragment;
import tfg.accelbikeapp.MainActivity;
import tfg.accelbikeapp.R;

/**
 * Created by David on 22/02/2016.
 */
public class TabFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 2 ;

    public PrincipalFragment principalFragment;
    public ActividadesFragment actividadesFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View x =  inflater.inflate(R.layout.tab_layout,null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);

        final Controlador controlador = ((MainActivity)getActivity()).getControlador();

        principalFragment = new PrincipalFragment();
        actividadesFragment = new ActividadesFragment(){ // Esto es para que sea lo primero que haga cuando cargue

            @Override
            public void onResume() {
                super.onResume();
                controlador.accion(Evento.CARGAR_TAB, null);
            }
        };

        /**
         *Set an Apater for the View Pager
         */
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return x;

    }

    class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : return principalFragment;
                case 1 : return actividadesFragment;
                /*case 2 : return new EstadisticasFragment();
                case 3 : return new AccelerometerFragment();*/
            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "Sesión";
                case 1 :
                    return "Actividades";
                /*case 2 :
                    return "Estadísticas";
                case 3 :
                    return "Acelerometro";*/
            }
            return null;
        }
    }

    public void actualizarSesiones(String sesion){

        actividadesFragment.actualizarActividades(sesion);

    }

}
