package com.juanitarouse.pollme;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.juanitarouse.pollme.Views.AnswerDetails;
import com.juanitarouse.pollme.Views.AnswerView;
import com.juanitarouse.pollme.Views.Contacts;
import com.juanitarouse.pollme.Views.HistoryView;
import com.juanitarouse.pollme.Views.QuestionView;
import com.juanitarouse.pollme.model.Answer;
import com.juanitarouse.pollme.model.Contact;
import com.juanitarouse.pollme.model.Question;
import com.juanitarouse.pollme.model.ToSend;

import io.realm.Realm;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, QuestionView.OnFragmentInteractionListener, AnswerView.OnFragmentInteractionListener, AnswerDetails.OnFragmentInteractionListener, Contacts.OnFragmentInteractionListener , HistoryView.OnFragmentInteractionListener{

    private Realm myRealm;

    Question questionToSend = new Question();
    RealmResults<Answer> answerList;
    RealmResults<Contact> contactsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myRealm = Realm.getDefaultInstance();



        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setEnabled(false);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Sending Message", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


                ToSend thisQuestion = myRealm.where(ToSend.class).equalTo("selected" , true).isNotNull("id").findFirst();


                if (thisQuestion  !=  null) {
                    questionToSend = myRealm.where(Question.class).equalTo("id" , thisQuestion.getQuestionToSend()).isNotNull("id").findFirst();
                    String idQuestion = questionToSend.getId();
                    answerList = myRealm.where(Answer.class).equalTo("QuestionId", idQuestion).findAll();
                    contactsList = myRealm.where(Contact.class).findAll();

                }

                if(!questionToSend.equals(null)){
                    String phoneNumber = "";
                    for (Contact contact : contactsList) {

                        phoneNumber = phoneNumber + contact.getPhone()+";";

                    }


                    String smsBody = "";

                    smsBody = smsBody + "Pregunta:" + questionToSend.getBody() + ".\n";


                    smsBody = smsBody + "Cual seria tu respuesta:" + ".\n";

                    for (Answer answer : answerList) {

                        smsBody = smsBody + "*:" + answer.getBodyAnswer() + ".\n";

                    }


                    Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                    // Invokes only SMS/MMS clients
                    smsIntent.setType("vnd.android-dir/mms-sms");
                    // Specify the Phone Number
                    smsIntent.putExtra("address", phoneNumber);
                    // Specify the Message
                    smsIntent.putExtra("sms_body", smsBody);

                    // Shoot!
                    startActivity(smsIntent);
                }
                else{
                    Snackbar.make(view, "There is no a valid question to send", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = new Fragment();
        FragmentManager manager = getSupportFragmentManager();
        Boolean selectedFragment = false;

        if (id == R.id.nav_question) {
            fragment = new QuestionView();
            Toast.makeText(this,"Create your question here", Toast.LENGTH_SHORT).show();
            selectedFragment = true;
            // Handle the camera action
        } else if (id == R.id.nav_results) {
            Toast.makeText(this,"Results", Toast.LENGTH_SHORT).show();
            selectedFragment = true;

        }
        else if (id == R.id.nav_groups) {
            Toast.makeText(this,"Groups", Toast.LENGTH_SHORT).show();
            selectedFragment = true;

        }else if (id == R.id.nav_history) {
            fragment = new HistoryView();
            Toast.makeText(this,"History", Toast.LENGTH_SHORT).show();
            selectedFragment = true;

        }else if (id == R.id.nav_tools) {
            Toast.makeText(this,"Tools", Toast.LENGTH_SHORT).show();
            selectedFragment = true;

        }else if (id == R.id.nav_contacts) {
            fragment = new Contacts();
            Toast.makeText(this,"Contacts", Toast.LENGTH_SHORT).show();
            selectedFragment = true;

        }else if (id == R.id.clear_db) {
            Toast.makeText(this,"Cleaning DB", Toast.LENGTH_SHORT).show();
        }


        if (selectedFragment){
            manager.beginTransaction().replace(R.id.Contenedor,fragment
            ).commit();

            item.setChecked(true);
            getSupportActionBar().setTitle(item.getTitle());
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void CleanDb(){
        myRealm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm) {

                Realm.deleteRealm(myRealm.getConfiguration());

                /*RealmResults answerElement = myRealm.where(Answer.class).findAll();
                answerElement.deleteAllFromRealm();
                RealmResults questionElement = myRealm.where(Question.class).findAll();
                questionElement.deleteAllFromRealm();
                RealmResults toSendElement = myRealm.where(ToSend.class).findAll();
                toSendElement.deleteAllFromRealm();
                RealmResults contactElement = myRealm.where(Contact.class).findAll();
                contactElement.deleteAllFromRealm();*/






                Toast.makeText(getApplication().getBaseContext(),"Question has been added", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
