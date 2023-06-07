package Handler;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import Model.AuthToken;
import Request.eventRequest;
import Result.EventIDResult;
import Service.event_eventid;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.sql.Connection;

public class eventID implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try{
            Database db = new Database();
            Connection conn = db.getConnection();
            AuthTokenDAO aDao = new AuthTokenDAO(conn);
            EventIDResult result = new EventIDResult();
            Gson gson = new Gson();
            if(exchange.getRequestMethod().toUpperCase().equals("GET")) {
                Headers reqHeaders = exchange.getRequestHeaders();
                if(reqHeaders.containsKey("Authorization")) {
                    String authToken = reqHeaders.getFirst("Authorization");
                    AuthToken token = aDao.findAuthToken(authToken);
                    db.closeConnection(true);

                    if(token != null) { //token exists
                        String path = exchange.getRequestURI().getPath();
                        String[] parts = path.split("/");
                        eventRequest request = new eventRequest(parts[2]);
                        event_eventid service = new event_eventid();
                        result = service.eventID(request);

                        if(result.isSuccess() && result.getAssociatedUsername().equals(token.getUsername())){
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        }else{
                            result = new EventIDResult();
                            result.setSuccess(false);
                            result.setMessage("Error: wrong username");
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        }
                        //exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        OutputStream respBody = exchange.getResponseBody();
                        String respData = gson.toJson(result);
                        writeString(respData, respBody);

                        respBody.close();

                        success = true;
                    }else{
                        result.setSuccess(false);
                        result.setMessage("Error: Bad AuthToken");
                    }
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

