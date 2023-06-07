package Handler;

import DataAccess.DataAccessException;
import Result.Fill_LoadResult;
import Service.fill_username;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class fill implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException{
        boolean success = false;
        Integer numGen = 4;
        try{
            if(exchange.getRequestMethod().toUpperCase().equals("POST")) {
                String path = exchange.getRequestURI().getPath();//path from the user
                String[] parts = path.split("/");
                String username = parts[2];
                if(parts.length == 4){
                    if(Integer.parseInt(parts[3]) > 0){
                        numGen = Integer.parseInt(parts[3]);
                    }
                }

                fill_username service = new fill_username();
                Gson gson = new Gson();
                Fill_LoadResult result = service.fill(username, numGen);
                if(result.isSuccess()){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }else{
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                OutputStream resBody = exchange.getResponseBody();
                String r = gson.toJson(result);
                writeString(r,resBody);
                resBody.close();

                System.out.println(r);

                success = true;
            }
            if(!success){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException | DataAccessException e){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
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
