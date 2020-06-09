package org.dika.crudfirebaseunis.ui.penduduk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.dika.crudfirebaseunis.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class detPenduduk extends Fragment {
    RecyclerView rv;
    FirebaseRecyclerAdapter fra;
    LinearLayoutManager llm;


    @Override
    public void onStart() {
        super.onStart();
        fra.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        fra.stopListening();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detpen, container, false);

                rv=root.findViewById(R.id.rv);
                llm= new LinearLayoutManager(root.getContext());
                rv.setLayoutManager(llm);
                rv.setHasFixedSize(true);

        Query query = FirebaseDatabase.getInstance().
                getReference("Penduduk");

        FirebaseRecyclerOptions<penduduk> options=new FirebaseRecyclerOptions.Builder<penduduk>()
                .setQuery(query, new SnapshotParser<penduduk>() {
                    @NonNull
                    @Override
                    public penduduk parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return new penduduk(snapshot.child("nik").getValue().toString(),
                                snapshot.child("nama").getValue().toString(),
                                snapshot.child("alamat").getValue().toString(),
                                snapshot.child("hp").getValue().toString()
                                );
                    }
                }).build();

        fra= new FirebaseRecyclerAdapter<penduduk,ViewHolder>(options) {

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_cardpen,parent,false);
                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull final penduduk penduduk) {
                viewHolder.setTnik(penduduk.getNik());
                viewHolder.setTnama(penduduk.getNama());
                viewHolder.setTalamat(penduduk.getAlamat());
                viewHolder.setThp(penduduk.getHp());
                viewHolder.setAedit(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editPenduduk fr = new editPenduduk();
                        Bundle dt = new Bundle();
                        dt.putString("nik",penduduk.getNik());
                        fr.setArguments(dt);

                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.nav_host_fragment,fr);
                        ft.commit();
                    }
                });

                viewHolder.setAdel(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Are you sure?")
                                .setConfirmText("Yes,delete it!")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.setTitleText("Deleted!")
                                                .setConfirmText("OK")
                                                .showCancelButton(false)
                                                .setConfirmClickListener(null);

                                        FirebaseDatabase.getInstance().getReference("Penduduk")
                                                .child(penduduk.getNik()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(getContext(), "Berhasil Menghapus Data", Toast.LENGTH_SHORT).show();
                                                }else {
                                                    Toast.makeText(getContext(), "Gagal Menghapus Data : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                })
                                .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                    }
                                })
                                .show();

                    }
                });
            }
        };


        rv.setAdapter(fra);

        return root;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout root;
        public ImageButton aedit,adel;
        public TextView tnik,tnama,talamat,thp;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            root=itemView.findViewById(R.id.viewcard);
            tnik=itemView.findViewById(R.id.nik);
            tnama=itemView.findViewById(R.id.nama);
            talamat=itemView.findViewById(R.id.alamat);
            thp=itemView.findViewById(R.id.hp);
            aedit=itemView.findViewById(R.id.edit);
            adel=itemView.findViewById(R.id.del);

        }

        public void setAedit(View.OnClickListener clickListener){
            aedit.setOnClickListener(clickListener);
        }
        public void setAdel(View.OnClickListener clickListener){
            adel.setOnClickListener(clickListener);
        }

        public void setTnik(String tnik) {
            this.tnik.setText(tnik);
        }

        public void setTnama(String tnama) {
            this.tnama.setText(tnama);
        }

        public void setTalamat(String talamat) {
            this.talamat.setText(talamat);
        }

        public void setThp(String thp) {
            this.thp.setText(thp);
        }
    }
}
