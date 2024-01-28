package sanchezfernandez.franciscojose.tarea08;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Clase del fragment 'Calculadora'.
 * La clase contiene 3 variables para controlar el flujo de operaciones:
 *  - String operacion -> almacena un String que coincide con el text del botón fuente del evento.
 *  - float operando -> almacena el resultado temporal de las operaciones. Al pulsar en el botón igual
 *                      se realiza la ultima operación almacenada y se muestra el resultado final en
 *                      el TextView de la IU.
 *  - boolean borrarInputs -> controla si se debe añadir dígitos al TextView de la IU, o si se debe
 *                      borrar el TV y empezar una nueva operación.
 *                      Al dar al botón 'igual' el usuario espera que se muestre el resultado de la
 *                      operación, pero que si empieza a introducir un nuevo número, este sustitya
 *                      al que se está mostrando. Pero si en vez de un número, pulsa una tecla de
 *                      operación tra pulsa 'igual', el usuario espera que el resultado anterior sea
 *                      usado como operando en la nueva operación.
 */
public class CalculadoraFragment extends Fragment {

    View view;

    // teclado
    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnMas, btnMenos, btnPor, btnEntre, btnIgual, btnMasMenos, btnComa, btnClear;
    TextView tvResult;

    // Variables para las operaciones
    float operando;
    String operacion;
    boolean borrarInputs = false;

    /**
     * En el constructor inicializo las variable de la lógica de operaciones
     */
    public CalculadoraFragment() {
        // Required empty public constructor
        operando = 0f;
        operacion = "";
    }

    /**
     * Durante la creación de la vista, defino la propia view, para luego inicializar el resto de
     * componentes de la vista.
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
        view = inflater.inflate(R.layout.fragment_calculadora, container, false);

        inicializarViews();
        return view;
    }

    /**
     * Este método inicializa y los botones y textviews de la IU.
     * En este método se settean los eventos click de los botones.
     */
    private void inicializarViews() {
        tvResult = view.findViewById(R.id.tvResult);
        // Botones que insertan texto
        btn0 = view.findViewById(R.id.btn0);
        btn1 = view.findViewById(R.id.btn1);
        btn2 = view.findViewById(R.id.btn2);
        btn3 = view.findViewById(R.id.btn3);
        btn4 = view.findViewById(R.id.btn4);
        btn5 = view.findViewById(R.id.btn5);
        btn6 = view.findViewById(R.id.btn6);
        btn7 = view.findViewById(R.id.btn7);
        btn8 = view.findViewById(R.id.btn8);
        btn9 = view.findViewById(R.id.btn9);

        btn0.setOnClickListener(this::clickNumber);
        btn1.setOnClickListener(this::clickNumber);
        btn2.setOnClickListener(this::clickNumber);
        btn3.setOnClickListener(this::clickNumber);
        btn4.setOnClickListener(this::clickNumber);
        btn5.setOnClickListener(this::clickNumber);
        btn6.setOnClickListener(this::clickNumber);
        btn7.setOnClickListener(this::clickNumber);
        btn8.setOnClickListener(this::clickNumber);
        btn9.setOnClickListener(this::clickNumber);

        btnMasMenos = view.findViewById(R.id.btnMasMenos);
        btnMasMenos.setOnClickListener((v) -> {
            String newResult = "";
            if (tvResult.getText().toString().startsWith("-")) {
                newResult = tvResult.getText().toString().replace('-', ' ').trim();
            } else {
                newResult = '-' + tvResult.getText().toString();
            }
            tvResult.setText(newResult);
        });
        btnComa = view.findViewById(R.id.btnComa);
        btnComa.setOnClickListener((v) -> {
            if (!tvResult.getText().toString().contains(".")) {
                String newResult = tvResult.getText().toString() + ".";
                tvResult.setText(newResult);
            }
        });
        btnMas = view.findViewById(R.id.btnMas);
        btnMas.setOnClickListener(this::clickBtnOperacion);
        btnMenos = view.findViewById(R.id.btnMenos);
        btnMenos.setOnClickListener(this::clickBtnOperacion);
        btnPor = view.findViewById(R.id.btnPor);
        btnPor.setOnClickListener(this::clickBtnOperacion);
        btnEntre = view.findViewById(R.id.btnEntre);
        btnEntre.setOnClickListener(this::clickBtnOperacion);
        btnIgual = view.findViewById(R.id.btnIgual);
        btnIgual.setOnClickListener((v) -> {
            realizarOperacion();
            tvResult.setText(String.valueOf(operando));
            borrarInputs = true;
        });


        btnClear = view.findViewById(R.id.btnClear);
        btnClear.setOnClickListener((v) ->{
            operando = 0f;
            tvResult.setText("0");
            operacion = "";
        });

    }

    /**
     * Método controlador del evento 'click' que se usa sobre cualquier botón numérico.
     * @param v button fuente del evento
     */
    private void clickNumber(View v) {
        Button btn = (Button) v;
        String s = (tvResult.getText().toString().equals("0") || borrarInputs) ?
                btn.getText().toString() : tvResult.getText().toString() + btn.getText().toString();
        tvResult.setText(s);
        borrarInputs = false;
    }

    /**
     * Método controlador del evento 'click' que se usa sobre cualquier botón de operación.
     * @param v button fuente del evento
     */
    private void clickBtnOperacion(View v) {
        Button btn = (Button) v;
        if (operacion != "") {
            realizarOperacion();
            operacion = btn.getText().toString();
            tvResult.setText(String.valueOf(operando));
        } else {
            operacion = btn.getText().toString();
            operando = Float.parseFloat(tvResult.getText().toString());
            borrarInputs = true;

        }
    }

    /**
     * Este método realiza la operación que haya almacenada en la varialble 'operacion'.
     */
    private void realizarOperacion() {
        switch (operacion) {
            case "+":
                operando = operando == 0 ? Float.parseFloat(tvResult.getText().toString()) : (operando + Float.parseFloat(tvResult.getText().toString()));
                //resultado = operando1;
                break;
            case "-":
                operando = operando == 0 ? Float.parseFloat(tvResult.getText().toString()) : (operando - Float.parseFloat(tvResult.getText().toString()));
                //resultado = operando1;
                break;
            case "*":
                operando = operando == 0 ? Float.parseFloat(tvResult.getText().toString()) : (operando * Float.parseFloat(tvResult.getText().toString()));
                //resultado = operando1;
                break;
            case "/":
                operando = operando == 0 ? Float.parseFloat(tvResult.getText().toString()) : (operando / Float.parseFloat(tvResult.getText().toString()));
                //resultado = operando1;
                break;

        }

        operacion = "";
        tvResult.setText("0");
        borrarInputs = true;
    }
}