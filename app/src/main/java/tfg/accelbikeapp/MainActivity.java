package tfg.accelbikeapp;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import tfg.accelbikeapp.Bluetooth.BLEGatt;
import tfg.accelbikeapp.GUI.Fragments.ConfigFragment;
import tfg.accelbikeapp.GUI.Fragments.HelpFragment;
import tfg.accelbikeapp.GUI.Fragments.TabFragment;

public class MainActivity extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    Controlador controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controlador = new Controlador(getApplicationContext());

        /**
         *Setup the DrawerLayout and NavigationView
         */

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */
        //TODO esto es super feo, peeeeero
        FragmentLoader.getInstancia().init(getSupportFragmentManager());

        FragmentLoader.getInstancia().cargar(new TabFragment(), "TAB_FRAGMENT");

        /**
         * Setup click events on the Navigation View Items.
         */
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

                if (menuItem.getItemId() == R.id.nav_item_inbox) {
                    FragmentLoader.getInstancia().cargar(new TabFragment(), "TAB_FRAGMENT");
                }

                if (menuItem.getItemId() == R.id.nav_item_config) {
                    FragmentLoader.getInstancia().cargar(new ConfigFragment(), "CONFIG_FRAGMENT");
                }

                if (menuItem.getItemId() == R.id.nav_item_help) {
                    FragmentLoader.getInstancia().cargar(new HelpFragment(), "HELP_FRAGMENT");
                }
                return false;
            }
        });

        /**
         * Setup Drawer Toggle of the Toolbar
         */
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    public void onDestroy(){

        super.onDestroy();
        BLEGatt.getInstancia().disconnect();

    }

    public Controlador getControlador(){ return controlador; }
}
