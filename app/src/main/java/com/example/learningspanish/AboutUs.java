package com.example.learningspanish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class AboutUs extends FragmentActivity implements OnMapReadyCallback {

    Button btn;
    GoogleMap mapAPI;
    SupportMapFragment mapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapAPI);
        mapFragment.getMapAsync(this);


        btn = findViewById(R.id.feedback_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogue();
            }
        });
    }

    private void showDialogue(){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Feedback");
        dialog.setMessage("Provide us your valueable feedback");
        LayoutInflater inflater = LayoutInflater.from(this);

        View  reg_layout = inflater.inflate(R.layout.send_feedback,null);

        final MaterialEditText editMail = reg_layout.findViewById(R.id.edtEmail);
        final MaterialEditText editName = reg_layout.findViewById(R.id.edtName);
        final MaterialEditText editFeedback = reg_layout.findViewById(R.id.edtFeedback);
        dialog.setView(reg_layout);

        //set Button
        dialog.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (TextUtils.isEmpty(editMail.getText().toString())){
                    Toast.makeText(AboutUs.this,"please tape your email...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editName.getText().toString())){
                    Toast.makeText(AboutUs.this,"Name field can not be empty...",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editFeedback.getText().toString())){
                    Toast.makeText(AboutUs.this,"Feedback field can not be empty...",Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Object value = dataSnapshot.getValue();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(AboutUs.this,"Field to read Value...",Toast.LENGTH_SHORT).show();
                    }
                });

                myRef.child("Users").child(editName.getText().toString()).child("Email").setValue(editMail.getText().toString());
                myRef.child("Users").child(editName.getText().toString()).child("Feedback").setValue(editFeedback.getText().toString());
                myRef.child("Users").child(editName.getText().toString()).child("Name").setValue(editName.getText().toString());

                Toast.makeText(AboutUs.this,"Thanx For your feedback...",Toast.LENGTH_SHORT).show();
            }
        });


        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapAPI =googleMap;
        LatLng home = new LatLng(38.375604, -0.492128);
        mapAPI.addMarker(new MarkerOptions().position(home).title("Learning Spanish"));
        mapAPI.moveCamera(CameraUpdateFactory.newLatLngZoom(home,17));
    }
}