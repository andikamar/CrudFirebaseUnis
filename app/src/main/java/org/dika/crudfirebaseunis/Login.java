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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
	EditText email,password;
	Button login;
	ProgressBar pb;
	FirebaseAuth fa;
	FirebaseDatabase fd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		email=findViewById(R.id.email);
		password=findViewById(R.id.password);
		login=findViewById(R.id.btn_login);
		pb=findViewById(R.id.pb2);
		fa= FirebaseAuth.getInstance();
		fd= FirebaseDatabase.getInstance();
		if (fa.getCurrentUser()!=null){
			pb.setVisibility(View.VISIBLE);
			DatabaseReference dr = fd.getReference("Users").child(fa.getCurrentUser().getUid()).child("nama");
			dr.addValueEventListener(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
					String val = dataSnapshot.getValue(String.class);
					Intent intent = new Intent(Login.this, MainActivity.class);
					intent.putExtra("nama", val);
					intent.putExtra("email", fa.getCurrentUser().getEmail().toString());
					pb.setVisibility(View.GONE);
					startActivity(intent);
				}
				@Override
				public void onCancelled(@NonNull DatabaseError databaseError) {

				}
			});

		}


		login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pb.setVisibility(View.VISIBLE);
				String txtemail=email.getText().toString();
				String txtpass=password.getText().toString();
				if (TextUtils.isEmpty(txtemail)){
					email.setError("Email Tidak Boleh Kosong");
					Toast.makeText(getApplicationContext(),"Email Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
					pb.setVisibility(View.GONE);
					return;
				}
				if (TextUtils.isEmpty(txtpass)){
					password.setError("Password Tidak Boleh Kosong");
					Toast.makeText(getApplicationContext(),"Password Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
					pb.setVisibility(View.GONE);
					return;
				}

				fa.signInWithEmailAndPassword(txtemail,txtpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							DatabaseReference dr = fd.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("nama");
							dr.addValueEventListener(new ValueEventListener() {
								@Override
								public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
									String val=dataSnapshot.getValue(String.class);
									Toast.makeText(getApplicationContext(), "Berhasil Login, Selamat Datang" + val , Toast.LENGTH_LONG).show();
									pb.setVisibility(View.GONE);
									Intent intent = new Intent(Login.this, MainActivity.class);
									intent.putExtra("nama", val);
									intent.putExtra("email", FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());
									startActivity(intent);
								}

								@Override
								public void onCancelled(@NonNull DatabaseError databaseError) {

								}
							});

						}else {
							Toast.makeText(getApplicationContext(), "Gagal Login" + task.getException()
									.getMessage(), Toast.LENGTH_LONG).show();
							pb.setVisibility(View.GONE);
						}
					}
				});
			}
		});
		}
}