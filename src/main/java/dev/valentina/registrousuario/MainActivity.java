package dev.valentina.registrousuario;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    // Creating EditText.
    EditText FullName, Email, Password ;

    // Creating button;
    Button Register;

    // Creating Volley RequestQueue.
    RequestQueue requestQueue;

    // Create string variable to hold the EditText Value.
    String NameHolder, EmailHolder, PasswordHolder ;

    // Creating Progress dialog.
    ProgressDialog progressDialog;

    // Storing server url into String variable.
    String HttpUrl = "http://10.0.2.2:80/Volley/User-Registration.php";
    //String HttpUrl = "http://127.0.0.1:80/Volley/User-Registration.php";

    Boolean CheckEditText ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assigning ID's to EditText.
        FullName = (EditText) findViewById(R.id.EditTextFullName);

        Email = (EditText) findViewById(R.id.EditTextEmail);

        Password = (EditText) findViewById(R.id.EditTextPassword);

        // Assigning ID's to Button.
        Register = (Button) findViewById(R.id.ButtonRegister);

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(MainActivity.this);

        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(MainActivity.this);

        // Adding click listener to button.
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckEditTextIsEmptyOrNot();

                if(CheckEditText){

                    UserRegistration();

                }
                else {

                    Toast.makeText(MainActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }

            }
        });

    }

    public void UserRegistration(){

        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
        progressDialog.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing Echo Response Message Coming From Server.
                        Toast.makeText(MainActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        //showMessage("Error en repuesta del servidor",ServerResponse.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                        showMessage("Error en Voley",volleyError.toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("User_Email", EmailHolder);
                params.put("User_Password", PasswordHolder);
                params.put("User_Full_Name", NameHolder);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }


    public void CheckEditTextIsEmptyOrNot(){

        // Getting values from EditText.
        NameHolder = FullName.getText().toString().trim();
        EmailHolder = Email.getText().toString().trim();
        PasswordHolder = Password.getText().toString().trim();

        // Checking whether EditText value is empty or not.
        if(TextUtils.isEmpty(NameHolder) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder))
        {

            // If any of EditText is empty then set variable value as False.
            CheckEditText = false;

        }
        else{

            // If any of EditText is filled then set variable value as True.
            CheckEditText = true ;
        }
    }
    public void showMessage(String titulo, String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(titulo);
        builder.setMessage(mensaje);
        builder.show();
    }



}