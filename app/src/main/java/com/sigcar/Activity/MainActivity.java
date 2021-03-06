package com.sigcar.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sigcar.Classes.Usuario;
import com.sigcar.DAO.ConfiguracaoFirebase;
import com.sigcar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private EditText edtEmailLogin;
    private EditText edtSenhaLogin;
    private Button btnLogin;
    private Button btnCadastre;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmailLogin = (EditText) findViewById(R.id.edtEmail);
        edtSenhaLogin = (EditText) findViewById(R.id.edtSenha);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnCadastre = (Button)  findViewById(R.id.btnCadastrese);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!edtEmailLogin.getText().toString().equals("") && !edtSenhaLogin.getText().toString().equals("")) {

                    usuario = new Usuario();

                    usuario.setEmail(edtEmailLogin.getText().toString());
                    usuario.setSenha(edtSenhaLogin.getText().toString());

                    validarLogin();


                } else {
                    Toast.makeText(MainActivity.this, "Preencha os campos de E-mail e senha", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCadastre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                abrirTelaCadastro();

            }
        });
    }

    private void validarLogin() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
        autenticacao.signInWithEmailAndPassword(usuario.getEmail().toString(), usuario.getSenha().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    abrirTelaPrincipal();
                    Toast.makeText(MainActivity.this, "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Usuário ou senha inválidos! Tente novamente!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void abrirTelaPrincipal() {

        Intent intent = new Intent(MainActivity.this, MusicaActivity.class);
        startActivity(intent);
    }

    public void abrirTelaCadastro(){

        Intent intent = new Intent(MainActivity.this, CadastroUsuarioActivity.class);
        startActivity(intent);
    }

}

