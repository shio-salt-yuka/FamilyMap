package Handler;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import Model.AuthToken;
import Request.personRequest;
import Result.PersonIDResult;
import Service.person_personid;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.sql.Connection;

public class personID implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try{
            Database db = new Database();
            Connection conn = db.getConnection();
            AuthTokenDAO aDao = new AuthTokenDAO(conn);
            PersonIDResult result = new PersonIDResult();
            Gson gson = new Gson();
            if(exchange.getRequestMethod().toUpperCase().equals("GET")) {
                Headers reqHeaders = exchange.getRequestHeaders();
                if(reqHeaders.containsKey("Authorization")) {
                    String authToken = reqHeaders.getFirst("Authorization");
                    AuthToken token = aDao.findAuthToken(authToken);
                    db.closeConnection(true);

                    if(token != null) {
                        String path = exchange.getRequestURI().getPath();//path from the user
                        String[] parts = path.split("/");
                        personRequest request = new personRequest(parts[2]); //personID
                        person_personid service = new person_personid();
                        result = service.personID(request);
                        if(result.isSuccess() && result.getA_Username().equals(token.getUsername())){
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        }else{
                            result = new PersonIDResult();
                            result.setSuccess(false);
                            result.setMessage("Error: Bad username");
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
