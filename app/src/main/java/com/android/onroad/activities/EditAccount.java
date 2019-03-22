package com.android.onroad.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.onroad.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditAccount extends Fragment {
View view;
CircleImageView profileImg;
EditText userName,email,password,editImage;
Button editAccountBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_edit_account, container, false);
        profileImg=view.findViewById(R.id.imageView);
        editImage=view.findViewById(R.id.editImage);
        userName=view.findViewById(R.id.edtUserName);
        email=view.findViewById(R.id.edtEmail);
        password=view.findViewById(R.id.edtPassword);
        editAccountBtn=view.findViewById(R.id.btnEditAccount);
        









        return  view;
    }
}
