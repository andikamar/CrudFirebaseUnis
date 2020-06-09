package org.dika.crudfirebaseunis.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import org.dika.crudfirebaseunis.Login;
import org.dika.crudfirebaseunis.R;


public class HomeFragment extends Fragment {

	private Button keluar;
	private FirebaseAuth auth;


	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {

		auth= FirebaseAuth.getInstance();

		View root = inflater.inflate(R.layout.fragment_home, container, false);
		final TextView textView = root.findViewById(R.id.text_home);
		textView.setText("Selamat Datang Di Menu Utama");

		keluar = root.findViewById(R.id.btn_keluar);
		keluar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				auth.signOut();
				Intent intent = new Intent(HomeFragment.this.getActivity(), Login.class);
				startActivity(intent);
				Toast.makeText(getContext(),"Anda Telah Sign Out", Toast.LENGTH_LONG).show();
			}
		});
		return root;

	}
}