package com.future.lobajo.Activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.future.lobajo.MainBottomNavigation;
import com.future.lobajo.MyGeoReferencedARObject;
import com.future.lobajo.R;
import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Pose;
import com.google.ar.core.Session;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.SceneView;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import uk.co.appoly.arcorelocation.LocationMarker;
import uk.co.appoly.arcorelocation.LocationScene;

public class ARActivity extends AppCompatActivity {

    private static final String TAG = ARActivity.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;

    ArFragment arFragment;
    ModelRenderable diceRenderable;

    Renderable redSphereRenderable;
    Renderable textRenderable;

    boolean populated = false;

    LocationScene locationScene = null;

    @TargetApi(Build.VERSION_CODES.N) //different from tutorial
    @Override
    @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!checkIsSupportedDeviceOrFinish(this)) {
            return;
        }
        setContentView(R.layout.activity_ar);
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);
        ModelRenderable.builder()
                .setSource(this, Uri.parse("Dice.sfb"))
                .build()
                .thenAccept(renderable -> diceRenderable = renderable)
                .exceptionally(throwable -> {
                    Toast toast = Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return null;
                });

        Log.d("lobajo","test onCreate");

        arFragment.setOnTapArPlaneListener((HitResult hitresult, Plane plane, MotionEvent motionevent) -> {
            if (diceRenderable == null) {
                return;
            }
            /*
            Anchor anchor = hitresult.createAnchor();
            AnchorNode anchorNode = new AnchorNode(anchor);
            anchorNode.setParent(arFragment.getArSceneView().getScene());
            TransformableNode lamp = new TransformableNode(arFragment.getTransformationSystem());
            lamp.setParent(anchorNode);
            lamp.setRenderable(diceRenderable);
            lamp.select();

            lamp.getScaleController().setMinScale(0.40f);
            lamp.getScaleController().setMaxScale(0.48f);

            */
            placeInfotainmentMarker(hitresult,plane,motionevent);


            //populateARTexts();

            if (!populated) {
                //populateAR2(); //to test dynamic placing
                //populateARTexts(); //plant some texts in the scene
                //populateAR3();
                //populateLocationMarkers();
                populated = true;
            }

        });


        MaterialFactory.makeOpaqueWithColor(this, new Color(android.graphics.Color.RED))
                .thenAccept(
                        material -> {
                            redSphereRenderable =
                                    ShapeFactory.makeSphere(0.1f, new Vector3(0.0f, 0.15f, 0.0f), material);
                        });

        //build our text
        ViewRenderable.builder()
                .setView(this, R.layout.test_view)
                .build()
                .thenAccept(renderable -> textRenderable = renderable);


        //populateAR2();
    }

    public void placeInfotainmentMarker(HitResult hitresult, Plane plane, MotionEvent motionevent){
        Intent enterInfotainmentIntent = new Intent(this,InfotainmentFormActivity.class);
        startActivity(enterInfotainmentIntent);
    }

    private List<MyGeoReferencedARObject> getSomeObjects() {

        ArrayList<MyGeoReferencedARObject> result = new ArrayList<>();

        MyGeoReferencedARObject obj1 = new MyGeoReferencedARObject(51.458816f, 7.007380f, "test object");

        result.add(obj1);

        return result;
    }

    private void populateLocationMarkers() {
        SceneView arSceneView = arFragment.getArSceneView();

    }

    private Node getAndy() {
        Node base = new Node();
        base.setRenderable(redSphereRenderable);
        Context c = this;
        base.setOnTapListener((v, event) -> {
            Toast.makeText(
                    c, "Andy touched.", Toast.LENGTH_LONG)
                    .show();
        });
        return base;
    }

    public void populateAR3() {

        Log.d("lobajo","test populateAR3");

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Pair<Double, Double> my_location = new Pair<>(location.getLatitude(), location.getLongitude());

        //TODO:
        //- get anchor of the camera
        //- get geolocation of the phone
        //- read .json to find out the geolocations
        //- translate the gps coordinates into the scene space

        SceneView sceneView = arFragment.getArSceneView();

        Vector3 cameraPos = sceneView.getScene().getCamera().getWorldPosition();

        for(MyGeoReferencedARObject object : getSomeObjects()) {

            Vector3 position = translate_geolocation_to_scene_space(object,cameraPos,my_location);

            // Create an ARCore Anchor at the position.
            Pose pose = Pose.makeTranslation(position.x, position.y, position.z);
            Anchor anchor = ((ArSceneView) sceneView).getSession().createAnchor(pose);

            // Create the Sceneform AnchorNode
            AnchorNode anchorNode = new AnchorNode(anchor);
            anchorNode.setParent(sceneView.getScene());

            // Create the node relative to the AnchorNode
            Node node = new Node();
            node.setParent(anchorNode);
            node.setRenderable(redSphereRenderable);
        }
    }

    public Vector3 translate_geolocation_to_scene_space(MyGeoReferencedARObject object,Vector3 camera_location,Pair<Double,Double> camera_gps){

        //calc object gps - camera_gps

        //translate lon,lat to x,y? with scaling?

        return new Vector3((float)(object.lat-camera_gps.first),(float)(object.lon-camera_gps.second),0f);
    }

    public void populateARTexts() {
        //
        SceneView sceneView = arFragment.getArSceneView();

        //Vector3 position2 = sceneView.getScene().getCamera().getWorldPosition();

        // Find a position half a meter in front of the user.
        Vector3 cameraPos = sceneView.getScene().getCamera().getWorldPosition();
        //Vector3 cameraForward = sceneView.getScene().getCamera().getForward();



        addsomeText(sceneView,cameraPos,0.1); //10 cm
        addsomeText(sceneView,cameraPos,1); //1 m
        addsomeText(sceneView,cameraPos,2);
        addsomeText(sceneView,cameraPos,3);
        addsomeText(sceneView,cameraPos,4);
        addsomeText(sceneView,cameraPos,5);
    }

    public void addsomeText(SceneView sceneView,Vector3 cameraPos, double x_axs_displacement){
        Vector3 position = Vector3.add(cameraPos, new Vector3((float)x_axs_displacement, 0.0f, 0.0f));

        // Create an ARCore Anchor at the position.
        Pose pose = Pose.makeTranslation(position.x, position.y, position.z);
        Anchor anchor = ((ArSceneView) sceneView).getSession().createAnchor(pose);

        // Create the Sceneform AnchorNode
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setParent(sceneView.getScene());

        // Create the node relative to the AnchorNode
        Node node = new Node();
        node.setParent(anchorNode);

        node.setRenderable(textRenderable);

        //Renderable mycopy = textRenderable.makeCopy();
    }

    public void populateAR2() {
        SceneView sceneView = arFragment.getArSceneView();

        Vector3 position2 = sceneView.getScene().getCamera().getWorldPosition();

        // Find a position half a meter in front of the user.
        Vector3 cameraPos = sceneView.getScene().getCamera().getWorldPosition();
        Vector3 cameraForward = sceneView.getScene().getCamera().getForward();
        Vector3 position = Vector3.add(cameraPos, cameraForward.scaled(0.5f));

        // Create an ARCore Anchor at the position.
        Pose pose = Pose.makeTranslation(position.x, position.y, position.z);
        Anchor anchor = ((ArSceneView) sceneView).getSession().createAnchor(pose);

        // Create the Sceneform AnchorNode
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setParent(sceneView.getScene());

        // Create the node relative to the AnchorNode
        Node node = new Node();
        node.setParent(anchorNode);
        node.setRenderable(redSphereRenderable);

        
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void populateAR(){


        //node.setParent(arFragment.getArSceneView().getScene());



        //node.setRenderable(redSphereRenderable);

        //Add an Anchor and a renderable in front of the camera
        Session session = arFragment.getArSceneView().getSession();
        float[] pos = { 0,0,-1 };
        float[] rotation = {0,0,0,1};
        Anchor anchor =  session.createAnchor(new Pose(pos, rotation));
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setRenderable(redSphereRenderable);
        anchorNode.setParent(arFragment.getArSceneView().getScene());

        //EXPERIMENTAL


        /*
        try {
            Renderable test_text = ViewRenderable.builder()
                    .setView(this, R.layout.test_view)
                    .build().get();

            //Node node = new Node();
            //node.setParent(arFragment.getArSceneView().getScene());
            //node.setRenderable(test_text);


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
        //arFragment.getArSceneView().getSession().getAllAnchors()
    }

    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Log.e(TAG, "Sceneform requires Android N or later");
            Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show();
            activity.finish();
            return false;
        }
        String openGlVersionString = ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE)).getDeviceConfigurationInfo().getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later");
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG).show();
            activity.finish();
            return false;
        }
        return true;
    }
}

