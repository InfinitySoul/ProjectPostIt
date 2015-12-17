package complementaryClass;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrien on 04/12/2015.
 */
public class callAPI extends AsyncTask<String, String, String> {



    @Override
    /**
     * Param 0 : URL
     */
    protected String doInBackground(String... params) {

        HttpClient httpclient;
        HttpPost request;
        HttpResponse response = null;
        String result = " ";

        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        //On crée la liste qui contiendra tous nos paramètres
        //Et on y rajoute nos paramétres
        postParameters.add(new BasicNameValuePair("email", "jean@jean.com"));
        postParameters.add(new BasicNameValuePair("password", "jean"));

        Log.i("try", "nous y sommes");

        try {
            httpclient = new DefaultHttpClient();
            Log.i("URL2", params[0]);
            request = new HttpPost(params[0]);
            Log.i("a", "1");
            request.setEntity(
                    new
                            UrlEncodedFormEntity(
                            postParameters
                    ));
            response = httpclient.execute(request);
            Log.i("a", "2");
        }
        catch (Exception e) {
            result = "error";
        }

        try {
            Log.i("a", "3");
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            Log.i("a", "4");
            while ((line = rd.readLine()) != null)
            {
                result = result + line ;
            }
        } catch (Exception e) {
            result = "error";
        }
        Log.i("a", "fin");
        return result;

        /*
            /**
             * TODO
             * Le code actuel est fait pour teter le serveurs. Cela envoit tourjours la meme requete.
             * Il faut un algorithme qui fasse
             * postParameters.add(new BasicNameValuePair(params[impaire], params[pair]))
             *
             * Il faut aussi changer les fonctions qui sont affectés.

            */

    }

    protected void onPostExecute(String result) {

    }

} // end CallAPI
