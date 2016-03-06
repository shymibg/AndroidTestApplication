package com.main;

import android.app.Activity;
import android.os.Bundle;
import com.com.dataTypes.KeyValuePair;
import com.components.AttachedDocsControl;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class MainActivity extends Activity {

    private AttachedDocsControl attachedDocs;
    private KeyValuePair<UUID, String> idCardCoppy;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        attachedDocs = (AttachedDocsControl) findViewById(R.id.attachedDocsControl);
        attachedDocs.setIdCardCopyDocTypeKey(getIdCardCopy().getKey());
        attachedDocs.setDocTypes(getDocTypes());
    }

    private ArrayList<KeyValuePair<UUID,String>> getDocTypes() {
        ArrayList<KeyValuePair<UUID,String>> docTypes = new ArrayList<>();
        Random random = new Random();
        int numberOfDoc = random.nextInt(15) + 5;

        docTypes.add(getIdCardCopy());

        for (int i =0; i<numberOfDoc; i++){
            docTypes.add(new KeyValuePair<>(UUID.randomUUID(),"doctype" + String.valueOf(i)));
        }

        return docTypes;
    }

    public KeyValuePair<UUID, String> getIdCardCopy() {
        if(idCardCoppy==null){
            idCardCoppy = new KeyValuePair<>(UUID.randomUUID(),"IdCardCopy");
        }

        return idCardCoppy;
    }
}
