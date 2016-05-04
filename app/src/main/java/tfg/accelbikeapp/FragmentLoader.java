package tfg.accelbikeapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by rodry on 28/04/16.
 */
public class FragmentLoader {

    private static FragmentLoader instancia;

    private FragmentManager mFragmentManager;
    private Fragment currentFragment;

    private FragmentLoader() {}

    public static FragmentLoader getInstancia(){

        if (instancia == null)
            instancia = new FragmentLoader();

        return instancia;

    }

    public void init(FragmentManager fm){

        mFragmentManager = fm;

    }

    public void cargar(Fragment fragment){

        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, fragment).commit();

        currentFragment = fragment;

    }

    public Fragment getCurrentFragment(){

        return currentFragment;

    }
}
