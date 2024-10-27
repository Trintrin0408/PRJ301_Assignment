package controller.productionplan;

import controller.accesscontrol.BaseRBACController;
import dal.PlanDBContext;
import entity.Plan;
import entity.accesscontrol.User;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.logging.Logger;


public class ProductionDetailPlan extends BaseRBACController {

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
     PlanDBContext db = new PlanDBContext();
        ArrayList<Plan> plans = db.list();
        req.setAttribute("plans", plans);
        req.getRequestDispatcher("../view/productionplan/detailplan.jsp").forward(req, resp);
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User account) throws ServletException, IOException {
        // Post functionality if required later (currently unused)
    }
}
