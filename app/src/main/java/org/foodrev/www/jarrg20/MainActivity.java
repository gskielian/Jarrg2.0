package org.foodrev.www.jarrg20;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import android.app.VoiceInteractor;
import android.app.VoiceInteractor.PickOptionRequest;
import android.app.VoiceInteractor.PickOptionRequest.Option;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity {
    int decision;
    int indexChosen;

    @Override
    public void onResume() {
        super.onResume();
        if (isVoiceInteraction()) {
//            voiceInteraction("you're awesome","I'm awesome", "who's awesome?");
            voiceInteraction("sudo get me a sandwich","make me a sandwich", "Sup");
//            voiceInteraction("you're awesome","I'm awesome", "who's awesome?");
        }
    }


    private int voiceInteraction(String first_option, String second_option, String question) {
        Option[] options = new Option[]
                {
                        new Option(first_option,0),
                        new Option(second_option,1)
                };

        getVoiceInteractor()
                .submitRequest(new PickOptionRequest(new VoiceInteractor.Prompt(question), options, null) {
                    @Override
                    public void onPickOptionResult(boolean finished, Option[] selections, Bundle result) {
                        if (finished && selections.length == 1) {
                            Message message = Message.obtain();
                            message.obj = result;
                            Toast.makeText(this.getContext(),selections[0].getLabel() + " " + String.valueOf(selections[0].getIndex()),Toast.LENGTH_LONG).show();
                            indexChosen =  selections[0].getIndex();
                            Bundle extras = new Bundle();
                            if(indexChosen == 1) {
                                getVoiceInteractor()
                                        .submitRequest(new VoiceInteractor.CompleteVoiceRequest(new VoiceInteractor.Prompt("make your own sandwich"), extras) {
                                        });
                            } else {
                                getVoiceInteractor()
                                        .submitRequest(new VoiceInteractor.CompleteVoiceRequest(new VoiceInteractor.Prompt("Okay"), extras) {
                                        });
                            }
//                            finish();
                            indexChosen = -1;
                        }

                    }
                    @Override
                    public void onCancel() {
                        getActivity().finish();
                        indexChosen = -1;
                    }
                });

        return indexChosen;
    }

    private void followUpQuestion() {
        Option[] options = new Option[]
                {
                        new Option("to search for the holy grail",0),
                        new Option("create a modular phone",1)
                };

        getVoiceInteractor()
                .submitRequest(new PickOptionRequest(new VoiceInteractor.Prompt("what is your quest?"), options, null) {
                    @Override
                    public void onPickOptionResult(boolean finished, Option[] selections, Bundle result) {
                        if (finished && selections.length == 1) {
                            Message message = Message.obtain();
                            message.obj = result;
                            Toast.makeText(this.getContext(),"Nothing",Toast.LENGTH_LONG).show();
                        } else {
                            finish();
                        }

                    }
                    @Override
                    public void onCancel() {
                        getActivity().finish();
                    }
                });
    }


}
