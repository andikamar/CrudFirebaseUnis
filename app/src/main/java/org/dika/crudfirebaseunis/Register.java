package org.dika.crudfirebaseunis;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
	EditText email,password,nama;
	Button register;
	ProgressBar pb;
	FirebaseAuth fa;
	FirebaseDatabase fd;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		email=  findViewById(R.id.email);
		password=  findViewById (R.id.password);
		nama= findViewById (R.id.name);
		register= findViewById(R.id.btn_register);
		pb=findViewById(R.id.pb1);

		fa=FirebaseAuth.getInstance();

		if (fa.getCurrentUser()!=null){
			Intent intent = new Intent(Register.this.getApplicationContext(), Login.class);
			startActivity(intent);
		}

		fd=FirebaseDatabase.getInstance();

		register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pb.setVisibility(View.VISIBLE);

				String txtemail=email.getText().toString();
				String txtpass=password.getText().toString();
				final String txtnama=nama.getText().toString();

				if (TextUtils.isEmpty(txtemail)){
					email.setError("Email Wajib Di Isi");
					Toast.makeText(getApplicationContext(),"Email Wajib Di Isi", Toast.LENGTH_LONG).show();
					pb.setVisibility(View.GONE);
					return;
				}
				if (TextUtils.isEmpty(txtpass)){
					password.setError("Password Tidak Boleh Kosong");
					Toast.makeText(getApplicationContext(),"Password Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
					pb.setVisibility(View.GONE);
					return;
				}
				if (TextUtils.isEmpty(txtnama)){
					nama.setError("Nama Lengkap Tidak Boleh Kosong");
					Toast.makeText(getApplicationContext(),"Nama Lengkap Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
					pb.setVisibility(View.GONE);
					return;
				}


				fa.createUserWithEmailAndPassword(txtemail,txtpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()){
							Toast.makeText(getApplicationContext(), "Berhasil Register" , Toast.LENGTH_SHORT).show();

							fd.getReference("Users").child(FirebaseAuth.getInstance()
									.getCurrentUser().getUid()).child("nama").setValue(txtnama)
									.addOnCompleteListener(new OnCompleteListener<Void>() {
								@Override
								public void onComplete(@NonNull Task<Void> task) {
									if (task.isSuccessful()){
										Toast.makeText(getApplicationContext(),
												"Berhasil Menambahkan User Ke Database" , Toast.LENGTH_SHORT).show();
										Intent intent = new Intent(Register.this.getApplicationContext(), Login.class);
										startActivity(intent);
										pb.setVisibility(View.GONE);
										finish();
									}
								}
							});
						}else {
							Toast.makeText(getApplicationContext(), "Gagal Register"
									+ task.getException().getMessage(), Toast.LENGTH_LONG).show();
							pb.setVisibility(View.GONE);
						}
					}
				});
			}
		});
	}
}
