package controllers;

import config.Config;
import daos.MajorDAO;
import daos.NotificationDAO;
import daos.QADAO;
import daos.UserDAO;
import dtos.GoogleDTO;
import dtos.MajorDTO;
import dtos.NotificationDTO;
import dtos.QADTO;
import dtos.UserDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utils.GoogleUtils;
import utils.MailUtils;

/**
 *
 * @author Thien
 */
@WebServlet(name = "QAController", urlPatterns = {"/qa"})
public class QAController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        request.setAttribute("controller", "qa");
        String op = request.getParameter("op");
        request.setAttribute("action", op);
        switch (op) {
            case "index_qa":
                index_qa(request, response);
                break;
            case "qa_question":
                qa_question(request, response);
                break;
            case "listAll":
                listAll(request, response);
                break;

        }

    }

    protected void index_qa(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
//        request.setAttribute("action", "index_qa");
        QADAO tf = new QADAO();
        List<QADTO> list = tf.selectAllQues();
        System.out.println("Question: " + list);
        request.setAttribute("listQ", list);
        request.setAttribute("action", "index_qa");
        request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
    }

    protected void qa_question(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String qId = QADAO.newIdQuestion();
//            String email = request.getParameter("email");
            HttpSession session = request.getSession();
            GoogleDTO google = (GoogleDTO) session.getAttribute("info");
            String email = google.getEmail();
            System.out.println(email);
            String detail = request.getParameter("detail");
            System.out.println("Detail: " + detail);
            Date postDate = new Date();
            QADTO qa = new QADTO();
            qa.setqId(qId);
            qa.setEmail(email);
            qa.setDetail(detail);
            qa.setDatetime(postDate);
            request.getAttribute("detail");
            request.getAttribute("qa");
            //Check Input
            if (detail != null && !"".equals(detail.trim())) {
                QADAO.QA_Question(qa);
                request.getRequestDispatcher("/qa?op=index_qa").forward(request, response);
            } else {
                request.setAttribute("msgFailed", "The Detail must be filled.");
                request.getRequestDispatcher("/qa?op=index_qa").forward(request, response);
            }
//            QADAO.QA_Question(new_job);
        } catch (SQLException ex) {
            Logger.getLogger(JobsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JobsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void listAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        try {
            QADAO tf = new QADAO();
            List<QADTO> list = tf.selectAllQues();
            System.out.println("Question: " + list);
            request.setAttribute("listQ", list);
            request.setAttribute("action", "index_qa");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ApplyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QAController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(QAController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QAController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(QAController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
