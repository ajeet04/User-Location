package com.assignment.leaf.leaf;

import android.Manifest;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.joinersa.oooalertdialog.Animation;
import br.com.joinersa.oooalertdialog.OnClickListener;
import br.com.joinersa.oooalertdialog.OoOAlertDialog;

public class MainActivity extends AppCompatActivity {



// for the reference please go through with the link  https://github.com/dirkam/backgroundable-android
    private static final Intent[] AUTO_START_INTENTS = {
            new Intent().setComponent(new ComponentName("com.samsung.android.lool",
                    "com.samsung.android.sm.ui.battery.BatteryActivity")),
            new Intent("miui.intent.action.OP_AUTO_START").addCategory(Intent.CATEGORY_DEFAULT),
            new Intent().setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity")),
            new Intent().setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.AutobootManageActivity")),
            new Intent().setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity")),
            new Intent().setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity")),
            new Intent().setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.startupapp.StartupAppListActivity")),
            new Intent().setComponent(new ComponentName("com.oppo.safe", "com.oppo.safe.permission.startup.StartupAppListActivity")),
            new Intent().setComponent(new ComponentName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity")),
            new Intent().setComponent(new ComponentName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager")),
            new Intent().setComponent(new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity")),
            new Intent().setComponent(new ComponentName("com.asus.mobilemanager", "com.asus.mobilemanager.entry.FunctionActivity")).setData(
                    Uri.parse("mobilemanager://function/entry/AutoStart"))
    };

private EditText numberResult;
private Button loadButton;
public static ProgressDialog mProgress;
public static List<Model> dataList=new ArrayList<>();
private RecyclerView mListView;
private LinearLayoutManager mLayoutManager;
static String url="https://randomuser.me/api/?inc=name,email,dob,phone,picture&results=50";
String urlEdit="https://randomuser.me/api/?inc=name,email,dob,phone,picture&results=";



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //for Above M ..................

        if(Build.VERSION.SDK_INT>=23) {
            allowLocationPermission();
        }

        mListView=(RecyclerView)findViewById(R.id.list);
        mLayoutManager=new LinearLayoutManager(this);
        mListView.setLayoutManager(mLayoutManager);


        //getSupportActionBar().getTitle()


       mProgress =new ProgressDialog(this);
        mProgress.setMessage("Fetching data....");
        mProgress.setCanceledOnTouchOutside(false);

        // execute API..............................

        BackgroundTask asyncTask=new BackgroundTask(this,mListView);
        asyncTask.execute(url);


     numberResult=(EditText)findViewById(R.id.number);
     loadButton =(Button)findViewById(R.id.load_bttn);

     loadButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             String num=numberResult.getText().toString();
             int numm=Integer.parseInt(num);
             if(numm>0){
                 try  {
                     // Close SoftKey......................

                     InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                     imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                     String urll="https://randomuser.me/api/?inc=name,email,dob,phone,picture&results="+numm;
                     dataList.clear();
                     BackgroundTask asyncTask=new BackgroundTask(MainActivity.this,mListView);
                     asyncTask.execute(urll);

                 } catch (Exception e) {

                 }



             }

         }
     });





    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void allowLocationPermission() {

        // if permission not granted....

        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission
                    .ACCESS_FINE_LOCATION
            },1);

        }

        else{
            runServices();
        }



    }

    private void runServices() {
        if(!isMyServiceRunning(MyLocationService.class)) {


            Intent myService = new Intent(this, MyLocationService.class);
            if (Build.VERSION.SDK_INT >= 26) {
                startForegroundService(myService);
            } else
                startService(myService);
        }
        else{

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       if(requestCode==1 && grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED  ){

           runServices();


           NotifyToAddAppstartUp();

       }

       else{
           Toast.makeText(this, "Please allow Location Permission.....", Toast.LENGTH_SHORT).show();
       }
    }
         // add app in startup......

    private void NotifyToAddAppstartUp() {
       OoOAlertDialog.Builder aw= new OoOAlertDialog.Builder(MainActivity.this);
               aw.setTitle("Add to startup").setCancelable(false)
                .setMessage("App will use background services and it will execute on boot, so you need to add this app on startup. ")
                       .setPositiveButton("Ok", new OnClickListener() {
                    @Override
                    public void onClick() {
                        for (Intent intent : AUTO_START_INTENTS){
                            if (getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
                                startActivity(intent);
                                break;
                            }
                        }


                    }
                })
                .setNegativeButton("Cancel", new OnClickListener() {
                    @Override
                    public void onClick() {

                    }
                })
                .build();


    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_options_menu, menu);

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public boolean onOptionsItemSelected(MenuItem item) {

      if(item.getItemId()==R.id.name){

          Model.NameComparator comparator = new Model.NameComparator();
          Collections.sort(dataList, comparator);
          MyAdapter myAdapter=new MyAdapter(dataList,MainActivity.this);
          mListView.setAdapter(myAdapter);
      }
      if(item.getItemId()==R.id.email){
          Model.EmailComparator comparator = new Model.EmailComparator();
          Collections.sort(dataList, comparator);
          MyAdapter myAdapter=new MyAdapter(dataList,MainActivity.this);
          mListView.setAdapter(myAdapter);
      }
        if(item.getItemId()==R.id.mobile){
            Model.PhoneComparator comparator = new Model.PhoneComparator();
            Collections.sort(dataList, comparator);
            MyAdapter myAdapter=new MyAdapter(dataList,MainActivity.this);
            mListView.setAdapter(myAdapter);
        }
        if(item.getItemId()==R.id.dob){
            Model.DOBComparator comparator = new Model.DOBComparator();
            Collections.sort(dataList, comparator);
            MyAdapter myAdapter=new MyAdapter(dataList,MainActivity.this);
            mListView.setAdapter(myAdapter);
        }

        return super.onOptionsItemSelected(item);
    }

            // check service is running.....................

        private boolean isMyServiceRunning(Class<?> serviceClass) {

        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
