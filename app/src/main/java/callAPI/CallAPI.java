package callAPI;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/*
doInBackground and params table.

The first String in params is the adress. It is determined by the subclass.
Then : odd : name of the value, even : value
 */
public abstract class CallAPI extends AsyncTask<String, String, String> {

    private Context context;
    private HttpRequestBase request;


    public CallAPI(Context context){
        this.context=context.getApplicationContext();
        this.request = request;
    }

    /*
Those functions are call by the inferior classes to define and get the HttpBase request.
request is a HttpGet for the APIGet subclass. A HttpPost for the APIPost subclass
 */
    protected  HttpRequestBase getRequest(){
        return request;
    }
    protected void setRequest(HttpRequestBase request){
        this.request =request;
    }

    @Override
    protected String doInBackground(String... params) {

        HttpClient httpclient;
        HttpResponse response = null;
        String result = " ";

        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        //We make the list that contains all the infos for the Post request.
        //We also make the String that will added for the GET request url
        Log.i("callApi", "avant boucle");
        for(int i = 0; i< params.length; i=i+2){
            Log.i("callApi", "boucle");
            postParameters.add(new BasicNameValuePair(params[i], params[i + 1]));
        }

        Log.i("callAPI", "fin de la boucle");

        try {
            httpclient = new DefaultHttpClient();

            //If Post request, we add the parameters to the entity
            if(request.getClass() == HttpPost.class){
                ((HttpPost)request).setEntity(
                        new
                                UrlEncodedFormEntity(
                                postParameters
                        ));
            }

            response = httpclient.execute(request);
        }
        catch (Exception e) {
            result = "error";
        }

        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line;

            while ((line = rd.readLine()) != null)
            {
                result = result + line ;
            }
        } catch (Exception e) {
            result = "error";
        }
        Log.i("j", result);
        return result;

    }


    public Context getContext(){
        return context;
    }

    //Function that adds the url at the beggining of the params array
    //Return a new instance of the params table
    //If the params are empty, we
    protected String[] addUrl(String[] params, String url){


            String nouvParams[] = new String[params.length + 1];
            nouvParams[0] = url;

            for (int compter = 0; compter < params.length; compter++) {
                Log.e("addUrl", "a");
                nouvParams[compter + 1] = params[compter];
            }
            return nouvParams;

    }

} // end CallAPI

