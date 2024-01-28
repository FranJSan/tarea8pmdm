package sanchezfernandez.franciscojose.tarea08;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.util.logging.Logger;

/**
 * Clase del fragment WebView.
 * Esta clase contiene wl WebView que mostrará la Url que introduzca el usuario.
 * La clase contiene métodos para comprobar la valided de la URL solicitada.
 */
public class WebViewFragment extends Fragment {

    private View view;
    private WebView wv;
    private Button btnIr;
    private EditText etInput;
    private Context context;

    public WebViewFragment() {
        // Required empty public constructor
    }


    /**
     * Durante la cración de la vista almaceno la View creada que se usará para inflar el resto de views.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_web_view, container, false);
        inicializarUI();
        return view;
    }

    /**
     * Este método inicializa los componentes de la IU.
     */
    private void inicializarUI() {
        context = view.getContext();
        btnIr = view.findViewById(R.id.btnIr);
        btnIr.setOnClickListener(this::btnClick);
        wv = view.findViewById(R.id.webView);
        etInput = view.findViewById(R.id.etUrl);
        etInput.requestFocus();
    }

    /**
     * Controlador del evento click del botón 'Ir'.
     * @param v
     * @see #cargarWeb()
     */
    private void btnClick(View v) {
        cargarWeb();
    }

    /**
     * Método que carga la url formateada en el WebView. Antes de cargarla, comprueba su validez.
     * @see #comprobarInput(String)
     *
     */
    private void cargarWeb() {
        String urlFormat = getFormatUrl();
        if(!comprobarInput(urlFormat)) return;
        wv.setWebViewClient(new WebViewClient());
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl(urlFormat);
    }

    /**
     * Método que devuelve la Url introducida por el usuario con un formato válido.
     * @return
     */
    private String getFormatUrl() {
        String urlInput = etInput.getText().toString();
        if (!urlInput.startsWith("http")) {
            urlInput = "http://" + urlInput;
        }
        return urlInput;
    }

    /**
     * Método que comprueba a través de un Regex (sacado de internet, pero lo he revisado y probado)
     * la validez de la Url solicitada por el usuario.
     * @param url
     * @return
     */
    private boolean comprobarInput(String url) {

        if (url.matches("https?://([a-zA-Z0-9]([^ @&%$\\\\\\/\\(\\)=?¿!.,:;]|\\d)+[a-zA-Z0-9][\\.])+[a-zA-Z0-9]{2,4}([\\.][a-zA-Z]{2})?")) {
            return true;
        }
        Toast toast = Toast.makeText(context, "Error en la URL introducida", Toast.LENGTH_SHORT);
        toast.show();
        return false;
    }








}