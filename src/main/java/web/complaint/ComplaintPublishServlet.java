/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.complaint;

import ejb.AccountBean;
import ejb.JsonBean;
import web.complaint.ComplaintBoardServlet;

import java.io.IOException;
import java.io.InputStreamReader;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Gwan
 */
@WebServlet(urlPatterns = {"/complaint/publish"})
public class ComplaintPublishServlet extends HttpServlet {

    @EJB
    private AccountBean account;
    @EJB
    private JsonBean jsonbean;
    @EJB
    private ComplaintBoardServlet cbs;
    private static final long serialVersionUID = 7903037019848392847L;

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JsonReader reader = Json.createReader(new InputStreamReader(request.getInputStream()));
        JsonObject object = reader.readObject();
        Integer comId = 1;
        Integer userId = Integer.parseInt(object.getString("userid"));
        String title = object.getString("title");
        String details = object.getString("details");

        this.account.createComplaint(userId, comId, title, details);

        jsonbean.initResponseAsJson(response);

        cbs.completeResponse(comId, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
