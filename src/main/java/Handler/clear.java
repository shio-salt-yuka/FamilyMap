package Handler;

import DataAccess.DataAccessException;
import Result.ClearResult;
import Service.clear_;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class clear implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException{
        boolean success = false;
        Gson gson = new Gson();
        ClearResult result = new ClearResult();
        try{
            if(exchange.getRequestMethod().toUpperCase().equals("POST")) { //no request body, but makes changes
                clear_ service = new clear_();
                result = service.clearAll();
                if(result.isSuccess()){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }else{
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                //exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0); // cleared
                OutputStream resBody = exchange.getResponseBody();
                String r = gson.toJson(result);
                writeString(r,resBody);
                resBody.close();

                System.out.println(r);

                success = true;
            }
//            if(!success){
//                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
//                exchange.getResponseBody().close();
//            }
        }
        catch (IOException | DataAccessException e){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            OutputStream resBody = exchange.getResponseBody();
            String r = gson.toJson(result);
            writeString(r,resBody);
            resBody.close();

            System.out.println(r);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }


    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}

