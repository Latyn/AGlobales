package com.juanitarouse.pollme.Views;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.juanitarouse.pollme.R;
import com.juanitarouse.pollme.model.Answer;
import com.juanitarouse.pollme.model.Question;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AnswerView.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AnswerView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnswerView extends Fragment implements AnswerDetails.OnFragmentInteractionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button btnAnswer;
    Activity activity;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private Realm myRealm;

    public AnswerView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnswerView.
     */
    // TODO: Rename and change types and number of parameters
    public static AnswerView newInstance(String param1, String param2) {
        AnswerView fragment = new AnswerView();
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
        myRealm = Realm.getDefaultInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_answer_view, container, false);
        final View mainView = inflater.inflate(R.layout.fragment_question_view, container, false); //enlazo elemento a la vista y retorno el view linea 74 para poder utilizar el FindViewBy
        btnAnswer = (Button)view.findViewById(R.id.addAnswer);


        btnAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //activity = getActivity();
                //Function to add answer when clicking
                Toast.makeText(getContext(),"Adding Answer", Toast.LENGTH_SHORT).show();
                FragmentManager manager = getFragmentManager();
                AnswerDetails details = new AnswerDetails();


               // RealmQuery<Answer> answers = myRealm.where(Answer.class);
               // RealmResults results = answers.findAll();

               // manager.beginTransaction().add(R.id.answersFragments, details, getTag()).commit();
                addQuestionToReal(mainView);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    public void addQuestionToReal(View view){

            final String Id = UUID.randomUUID().toString();
            final String answerId = UUID.randomUUID().toString();
            EditText question = (EditText) this.getActivity().findViewById(R.id.editQuestion);
            EditText answer = (EditText) this.getActivity().findViewById(R.id.editAnswer1);
            final String questionBody = question.getText().toString();
            final String answerBody = answer.getText().toString();




        myRealm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm) {
                Question realmQuestion = myRealm.createObject(Question.class, Id);
                realmQuestion.setBody(questionBody);

                Answer realmAnswer = myRealm.createObject(Answer.class, answerId);
                realmAnswer.setBodyAnswer(answerBody);
                realmQuestion.setAnswers(realmAnswer);


                Toast.makeText(getContext(),"Question has been added", Toast.LENGTH_SHORT).show();
            }
        });

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

    @Override
    public void onFragmentInteraction(Uri uri) {
        //You can leave it empty
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
