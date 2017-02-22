package com.example.happy.myapplication;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.happy.myapplication.Eventmanagerlogin.getContextOfApplication;

/**
 * Created by Hp on 22-02-2017.
 */

public class post_fragment extends Fragment {
    EditText e1,e2,e3;
    ImageButton ib1,ib2;
    Button b1;
    ImageView iv1;
    int requestcode = 1111,PICK=1,i=0;
    Context applicationContext;
    Spinner s;
    String area,filename;
    HttpClient httpclient;
    HttpPost httppost;
    ResponseHandler<String> response;
    List<NameValuePair> nameValuePairs;
    int permission,per1;
    File file = null;
    private static final String MULTIPART_FORM_DATA = "multipart/form-data";
    final String[] choices = {"Select","North Coimbatore","Kavundampalayam","Saravanampatti","rspuram","Gandhipuram","Saibabacolony","Thudiyalur"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View v = inflater.inflate(R.layout.post_fragment, container, false);
        b1 = (Button) v.findViewById(R.id.button);
        s=(Spinner)v.findViewById(R.id.spinner);
        applicationContext =Eventmanagerlogin.getContextOfApplication();
        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
        permission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
        per1 = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        e1 = (EditText)v.findViewById(R.id.e1);
        e2 = (EditText)v.findViewById(R.id.e2);
        e3 = (EditText)v.findViewById(R.id.e3);
        ArrayAdapter<String> a = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, choices);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(a);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                area = (String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        nameValuePairs = new ArrayList<NameValuePair>();
        response = new BasicResponseHandler();
        ib1 = (ImageButton)v.findViewById(R.id.ib1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(photoCaptureIntent, requestcode);
            }
        });
        ib2 = (ImageButton)v.findViewById(R.id.ib2);
        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select File"),PICK);
            }
        });
        iv1 = (ImageView)v.findViewById(R.id.iv1);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (file !=null) {
                    sa(file.getAbsolutePath());
                }else{
                    Toast.makeText(getActivity(), "Please upload your image", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == requestcode) {
            //2
            i++;
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            iv1.setImageBitmap(thumbnail);
            iv1.setImageTintMode(PorterDuff.Mode.SRC_IN);
            //3
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            //4
            filename = "image"+i+".jpg";
            file = new File(Environment.getExternalStorageDirectory() + File.separator + filename);

            try {
                file.createNewFile();
                FileOutputStream fo = new FileOutputStream(file);
                //5
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }else{
            try {
                String realPath = getRealPath(data.getData());
                file = new File(realPath);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public String getRealPath(Uri uri){

        String realPathFromURI_api19 = RealPathUtils.getRealPathFromURI_API19(getActivity(), uri);
        return realPathFromURI_api19;
    }
    public void sa(String fileUrl) {


        // Change base URL to your upload server URL.
        Retrofit build = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2")
                .build();
        ApiService service = build.create(ApiService.class);
        File file = new File(fileUrl);
        RequestBody reqFile  = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);

        // create a map of data to pass along

        RequestBody fileUrlReq = createPartFromString(fileUrl);
        RequestBody complaintReq = createPartFromString(e1.getText().toString());
        RequestBody descReq = createPartFromString(e2.getText().toString());
        RequestBody areaReq = createPartFromString(area);
        RequestBody landReq = createPartFromString(e3.getText().toString());


        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("imagename", fileUrlReq);
        map.put("complaint", complaintReq);
        map.put("desc", descReq);
        map.put("area", areaReq);
        map.put("land", landReq);

        Call<ResponseBody> call = service.uploadImageWithPartMap(map, body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // Do
                if(response.isSuccessful()){
                    System.out.println(response.body().toString()+":Successful");
                    System.out.println("Url:"+response.raw().request().url());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }
    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                MediaType.parse(MULTIPART_FORM_DATA), descriptionString);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Make complaints");
    }

}
