package org.dika.crudfirebaseunis.ui.penduduk;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.dika.crudfirebaseunis.R;

public class editPenduduk extends Fragment {

    EditText txtnama,txtalamat,txthp,txtnik;
    Button btnsimpan;
    FirebaseDatabase fd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_edit, container, false);

        txtnama=root.findViewById(R.id.txtnama);
        txtalamat=root.findViewById(R.id.txtalamat);
        txthp=root.findViewById(R.id.txthp);
        txtnik=root.findViewById(R.id.txtnik);
        btnsimpan=root.findViewById(R.id.btnsimpan);
        Bundle dt = getArguments();
        final String fnik =dt.getString("nik");

        FirebaseDatabase.getInstance().getReference("Penduduk")
                .child(fnik).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                txtnama.setText(dataSnapshot.child("nama").getValue().toString());
                txtalamat.setText(dataSnapshot.child("alamat").getValue().toString());
                txthp.setText(dataSnapshot.child("hp").getValue().toString());
                txtnik.setText(fnik);
                txtnik.setEnabled(false);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fd= FirebaseDatabase.getInstance();

        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama=txtnama.getText().toString();
                String alamat=txtalamat.getText().toString();
                String hp=txthp.getText().toString();

                if (TextUtils.isEmpty(nama)) {
                    txtnama.setError("Nama Harus Di Isi");
                    Toast.makeText(getContext(),"Nama Harus Di Isi", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(alamat)) {
                    txtalamat.setError("Alamat Harus Di Isi");
                    Toast.makeText(getContext(),"Alamat Harus Di Isi", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(hp)) {
                    txthp.setError("HP Harus Di Isi");
                    Toast.makeText(getContext(),"HP Harus Di Isi", Toast.LENGTH_LONG).show();
                    return;
                }

                penduduk pen=new penduduk(fnik,nama,alamat,hp);

                fd.getReference("Penduduk").child(fnik).setValue(pen)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(),"Berhasil Merubah Data"
                                            , Toast.LENGTH_SHORT).show();


                                    getFragmentManager().beginTransaction()
                                            .replace(R.id.nav_host_fragment, new detPenduduk()).commit();

                                }else{
                                    Toast.makeText(getContext(),"Gagal Merubah Data:"+task.getException().getMessage()
                                            ,Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });

        return root;
    }
}
