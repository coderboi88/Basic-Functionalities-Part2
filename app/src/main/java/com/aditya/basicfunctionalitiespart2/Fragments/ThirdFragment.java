package com.aditya.basicfunctionalitiespart2.Fragments;

import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aditya.basicfunctionalitiespart2.R;
import com.shreyaspatil.EasyUpiPayment.EasyUpiPayment;
import com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener;
import com.shreyaspatil.EasyUpiPayment.model.TransactionDetails;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ThirdFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ThirdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThirdFragment extends Fragment implements PaymentStatusListener{

    private EditText PickDate,AmountText,UpiText,NameText,DescriptionText;
    private Button PayButton;
    private TextView TranscationDetailTextView;
    private int year,month,day;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ThirdFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThirdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThirdFragment newInstance(String param1, String param2) {
        ThirdFragment fragment = new ThirdFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_third, container, false);

        PickDate=view.findViewById(R.id.pickDate);

        AmountText = view.findViewById(R.id.amountEditText);
        NameText = view.findViewById(R.id.nameEditText);
        UpiText = view.findViewById(R.id.upiIdEditText);
        DescriptionText = view.findViewById(R.id.descriptionEditText);
        PayButton = view.findViewById(R.id.makePaymentBtn);
        TranscationDetailTextView = view.findViewById(R.id.transactionDetailsTextView);

        final Calendar calendar=Calendar.getInstance();
        PickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                day=calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        PickDate.setText(dayOfMonth+"-" + (month+1)+"-"+year);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault());
        final String transcId = df.format(c);

        PayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = AmountText.getText().toString();
                String upi = UpiText.getText().toString();
                String name = NameText.getText().toString();
                String desc = DescriptionText.getText().toString();
                if (TextUtils.isEmpty(amount) && TextUtils.isEmpty(upi) && TextUtils.isEmpty(name) && TextUtils.isEmpty(desc)) {
                    Toast.makeText(getContext(), "Please enter all the details..", Toast.LENGTH_SHORT).show();
                } else {
                    makePayment(amount, upi, name, desc, transcId);
                }
            }
        });

        return view;
    }

    //It has been Copied from the gfg
    private void makePayment(String amount, String upi, String name, String desc, String transcId) {
        final EasyUpiPayment easyUpiPayment = new EasyUpiPayment.Builder()
                .with(getActivity())
                .setPayeeVpa(upi)
                .setPayeeName(name)
                .setTransactionId(transcId)
                .setTransactionRefId(transcId)
                .setDescription(desc)
                .setAmount(amount)
                .build();
        easyUpiPayment.startPayment();
        easyUpiPayment.setPaymentStatusListener((PaymentStatusListener) getActivity());
    }

    @Override
    public void onTransactionCompleted(TransactionDetails transactionDetails) {
        String transcDetails = transactionDetails.getStatus().toString() + "\n" + "Transaction ID : " + transactionDetails.getTransactionId();
        TranscationDetailTextView.setVisibility(View.VISIBLE);
        TranscationDetailTextView.setText(transcDetails);
    }

    @Override
    public void onTransactionSuccess() {
        Toast.makeText(getContext(), "Transaction successfully completed..", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTransactionSubmitted() {
        Log.e("TAG", "TRANSACTION SUBMIT");
    }

    @Override
    public void onTransactionFailed() {
        Toast.makeText(getContext(), "Failed to complete transaction", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTransactionCancelled() {
        Toast.makeText(getContext(), "Transaction cancelled..", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAppNotFound() {
        Toast.makeText(getContext(), "No app found for making transaction..", Toast.LENGTH_SHORT).show();
    }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
