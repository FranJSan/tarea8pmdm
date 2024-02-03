package sanchezfernandez.franciscojose.tarea08;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import java.util.logging.Logger;

/**
 * TAREA 8
 * La tarea consiste en crear una activity que contenga dos botones para mostrar un fragment difirente
 * cada uno. Los fragments que se muestran son:
 *  - Button 'Webview" -> El fragment contiene un EditText que simula una barra de direcciones y un
 *                        WebView.
 *  - Button 'Calculadora' -> El fragment contiene los botones necesarios y un TextView para realizar
 *                            operaciones básica y mostrar el resultado.
 * La aplicación contiene layouts para modo land y port, almacenando información para mostrar el mismo
 * fragment en los cambio de estado y colores específicos para el modo día y noche del teléfono.
 */
public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    CalculadoraFragment calculadoraFragment;
    WebViewFragment webViewFragment;
    Button btnWebView, btnCalculadora;

    /**
     * Durante la creación de la activity se inicializan los componentes y se comprueba si hay
     * informaciçon en el Budle savedInstanceState.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarViews();

        // Si hay información en el savedInstanceState, se carga un fragment u otro según esta.
        if (savedInstanceState != null) {
            String fragment = savedInstanceState.getString("fragment");
            if (fragment != null) {
                if (fragment.equals("calc")) {
                    replaceFragment(calculadoraFragment);
                    return;
                }
            }
        }
        replaceFragment(webViewFragment);

    }

    /**
     * Método para inicializar los fragments y los botones a los que se le añade el controlador al
     * evento click.
     */
    private void inicializarViews() {
        fragmentManager = getSupportFragmentManager();
        webViewFragment = new WebViewFragment();
        calculadoraFragment = new CalculadoraFragment();

        btnWebView = findViewById(R.id.btnWebView);
        btnWebView.setOnClickListener((v) -> {
            replaceFragment(webViewFragment);
            mostrarTeclado();
        });


        btnCalculadora = findViewById(R.id.btnCalculadora);
        btnCalculadora.setOnClickListener((v) -> {
            ocultarTeclado();
            replaceFragment(calculadoraFragment);

        });
    }

    /**
     * Método para guardar información sobre el fragment que se está mostrando para poder restaurarlo
     * durante los cambios de estado. No se almacena ninguna información relativa al estado interno
     * del fragment.
     * @param outState Bundle in which to place your saved state.
     *
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Fragment currentFragment = fragmentManager.getFragments().get(0);
        if (currentFragment != null) {
            if (currentFragment == calculadoraFragment) {
                outState.putString("fragment", "calc");
            } else {
                outState.putString("fragment", "web");
            }
        }
    }

    /**
     * Método para ocualtar el teclado cuando se abre el fragment de la calculadora.
     */
    public void ocultarTeclado() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view != null) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Método para mostrar el teclado al abrir el fragment del WebView.
     * NOTA -> Al implementar los cambios de estado y los diferentes layouts, este método empezó
     * a funcionar mal. Creo que tiene que ver con algún aspecto del ciclo de vida al restaurar la vista,
     * pero no he conseguido arreglarlo. Dejo el método porque no da errores y funciona en algunas ocasiones:
     * por ejemplo, si damos click dos veces al boton webview, el EditText recupera el foco como se espera
     * y se muestra el teclado.
     *
     */
    public void mostrarTeclado() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();

        if (view != null) {
            Logger.getAnonymousLogger().info(view.toString());
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    /**
     * Método para reemplazar el fragment actual del fragment content por el fragment que se le
     * pase por parámentro
     * @param fragment nuevo a mostrar
     */
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // Recupero en fragment actual. Si es el mismo que el que queremos mostrar no hago nada.
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragmentContent);
        if (currentFragment != null) {
            if (fragment != currentFragment) {
                transaction.replace(R.id.fragmentContent, fragment, null);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        } else { // si currentFragment es null muestro el fragment del webView
            transaction.replace(R.id.fragmentContent, webViewFragment, null);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}