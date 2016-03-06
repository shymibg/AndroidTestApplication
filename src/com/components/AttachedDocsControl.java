package com.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.dataTypes.KeyValuePair;
import com.main.R;

import java.util.ArrayList;
import java.util.UUID;

public class AttachedDocsControl extends LinearLayout {

    private LinearLayout rowContainer;
    private ArrayList<KeyValuePair<UUID,String>> docTypes;
    private UUID idCardCopyDocTypeKey;
    private LayoutInflater inflater;

    public AttachedDocsControl(Context context) {
        super(context);
        initControl();
    }

    public AttachedDocsControl(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl();
    }

    public AttachedDocsControl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl();
    }

    private void initControl()
    {
        LinearLayout mainView = (LinearLayout) inflate(getContext(), R.layout.attached_docs_control, this);

        rowContainer = (LinearLayout) mainView.findViewById(R.id.containerAttachedDocs);
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Button addRow = (Button) mainView.findViewById(R.id.btnAddRow);
        Button removeRow = (Button) mainView.findViewById(R.id.btnRemoveRow);
        Button saveDocs = (Button) mainView.findViewById(R.id.btnSave);

        addRow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addRow();
            }
        });

        removeRow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                removeRow();
            }
        });

        saveDocs.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<KeyValuePair<UUID,String>> docs = getAttachedDocs();

                StringBuilder docTypes = new StringBuilder();

                for(KeyValuePair<UUID,String> doc:docs){
                    docTypes.append(doc.getKey().toString());
                    docTypes.append("-");
                    docTypes.append(doc.getValue());
                    docTypes.append(";");
                }

                Toast.makeText(getContext(),docTypes.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public void addRow(){
        addRowInt();
    }

    public void addRow(KeyValuePair<UUID,String> attachedDoc){
        if(attachedDoc==null){
            return;
        }

        LinearLayout row = addRowInt();

        Spinner docTypeSelector = (Spinner) row.findViewById(R.id.sDocType);
        EditText docDesc = (EditText) row.findViewById(R.id.etDocDesc);

        setDocTypeSelectorSelection(docTypeSelector, attachedDoc.getKey());

        setDocDescription(docDesc, attachedDoc.getValue());
    }

    public void removeRow(){
        int rowCount = rowContainer.getChildCount();

        if(rowCount <= 1){
            return;
        }

        rowContainer.removeViewAt(rowCount-1);
    }

    public ArrayList<KeyValuePair<UUID,String>> getAttachedDocs(){
        ArrayList<KeyValuePair<UUID,String>> attachedDocs = new ArrayList<>();
        int rowCount = rowContainer.getChildCount();

        for (int i=0; i<rowCount; i++){
            View row = rowContainer.getChildAt(i);

            Spinner docTypeSelector = (Spinner) row.findViewById(R.id.sDocType);
            EditText docDescription = (EditText) row.findViewById(R.id.etDocDesc);

            UUID docType = ((KeyValuePair<UUID,String>)docTypeSelector.getSelectedItem()).getKey();
            String docDesc = docDescription.getText().toString();

            attachedDocs.add(new KeyValuePair<>(docType,docDesc));
        }

        return attachedDocs;
    }

    public ArrayList<KeyValuePair<UUID, String>> getDocTypes() {
        return docTypes;
    }

    public void setDocTypes(ArrayList<KeyValuePair<UUID, String>> docTypes) {
        this.docTypes = docTypes;
    }

    private void setDocTypeSelector(Spinner docTypeSelector) {

        ArrayAdapter<KeyValuePair<UUID,String>> dataAdapter =
                new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, docTypes);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        docTypeSelector.setAdapter(dataAdapter);
    }

    public UUID getIdCardCopyDocTypeKey() {
        return idCardCopyDocTypeKey;
    }

    public void setIdCardCopyDocTypeKey(UUID idCardCopyDocTypeKey) {
        this.idCardCopyDocTypeKey = idCardCopyDocTypeKey;
    }

    private void setDocTypeSelectorSelection(Spinner docTypeSelector, UUID docType){

        if(docType == null){
            return;
        }

        if(docTypeSelector.getAdapter() == null){
            return;
        }

        ArrayAdapter<KeyValuePair<UUID,String>> docTypes =
                (ArrayAdapter<KeyValuePair<UUID, String>>) docTypeSelector.getAdapter();

        int selectedItemPosition = -1;

        for(int i=0;i<docTypes.getCount();i++){
            KeyValuePair<UUID,String> item = docTypes.getItem(i);
            if(item.getKey().equals(docType)){
                selectedItemPosition = i;
                break;
            }
        }

        if(selectedItemPosition!=-1) {
            docTypeSelector.setSelection(selectedItemPosition);
        }
    }

    private void setDocDescription(EditText docDesc, String value) {
        if(value==null){
            return;
        }

        docDesc.setText(value);
    }

    private LinearLayout addRowInt(){

        LinearLayout row = (LinearLayout) inflater.inflate(R.layout.attached_docs_row,rowContainer,false);
        Spinner docTypeSelector = (Spinner) row.findViewById(R.id.sDocType);

        if(docTypes!=null) {
            setDocTypeSelector(docTypeSelector);
        }

        EditText docDescription = (EditText) row.findViewById(R.id.etDocDesc);

        docTypeSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                KeyValuePair<UUID,String> selectedDocType = (KeyValuePair<UUID, String>) parent.getItemAtPosition(position);

                if(selectedDocType!= null
                        && selectedDocType.getKey()!=null
                        && selectedDocType.getKey().equals(getIdCardCopyDocTypeKey()))
                {
                    docDescription.setText("");
                    docDescription.setEnabled(false);
                }
                else{
                    docDescription.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        rowContainer.addView(row);

        return row;
    }

}
