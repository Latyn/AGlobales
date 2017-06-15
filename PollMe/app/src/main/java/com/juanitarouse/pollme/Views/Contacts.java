package com.juanitarouse.pollme.Views;
import android.view.View;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.juanitarouse.pollme.R;
import com.juanitarouse.pollme.model.Answer;
import com.juanitarouse.pollme.model.Contact;
import com.juanitarouse.pollme.model.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Contacts.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Contacts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Contacts extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Realm myRealm;
    public Contacts() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Contacts.
     */
    // TODO: Rename and change types and number of parameters
    public static Contacts newInstance(String param1, String param2) {
        Contacts fragment = new Contacts();
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
        myRealm = Realm.getDefaultInstance();
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);

        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);

        //startActivityForResult(intent, 1);
        startActivityForResult(Intent.createChooser(intent,"Select Contact"), 1);
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Uri uri = data.getData();
            Log.i("data", uri.toString());
            if (uri != null) {
                Cursor c = null;
                try {
                    c = this.getActivity().getContentResolver().query(uri, new String[]{
                                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                                    ContactsContract.CommonDataKinds.Phone.TYPE },
                            null, null, null);

                    if (c != null && c.moveToFirst()) {
                        String name = c.getString(0);
                        String number = c.getString(1);
                        int type = c.getInt(2);

                        showSelectedNumber(name, number,type);
                    }
                } finally {
                    if (c != null) {
                        c.close();
                    }
                }
            }
        }
    }
    public void showSelectedNumber(String name,  String number, int type){
        ListView contactListView = (ListView) this.getView().findViewById(R.id.contacts_list);


        Contact myContact = new Contact();

        myContact.setName(name);
        myContact.setPhone(number);
        // Create a List from String Array elements
        final List<Contact> contactList = new ArrayList<Contact>();
        contactList.add(myContact);

        ArrayList <String> StringValues = new ArrayList <String>();

        for (final Contact cont:contactList){
            StringValues.add(cont.getName() + " - " +cont.getPhone());
            final String Id = UUID.randomUUID().toString();
            myRealm.executeTransaction(new Realm.Transaction(){
                @Override
                public void execute(Realm realm) {
                    Contact realmQuestion = myRealm.createObject(Contact.class, Id);
                    realmQuestion.setName(cont.getName());
                    realmQuestion.setPhone(cont.getPhone());

                    Toast.makeText(getContext(),"Phone added", Toast.LENGTH_SHORT).show();
                }
            });
        }
        // Create an ArrayAdapter from List
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this.getContext(), android.R.layout.simple_list_item_1, StringValues);


        contactListView.setAdapter(arrayAdapter);

        //Get Phone type based on enum type
        //String typelabel = (String) ContactsContract.CommonDataKinds.Phone.getTypeLabel(getResources(), type, "");
        //contactList.add(name+": "+number+" "+typelabel);

    }
}
