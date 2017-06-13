package com.juanitarouse.pollme.Views;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.juanitarouse.pollme.R;
import com.juanitarouse.pollme.model.Contact;
import com.juanitarouse.pollme.model.Question;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HistoryView.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistoryView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryView extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Realm myRealm;

    private OnFragmentInteractionListener mListener;

    public HistoryView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryView.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryView newInstance(String param1, String param2) {
        HistoryView fragment = new HistoryView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
   public void displayQuestionHistory(){
      RealmResults<Question> questionList = myRealm.where(Question.class).findAll();


       ListView listHistoryView = (ListView) this.getActivity().findViewById(R.id.history_list);
       ArrayList<String> listOfQuestions = new ArrayList<String>();

       if (questionList!= null) {
           for (Question question : questionList) {

               String id = question.getId();
               String Body = question.getBody();
               listOfQuestions.add(id + Body);

           }
       }

       if (listOfQuestions!= null) {
           ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                   (this.getActivity(), android.R.layout.simple_list_item_1, listOfQuestions);
           listHistoryView.setAdapter(arrayAdapter);
       }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        myRealm = Realm.getDefaultInstance();
        displayQuestionHistory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history_view, container, false);
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
}
