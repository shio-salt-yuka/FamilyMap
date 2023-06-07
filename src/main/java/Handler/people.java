package Handler;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import Model.AuthToken;
import Result.peopleResult;
import Service.person_;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.sql.Connection;

public class people implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try{
            Database db = new Database();
            Connection conn = db.getConnection();// connection open
            AuthTokenDAO aDao = new AuthTokenDAO(conn);
            peopleResult result = new peopleResult();
            Gson gson = new Gson();
            if(exchange.getRequestMethod().toUpperCase().equals("GET")) {
                Headers reqHeaders = exchange.getRequestHeaders();
                if(reqHeaders.containsKey("Authorization")) {
                    String authToken = reqHeaders.getFirst("Authorization");
                    AuthToken token = aDao.findAuthToken(authToken);
                    db.closeConnection(true); //connection closed

                    if(token != null) {
                        person_ service = new person_();
                        result = service.person(token);
                        if(result.isSuccess()){
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        }else{
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        }

                        OutputStream respBody = exchange.getResponseBody();
                        String respData = gson.toJson(result);
                        writeString(respData, respBody);

                        respBody.close();

                        success = true;
                    }else{
                        result.setSuccess(false);
                        result.setMessage("Error: Bad AuthToken");
                    }
//                    db.closeConnection(true); //connection closed
                }
            }
            if(!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                OutputStream respBody = exchange.getResponseBody();
                String respData = gson.toJson(result);
                writeString(respData, respBody);
                exchange.getResponseBody().close();
            }
        }
        catch(IOException | DataAccessException e){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);
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
